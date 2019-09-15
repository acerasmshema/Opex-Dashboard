package com.rgei.kpi.dashboard.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.exception.RecordExistException;
import com.rgei.kpi.dashboard.repository.RgeUserEntityRepository;

@Service
public class ValidationServiceImpl implements ValidationService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ValidationServiceImpl.class);
	
	@Resource
	RgeUserEntityRepository rgeUserEntityRepository;
	
	@Override
	public Boolean validateUserName(String username) {
		logger.info("Inside service call to validate user by Username : "+username);
		RgeUserEntity entity = rgeUserEntityRepository.findByLoginId(username);
		if(entity != null) {
			throw new RecordExistException("User with Username "+username+" already exists");
		}
		return true;
	}
	
	@Override
	public Boolean validateEmail(String email) {
		logger.info("Inside service call to validate user by Username : "+email);
		RgeUserEntity entity = rgeUserEntityRepository.findByEmail(email);
		if(entity != null) {
			throw new RecordExistException("User with Email "+email+" already exists");
		}
		return true;
	}
}
