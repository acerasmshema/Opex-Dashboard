package com.rgei.kpi.dashboard.security;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.controller.KPICategoryController;
import com.rgei.kpi.dashboard.service.RgeUserService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	CentralizedLogger log = RgeiLoggerFactory.getLogger(KPICategoryController.class);
	@Resource
	private RgeUserService rgeUserService;
	@Resource
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.session.timeout}")
	private long jwtTokenValidity;

	@Value("${jwt.session.timeout.loadfactor}")
	private double jwtTokenLoadFactor;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				log.info("Exception while getting user token");
			} catch (ExpiredJwtException e) {
				log.info("JWT Token has expired");
			}
		} else {
			log.info("JWT Token does not begin with Bearer String");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			jwtTokenUtil.setExposedHeaders();
			UserDetails userDetails = this.rgeUserService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				long issuedTime = jwtTokenUtil.getAllClaimsFromToken(jwtToken).getIssuedAt().getTime();
				long currentTime = Calendar.getInstance().getTimeInMillis();
				long expire = currentTime - issuedTime ;
				long loadFac = (long) (TimeUnit.MINUTES.toMillis(jwtTokenValidity) * this.jwtTokenLoadFactor);
				if (expire > loadFac) {
					jwtToken = jwtTokenUtil.generateToken(userDetails);
					response.addHeader("token", jwtToken);
				}
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				throw new AuthorizationServiceException("Unauthorized Access");
			}
		}
		chain.doFilter(request, response);
	}
}