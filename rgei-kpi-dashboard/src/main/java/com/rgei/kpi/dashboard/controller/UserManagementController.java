package com.rgei.kpi.dashboard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRoleResponse;
import com.rgei.kpi.dashboard.service.UserManagementService;

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
	public ResponseEntity<List<UserRoleResponse>> getRoles(@RequestHeader(value = "allRoles") Boolean allRoles){
		logger.info("Get roles by status : "+allRoles);
		List<UserRoleResponse> responseList = userManagementService.getUserRolesByStatus(allRoles);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
}
