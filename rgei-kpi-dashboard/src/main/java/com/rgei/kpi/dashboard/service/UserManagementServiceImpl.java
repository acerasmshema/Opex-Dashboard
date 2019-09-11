package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
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
		List<CountryResponse> responseList = new ArrayList<CountryResponse>();
		List<CountryEntity> entities = countryRepository.findAllByActiveOrderByCountryNameAsc(true);
		if(entities != null && !entities.isEmpty()) {
			CountryResponse resp = null;
			for(CountryEntity entity : entities) {
				resp = new CountryResponse();
				resp.setCountryId(entity.getCountryId());
				resp.setCountryName(entity.getCountryName());
				resp.setCountryCode(entity.getCountryCode());
				responseList.add(resp);
			}
			return responseList;
		}
		throw new RecordNotFoundException("Country list not available in database");
	}

	@Override
	public List<UserRoleResponse> getUserRolesByStatus(Boolean status) {
		logger.info("Inside service call to get roles by status : "+status);
		List<UserRoleResponse> responseList = new ArrayList<UserRoleResponse>();
		List<UserRoleEntity> entities = null;
		if(Objects.nonNull(status) && status) {
			entities = userRoleRepository.findAllByStatusOrderByRoleNameAsc(status);
		} else {
			entities = userRoleRepository.findAllByOrderByRoleNameAsc();
		}
		if(entities != null && !entities.isEmpty()) {
			UserRoleResponse resp = null;
			for(UserRoleEntity entity : entities) {
				resp = new UserRoleResponse();
				resp.setRoleId(entity.getRoleId());
				resp.setRoleName(entity.getRoleName());
				resp.setStatus(entity.getStatus());
				responseList.add(resp);
			}
			return responseList;
		}
		throw new RecordNotFoundException("Roles list not available in database");
	}

	
}
