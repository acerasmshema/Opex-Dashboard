package com.rgei.kpi.dashboard.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.exception.RecordExistException;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.RgeUserEntityRepository;
import com.rgei.kpi.dashboard.repository.UserRoleRepository;

@Service
public class ValidationServiceImpl implements ValidationService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ValidationServiceImpl.class);
	
	@Resource
	RgeUserEntityRepository rgeUserEntityRepository;
	
	@Resource
	UserRoleRepository userRoleRepository;
	
	@Override
	public Boolean validateUserName(String username) {
		logger.info("Inside service call to validate user by Username : "+username);
		RgeUserEntity entity = rgeUserEntityRepository.findByLoginId(username);
		if(entity != null) {
			throw new RecordExistException("User with Username "+username+" exists");
		}
		return true;
	}	
	
	@Override
	public Boolean validateEmail(String email) {
		logger.info("Inside service call to validate user by email : "+email);
		RgeUserEntity entity = rgeUserEntityRepository.findByEmail(email);
		if(entity != null) {
			throw new RecordExistException("User with Email "+email+" already exists");
		}
		return true;
	}

	@Override
	public Boolean validateRoleName(String roleName) {
		logger.info("Inside service call to validate role name : " + roleName);
		List<UserRoleEntity> entity = userRoleRepository.findAllByOrderByRoleNameAsc();
		if (!entity.isEmpty()) {
			for (UserRoleEntity role : entity) {
				if (Objects.nonNull(role.getRoleName()) && role.getRoleName().equalsIgnoreCase(roleName) ) {
					throw new RecordExistException("Role with name " + roleName + " already exists");
				}
			}
		} else {
			throw new RecordNotFoundException("No user roles exists in system");
		}
		return true;
	}
}
