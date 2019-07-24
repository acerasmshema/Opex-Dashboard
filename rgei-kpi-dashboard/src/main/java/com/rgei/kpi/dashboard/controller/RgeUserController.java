package com.rgei.kpi.dashboard.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.rgei.kpi.dashboard.service.RgeUserService;
import com.rgei.kpi.dashboard.util.CommonFunction;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall/v1")
public class RgeUserController {
	
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(RgeUserController.class);

	@Resource
	RgeUserService rgeUserService;

	@GetMapping(value = "/user_info/get_by_id")
	public ResponseEntity<RgeUserResponse> getUserById(@RequestHeader(value = "userId") String userId) {
		logger.info("Entering into the find user by Id:{}",userId);
		RgeUserResponse response = rgeUserService.getUserById(CommonFunction.covertToLong(userId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/user_info/get_by_email")
	public ResponseEntity<RgeUserResponse> getUserByEmail(@RequestHeader(value = "emailId") String emailId) {
		logger.info("Entering into the find user by Email:{}",emailId);
		RgeUserResponse response = rgeUserService.getUserByEmail(emailId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/user_info/get_by_name")
	public ResponseEntity<RgeUserResponse> getUserByName(@RequestHeader(value = "userName") String userName) {
		logger.info("Entering into the find user by user name:{}",userName);
		RgeUserResponse response = rgeUserService.getUserByName(userName);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/user_info/login")
	public ResponseEntity<RgeUserResponse> getUserByName(@RequestBody RgeUserLoginRequest rgeUserLoginRequest) {
		logger.info("Entering into the login process by the requsted id", rgeUserLoginRequest.getLoginId());
		RgeUserResponse response = rgeUserService.loginProcess(rgeUserLoginRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/user_info/logout")
	public ResponseEntity<HttpStatus> logout(@RequestBody RgeUserLogoutRequest rgeUserLogoutRequest) {
		logger.info("Entering into the logout process by the requsted id", rgeUserLogoutRequest.getLoginId());
		rgeUserService.logoutProcess(rgeUserLogoutRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
