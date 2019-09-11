package com.rgei.kpi.dashboard.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.CountryRepository;
import com.rgei.kpi.dashboard.repository.UserRoleRepository;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRoleResponse;
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
	public List<UserRoleResponse> getUserRolesByStatus(Boolean status) {
		logger.info("Inside service call to get roles by status : "+status);
		List<UserRoleEntity> entities = null;
		if(Objects.nonNull(status) && status) {
			entities = userRoleRepository.findAllByStatusOrderByRoleNameAsc(status);
		} else {
			entities = userRoleRepository.findAllByOrderByRoleNameAsc();
		}
		if(entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToUserRoleResponse(entities);
		}
		throw new RecordNotFoundException("Roles list not available in database");
	}

	
}
