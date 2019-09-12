package com.rgei.kpi.dashboard.controller;

import java.util.List;

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
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRole;
import com.rgei.kpi.dashboard.service.UserManagementService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class UserManagementController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(UserManagementController.class);
	
	@Resource
	UserManagementService userManagementService;
	
	@GetMapping("/v1/countries")
	public ResponseEntity<List<CountryResponse>> getAllCountries(){
		logger.info("Get all countries list");
		List<CountryResponse> responseList = userManagementService.getCountryList();
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	@GetMapping("/v1/roles")
	public ResponseEntity<List<UserRole>> getRoles(@RequestHeader(value = "activeRoles") Boolean activeRoles){
		logger.info("Get roles by status : "+activeRoles);
		List<UserRole> responseList = userManagementService.getUserRolesByStatus(activeRoles);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
}
