package com.rgei.kpi.dashboard.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.LoginDetailEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.exception.InvalidCredentialsException;
import com.rgei.kpi.dashboard.exception.LogoutException;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.LoginDetailEntityRepository;
import com.rgei.kpi.dashboard.repository.RgeUserEntityRepository;
import com.rgei.kpi.dashboard.response.model.RgeUserLoginRequest;
import com.rgei.kpi.dashboard.response.model.RgeUserLogoutRequest;
import com.rgei.kpi.dashboard.response.model.RgeUserResponse;
import com.rgei.kpi.dashboard.util.UserConverter;

@Service
public class RgeUserServiceImpl implements RgeUserService{
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(RgeUserServiceImpl.class);

	@Resource
	RgeUserEntityRepository rgeUserEntityRepository;

	@Resource
	LoginDetailEntityRepository loginDetailEntityRepository;


	@Override
	public RgeUserResponse getUserById(Long userId) {
		logger.info("Get user by userId ", userId);
		Optional<RgeUserEntity> userObject = rgeUserEntityRepository.findById(userId);
		RgeUserResponse response = null;
		if(userObject.isPresent()) {
			response = UserConverter.convertToResponse(userObject.get());
		}else {
			throw new RecordNotFoundException("User not found for user Id : "+userId);
		}
		return response;
	}

	@Override
	public RgeUserResponse getUserByName(String name) {
		logger.info("Get user by name ", name);
		return UserConverter.convertToResponse(rgeUserEntityRepository.findByLoginId(name.trim()));
	}

	@Override
	public RgeUserResponse getUserByEmail(String email) {
		logger.info("Get user by email ", email);
		RgeUserEntity userObject = rgeUserEntityRepository.findByEmail(email);
		return  UserConverter.convertToResponse(userObject);
	}

	@Override
	public RgeUserResponse loginProcess(RgeUserLoginRequest rgeUserLoginRequest) {
		RgeUserEntity entity = null;
		RgeUserResponse response = null;
		if(rgeUserLoginRequest.getLoginId() != null) {
			entity = rgeUserEntityRepository.findByLoginId(rgeUserLoginRequest.getLoginId());
			if(entity == null) {
				logger.info("User not found against the requested id and password.",rgeUserLoginRequest.getLoginId(), rgeUserLoginRequest.getUserPassword());
				throw new InvalidCredentialsException("Requested user name and password is not found in the system:"+rgeUserLoginRequest.getLoginId() +"-"+rgeUserLoginRequest.getUserPassword());
			}
			response = 	validateCredential(entity,rgeUserLoginRequest);
			populateLoginDetails(entity);
		}
		logger.info("User logged in successfully.",rgeUserLoginRequest.getLoginId());
		return response;
	}

	private void populateLoginDetails(RgeUserEntity entity) {
		logger.info("Populate login details", entity);
		if(entity != null) {
			List<LoginDetailEntity> loginDetails = loginDetailEntityRepository.findByRgeUserEntity_UserIdAndStatus(entity.getUserId(), Boolean.TRUE);
			if(!loginDetails.isEmpty()) {
				for(LoginDetailEntity logDetails:loginDetails) {
					logDetails.setLogoutTime(new java.util.Date());
					logDetails.setStatus(Boolean.FALSE);
					loginDetailEntityRepository.save(logDetails);
				}
			}
		}
		LoginDetailEntity loginDetailEntity = new LoginDetailEntity();
		loginDetailEntity.setRgeUserEntity(entity);
		loginDetailEntity.setLoginTime(new java.util.Date());
		loginDetailEntity.setStatus(Boolean.TRUE);
		loginDetailEntityRepository.save(loginDetailEntity);
	}

	private RgeUserResponse validateCredential(RgeUserEntity entity, RgeUserLoginRequest rgeUserLoginRequest) {
		if(entity.getLoginId().equals(rgeUserLoginRequest.getLoginId()) && entity.getUserPassword().equals(rgeUserLoginRequest.getUserPassword())) {
			RgeUserResponse userResponse = new RgeUserResponse();
			userResponse.setLoginId(entity.getLoginId());
			userResponse.setUserName(entity.getFirstName());
			userResponse.setUserRole(entity.getUserRoles().get(0).getRoleName());
			return userResponse;
		}else {
			logger.info("Invalid credential requested for login id :",rgeUserLoginRequest.getLoginId(),rgeUserLoginRequest.getUserPassword());
			throw new InvalidCredentialsException("Invalid credential requested for login id :"+entity.getLoginId());
		}
	}

	@Override
	public void logoutProcess(RgeUserLogoutRequest rgeUserLogoutRequest) {
		if(rgeUserLogoutRequest.getLoginId() != null) {
			LoginDetailEntity loginEntity = loginDetailEntityRepository.findByLoginIdAndStatus(rgeUserLogoutRequest.getLoginId().trim(), Boolean.TRUE);
			if(loginEntity == null) {
				throw new LogoutException("Invalid user id requested or user is not logged in.");
			}
				loginEntity.setLogoutTime(new java.util.Date());
				loginEntity.setStatus(Boolean.FALSE);
				loginDetailEntityRepository.save(loginEntity);
			logger.info("User logged out successfully.",rgeUserLogoutRequest.getLoginId());
		}
	}





}
