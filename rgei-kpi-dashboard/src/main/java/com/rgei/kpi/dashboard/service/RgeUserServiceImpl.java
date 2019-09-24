package com.rgei.kpi.dashboard.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.LoginDetailEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.exception.InActiveUserException;
import com.rgei.kpi.dashboard.exception.InvalidCredentialsException;
import com.rgei.kpi.dashboard.exception.LogoutException;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.LoginDetailEntityRepository;
import com.rgei.kpi.dashboard.repository.RgeUserEntityRepository;
import com.rgei.kpi.dashboard.response.model.RgeUserLoginRequest;
import com.rgei.kpi.dashboard.response.model.RgeUserLogoutRequest;
import com.rgei.kpi.dashboard.response.model.RgeUserResponse;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.util.UserConverter;
import com.rgei.kpi.dashboard.util.UserManagementUtility;

@Service
public class RgeUserServiceImpl implements RgeUserService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(RgeUserServiceImpl.class);

	@Resource
	RgeUserEntityRepository rgeUserEntityRepository;

	@Resource
	LoginDetailEntityRepository loginDetailEntityRepository;

	@Override
	public User getUserById(Long userId) {
		logger.info("Get user by userId ", userId);
		Optional<RgeUserEntity> userObject = rgeUserEntityRepository.findById(userId);
		User response = null;
		if (userObject.isPresent()) {
			response = UserManagementUtility.convertToUserFromRgeUserEntity(userObject.get());
		} else {
			throw new RecordNotFoundException("User not found for user Id : " + userId);
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
		return UserConverter.convertToResponse(userObject);
	}

	@Override
	public User loginProcess(RgeUserLoginRequest rgeUserLoginRequest) throws NoSuchAlgorithmException {
		RgeUserEntity entity = null;
		User response = null;
		if (rgeUserLoginRequest.getUsername() != null) {
			entity = rgeUserEntityRepository.findByLoginId(rgeUserLoginRequest.getUsername());
			if (entity == null) {
				logger.info("User not found against the requested username", rgeUserLoginRequest.getUsername());
				throw new InvalidCredentialsException(
						"Requested user not exist in the system:" + rgeUserLoginRequest.getUsername());
			}
			if (Boolean.TRUE.equals(entity.getIsActive())) {
				response = validateCredential(entity, rgeUserLoginRequest);
				populateLoginDetails(entity);
			} else {
				logger.info("User is not active.", rgeUserLoginRequest.getUsername());
				throw new InActiveUserException("User is not active :" + entity.getLoginId());
			}
		}
		logger.info("User logged in successfully.", rgeUserLoginRequest.getUsername());
		return response;
	}

	private void populateLoginDetails(RgeUserEntity entity) {
		logger.info("Populate login details", entity);
		if (entity != null) {
			List<LoginDetailEntity> loginDetails = loginDetailEntityRepository
					.findByRgeUserEntity_UserIdAndStatus(entity.getUserId(), Boolean.TRUE);
			if (!loginDetails.isEmpty()) {
				for (LoginDetailEntity logDetails : loginDetails) {
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

	private User validateCredential(RgeUserEntity entity, RgeUserLoginRequest rgeUserLoginRequest)
			throws NoSuchAlgorithmException {
		User user = null;
		String decodedString = new String(Base64.getDecoder().decode(rgeUserLoginRequest.getPassword()));
		String passwordStringForRequest = rgeUserLoginRequest.getUsername() + "_" + decodedString;
		String userEncryptedPasswordForRequest = UserManagementUtility
				.toHexString(UserManagementUtility.getSHA(passwordStringForRequest));
		if (entity.getLoginId().equals(rgeUserLoginRequest.getUsername())
				&& entity.getUserPassword().equals(userEncryptedPasswordForRequest)) {
			user = UserConverter.createUserResponse(entity);
		} else {
			logger.info("Invalid credential requested for login id :", rgeUserLoginRequest.getUsername(),
					rgeUserLoginRequest.getPassword());
			throw new InvalidCredentialsException("Invalid credential requested for login id :" + entity.getLoginId());
		}
		return user;
	}

	@Override
	public void logoutProcess(RgeUserLogoutRequest rgeUserLogoutRequest) {
		if (rgeUserLogoutRequest.getUsername() != null) {
			LoginDetailEntity loginEntity = loginDetailEntityRepository
					.findByLoginIdAndStatus(rgeUserLogoutRequest.getUsername().trim(), Boolean.TRUE);
			if (loginEntity == null) {
				throw new LogoutException("Invalid user id requested or user is not logged in.");
			}
			loginEntity.setLogoutTime(new java.util.Date());	
			loginEntity.setStatus(Boolean.FALSE);
			loginDetailEntityRepository.save(loginEntity);
			logger.info("User logged out successfully.", rgeUserLogoutRequest.getUsername());
		}
	}

}
