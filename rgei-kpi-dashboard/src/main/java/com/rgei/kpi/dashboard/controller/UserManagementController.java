package com.rgei.kpi.dashboard.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

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
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.Department;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.response.model.UserRole;
import com.rgei.kpi.dashboard.service.UserManagementService;
import com.rgei.kpi.dashboard.util.CommonFunction;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class UserManagementController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(UserManagementController.class);
	
	@Resource
	UserManagementService userManagementService;
	
	@ApiOperation(value = "getAllCountries", notes = "Retrieve all countries", response = CountryResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping("/v1/countries")
	public ResponseEntity<List<CountryResponse>> getAllCountries(){
		logger.info("Get all countries list");
		List<CountryResponse> responseList = userManagementService.getCountryList();
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "getRoles", notes = "Retrieve user roles based on active roles flag in header", response = UserRole.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping("/v1/roles")
	public ResponseEntity<List<UserRole>> getRoles(@RequestHeader(value = "activeRoles") Boolean activeRoles){
		logger.info("Get roles by status : "+activeRoles);
		List<UserRole> responseList = userManagementService.getUserRolesByStatus(activeRoles);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "getDepartments", notes = "Retrieve active departments", response = Department.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping("/v1/departments")
	public ResponseEntity<List<Department>> getDepartments(){
		logger.info("Get all active departments list");
		List<Department> responseList = userManagementService.getDepartments();
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "getUsersByMillId", notes = "Retrieve all users by Mill Id", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping("/v1/users")
	public ResponseEntity<List<User>> getUsersByMillId(@RequestHeader(value = "millId") String millId){
		logger.info("Get all users by mill Id : "+millId);
		List<User> responseList = userManagementService.getUsersByMillId(CommonFunction.covertToInteger(millId));
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "createUserRole", notes = "Create new user role")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created") })
	@PostMapping("/v1/create_user_role")
	public ResponseEntity<HttpStatus> createUserRole(@Valid @RequestBody UserRole userRole){
		logger.info("Creating new user role", userRole);
		userManagementService.createUserRole(userRole);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
		
	@ApiOperation(value = "updateUserRole", notes = "Update user role")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@PutMapping("/v1/update_user_role")
	public ResponseEntity<HttpStatus> updateUserRole(@Valid @RequestBody UserRole userRole) {
		logger.info("Updating user role", userRole);
		userManagementService.updateUserRole(userRole);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@ApiOperation(value = "createUser", notes = "Create new user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created") })
	@PostMapping("/v1/create_user")
	public ResponseEntity<HttpStatus> createUser(@RequestBody User user){
		logger.info("Creating new user", user);
		userManagementService.createUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}