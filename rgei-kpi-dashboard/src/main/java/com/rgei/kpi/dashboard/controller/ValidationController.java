package com.rgei.kpi.dashboard.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.service.ValidationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class ValidationController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ValidationController.class);
	
	@Resource
	ValidationService validationService;
	
	@ApiOperation(value = "validateUsernameAndEmail", notes = "Validate username received in request, throw exception if it exists")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"), @ApiResponse(code = 500, message = "RecordExistException") })
	@GetMapping("/v1/validate_user_name")
	public ResponseEntity<HttpStatus> validateUsername(@RequestHeader(name = "username") String username) {
		logger.info("Validate Username for ", username);
		validationService.validateUserName(username);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "validateUsernameAndEmail", notes = "Validate email received in request, throw exception if it exists")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"), @ApiResponse(code = 500, message = "RecordExistException") })
	@GetMapping("/v1/validate_email")
	public ResponseEntity<HttpStatus> validateEmail(@RequestHeader(name = "email") String email) {
		logger.info("Validate Email for ", email);
		validationService.validateEmail(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}