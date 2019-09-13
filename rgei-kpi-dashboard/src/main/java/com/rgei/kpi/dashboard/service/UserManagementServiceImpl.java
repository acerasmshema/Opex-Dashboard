package com.rgei.kpi.dashboard.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.exception.RecordNotCreatedException;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.CountryRepository;
import com.rgei.kpi.dashboard.repository.UserRoleRepository;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRole;
import com.rgei.kpi.dashboard.util.UserManagementUtility;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(UserManagementServiceImpl.class);
	
	@Resource
	CountryRepository countryRepository;
	
	@Resource
	UserRoleRepository userRoleRepository;
	
	@Override
	public List<CountryResponse> getCountryList() {
		logger.info("Inside service call to get all countries");
		List<CountryEntity> entities = countryRepository.findAllByActiveOrderByCountryNameAsc(true);
		if(entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToCountryResponse(entities);
		}
		throw new RecordNotFoundException("Country list not available in database");
	}

	@Override
	public List<UserRole> getUserRolesByStatus(Boolean activeRoles) {
		logger.info("Inside service call to get roles by status : "+activeRoles);
		List<UserRoleEntity> entities = null;
		if(Objects.nonNull(activeRoles) && activeRoles) {
			entities = userRoleRepository.findAllByStatusOrderByRoleIdAsc(activeRoles);
		} else {
			entities = userRoleRepository.findAllByOrderByRoleIdAsc();
		}
		if(entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToUserRoleResponse(entities);
		}
		throw new RecordNotFoundException("Roles list not available in database");
	}

	@Override
	public void createUserRole(UserRole userRole) {
		logger.info("Inside service call to get create new user role for request : "+userRole);
		UserRoleEntity entity = UserManagementUtility.fetchUserRoleEntity(userRole);
		try {
		userRoleRepository.save(entity);
		}catch(RuntimeException e) {
			throw new RecordNotCreatedException("Error while creating new user role :"+ userRole);
		}
	}

	@Override
	public void updateUserRole(UserRole userRole) {
		logger.info("Inside service call to get update user role for request : " + userRole);
		UserRoleEntity entity = userRoleRepository.findByRoleId(Long.parseLong(userRole.getUserRoleId()));
		if (null != entity) {
			entity = UserManagementUtility.updateFetchedUserRoleEntity(userRole, entity);
		} else {
			throw new RecordNotFoundException("User Role not found against role id  :" + userRole.getUserRoleId());
		}
		try {
			userRoleRepository.save(entity);
		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while creating new user role :" + userRole);
		}
	}

	
}
