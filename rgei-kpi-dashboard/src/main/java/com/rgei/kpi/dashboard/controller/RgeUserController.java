package com.rgei.kpi.dashboard.controller;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.response.model.RgeUserLoginRequest;
import com.rgei.kpi.dashboard.response.model.RgeUserLogoutRequest;
import com.rgei.kpi.dashboard.response.model.RgeUserResponse;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.security.JwtTokenUtil;
import com.rgei.kpi.dashboard.service.RgeUserService;
import com.rgei.kpi.dashboard.util.CommonFunction;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class RgeUserController {

	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(RgeUserController.class);

	@Resource
	RgeUserService rgeUserService;
	
	@Resource
	private JwtTokenUtil jwtTokenUtil;

	@ApiOperation(value = "getUserById", notes = "Retrieve user by id", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping(value = "/v1/user_info/user_by_id")
	public ResponseEntity<User> getUserById(@RequestHeader(value = "userId") String userId) {
		logger.info("Entering into the find user by Id:{}", userId);
		User response = rgeUserService.getUserById(CommonFunction.covertToLong(userId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "getUserByEmail", notes = "Retrieve user by email", response = RgeUserResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping(value = "/v1/user_info/get_by_email")
	public ResponseEntity<RgeUserResponse> getUserByEmail(@RequestHeader(value = "emailId") String emailId) {
		logger.info("Entering into the find user by Email:{}", emailId);
		RgeUserResponse response = rgeUserService.getUserByEmail(emailId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "getUserByName", notes = "Retrieve user by name", response = RgeUserResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping(value = "/v1/user_info/get_by_name")
	public ResponseEntity<RgeUserResponse> getUserByName(@RequestHeader(value = "userName") String userName) {
		logger.info("Entering into the find user by user name:{}", userName);
		RgeUserResponse response = rgeUserService.getUserByName(userName);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "getUserByName", notes = "Authenticate user by username and password", response = RgeUserResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@PostMapping(value = "/v1/user_info/login")
	public ResponseEntity<User> getUserByName(@RequestBody RgeUserLoginRequest rgeUserLoginRequest) throws NoSuchAlgorithmException {
		logger.info("Entering into the login process by the requsted id", rgeUserLoginRequest.getUsername());
		User user = rgeUserService.loginProcess(rgeUserLoginRequest);
		UserDetails userDetails = rgeUserService.loadUserByUsername(rgeUserLoginRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		user.setToken(token);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "logout", notes = "Logout user", response = RgeUserResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@PutMapping(value = "/v1/user_info/logout")
	public ResponseEntity<HttpStatus> logout(@RequestBody RgeUserLogoutRequest rgeUserLogoutRequest) {
		logger.info("Entering into the logout process by the requsted id", rgeUserLogoutRequest.getUsername());
		rgeUserService.logoutProcess(rgeUserLogoutRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
