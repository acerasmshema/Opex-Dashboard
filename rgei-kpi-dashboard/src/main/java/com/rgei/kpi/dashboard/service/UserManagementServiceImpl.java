package com.rgei.kpi.dashboard.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.DepartmentEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.entities.UserRoleMillEntity;
import com.rgei.kpi.dashboard.exception.RecordNotCreatedException;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.exception.RecordNotUpdatedException;
import com.rgei.kpi.dashboard.repository.CountryRepository;
import com.rgei.kpi.dashboard.repository.DepartmentRepository;
import com.rgei.kpi.dashboard.repository.RgeUserEntityRepository;
import com.rgei.kpi.dashboard.repository.RgeUserRoleMillRepository;
import com.rgei.kpi.dashboard.repository.UserRoleRepository;
import com.rgei.kpi.dashboard.response.model.ChangePasswordRequest;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.Department;
import com.rgei.kpi.dashboard.response.model.MillRole;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.response.model.UserRole;
import com.rgei.kpi.dashboard.util.UserManagementUtility;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(UserManagementServiceImpl.class);

	@Resource
	ValidationService validationService;

	@Resource
	CountryRepository countryRepository;

	@Resource
	UserRoleRepository userRoleRepository;

	@Resource
	RgeUserEntityRepository rgeUserEntityRepository;

	@Resource
	RgeUserRoleMillRepository rgeUserRoleMillRepository;

	@Resource
	DepartmentRepository departmentRepository;

	@Override
	public List<CountryResponse> getCountryList() {
		logger.info("Inside service call to get all countries");
		List<CountryEntity> entities = countryRepository.findAllByActiveOrderByCountryNameAsc(Boolean.TRUE);
		if (entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToCountryResponse(entities);
		}
		throw new RecordNotFoundException("Country list not available in database");
	}

	@Override
	public List<UserRole> getUserRolesByStatus(Boolean activeRoles) {
		logger.info("Inside service call to get roles by status : " + activeRoles);

		List<UserRoleEntity> entities = null;
		if (Objects.nonNull(activeRoles) && activeRoles) {
			entities = userRoleRepository.findAllByStatusAndAceAdminOrderByRoleNameAsc(activeRoles, Boolean.FALSE);
		} else {
			entities = userRoleRepository.findAllByAceAdminOrderByRoleNameAsc(Boolean.FALSE);
		}
		if (entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToUserRoleResponse(entities);
		}
		throw new RecordNotFoundException("Roles list not available in database");
	}

	@Override
	public void createUserRole(UserRole userRole) {
		logger.info("Inside service call to create new user role for request : " + userRole);
		validationService.validateRoleName(userRole.getRoleName());
		UserRoleEntity userRoleEntity = UserManagementUtility.fetchUserRoleEntity(userRole);
		try {
			userRoleRepository.save(userRoleEntity);
		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while creating new user role :" + userRole);
		}
	}

	@Override
	public void updateUserRole(UserRole userRole) {
		logger.info("Inside service call to update user role for request : " + userRole);
		UserRoleEntity updateEntity = null;
		Optional<UserRoleEntity> entity = Optional
				.ofNullable(userRoleRepository.findByRoleId(Long.parseLong(userRole.getUserRoleId())));
		if (entity.isPresent()) {
			updateEntity = UserManagementUtility.updateFetchedUserRoleEntity(userRole, entity.get());
		} else {
			throw new RecordNotFoundException("User Role not found against role id  :" + userRole.getUserRoleId());
		}
		try {
			userRoleRepository.save(updateEntity);
		} catch (RuntimeException e) {
			throw new RecordNotUpdatedException("Error while updating user role :" + userRole);
		}
	}

	@Transactional
	@Override
	public void createUser(User user) {
		logger.info("Inside service call to get create new user for request : " + user);
		validationService.validateUserName(user.getUsername());
		validationService.validateEmail(user.getEmail());
		try {
			RgeUserEntity userEntity = UserManagementUtility.createUserEntity(user);
			rgeUserEntityRepository.save(userEntity);
			user.setUserId(userEntity.getUserId().toString());
			List<MillRole> millRoles = user.getMillRoles();
			for (MillRole millRole : millRoles) {
				UserRoleMillEntity userRoleMillEntity = UserManagementUtility.createUserRoleMillEntity(millRole, user);
				rgeUserRoleMillRepository.save(userRoleMillEntity);
			}

		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while creating new user  :" + user);
		}
	}

	@Override
	public List<Department> getDepartments() {
		logger.info("Inside service call to get departments");
		List<DepartmentEntity> entities = departmentRepository.findAllByActiveOrderByDepartmentNameAsc(true);
		if (entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToDepartmentResponse(entities);
		}
		throw new RecordNotFoundException("Departments list not available in database");
	}

	@Override
	public List<User> getUsersByMillId(Integer millId) {
		logger.info("Inside service call to get users by Mill Id : " + millId);
		List<RgeUserEntity> userEntities = rgeUserEntityRepository.findAllUsersByMillId(millId, Boolean.TRUE);
		if (userEntities != null && !userEntities.isEmpty()) {
			return UserManagementUtility.convertToUserFromRgeUserEntity(userEntities);
		}
		throw new RecordNotFoundException("Users list not available in database for Mill Id : " + millId);
	}

	@Transactional
	@Override
	public void updateUser(User user) {
		logger.info("Inside service call to update user for request : " + user);
		try {
			if (user != null) {
				RgeUserEntity userEntity = rgeUserEntityRepository.findByUserId(Long.parseLong(user.getUserId()));
				if (userEntity != null) {
					RgeUserEntity updatedUser = UserManagementUtility.updateFetchedUserEntity(user, userEntity);
					rgeUserEntityRepository.save(updatedUser);
					Optional<List<UserRoleMillEntity>> millRoles = Optional.ofNullable(rgeUserRoleMillRepository
							.findAllByUserIdAndStatus(Long.parseLong(user.getUserId()), Boolean.TRUE));
					if (millRoles.isPresent()) {
						for (UserRoleMillEntity entity : millRoles.get()) {
							UserRoleMillEntity userRoleMillEntity = UserManagementUtility
									.updateUserRoleMillEntity(entity);
							userRoleMillEntity.setUpdatedBy(user.getUpdatedBy());
							rgeUserRoleMillRepository.save(userRoleMillEntity);
						}
					}
					for (MillRole millRole : user.getMillRoles()) {
						UserRoleMillEntity userRoleMillEntity = UserManagementUtility.createUserRoleMillEntity(millRole,
								user);
						rgeUserRoleMillRepository.save(userRoleMillEntity);
					}
				} else {
					throw new RecordNotFoundException("User not found against user id  :" + user.getUserId());
				}
			}
		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while updating user  :" + user);
		}
	}

	@Override
	public void changePassword(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException {
		logger.info("Inside service call to change password");
		RgeUserEntity rgeUserEntity = null;
		try {
			rgeUserEntity = rgeUserEntityRepository.findByUserId(Long.parseLong(changePasswordRequest.getUserId()));
		} catch (Exception e) {
			throw new RecordNotFoundException("No record found for user Id : " + changePasswordRequest.getUserId());
		}
		if (rgeUserEntity != null) {
			Boolean isCurrentPasswordSame = validateCurrentPassword(changePasswordRequest, rgeUserEntity);
			if (isCurrentPasswordSame) {
				String newPassword = UserManagementUtility.encryptPassword(changePasswordRequest, rgeUserEntity);
				rgeUserEntity.setUserPassword(newPassword);
				rgeUserEntity.setUpdatedOn(new Date());
				rgeUserEntity.setUpdatedBy(rgeUserEntity.getLoginId());
				try {
					rgeUserEntityRepository.save(rgeUserEntity);
				} catch (Exception e) {
					throw new RecordNotUpdatedException(
							"Error while changing password for user Id : " + changePasswordRequest.getUserId());
				}
			} else {
				throw new RecordNotFoundException(
						"Current password don't match for user id: " + changePasswordRequest.getUserId());
			}
		} else {
			throw new RecordNotFoundException("No record found for user Id : " + changePasswordRequest.getUserId());
		}

	}

	private Boolean validateCurrentPassword(ChangePasswordRequest changePasswordRequest, RgeUserEntity rgeUserEntity)
			throws NoSuchAlgorithmException {
		String newPassword = null;
		Boolean isCurrentPasswordSame = false;
		String decodedString = new String(Base64.getDecoder().decode(changePasswordRequest.getCurrentPassword()));
		String passwordString = rgeUserEntity.getLoginId() + "_" + decodedString;
		try {
			newPassword = UserManagementUtility.toHexString(UserManagementUtility.getSHA(passwordString));
		} catch (Exception e) {
			throw new NoSuchAlgorithmException();
		}
		if (newPassword.equals(rgeUserEntity.getUserPassword())) {
			isCurrentPasswordSame = true;
		}
		return isCurrentPasswordSame;
	}

	@Override
	public void encryptPasswordForAllUsers() throws NoSuchAlgorithmException {
		logger.info("Inside service call to encrypt password for all users");
		List<RgeUserEntity> users = rgeUserEntityRepository.findAll();
		for (RgeUserEntity usr : users) {
			
			String passwordString = usr.getLoginId() + "_" + usr.getUserPassword();
			String encodedSHAString = "";
			try {
				encodedSHAString = UserManagementUtility.toHexString(UserManagementUtility.getSHA(passwordString));
			} catch (Exception e) {
				throw new NoSuchAlgorithmException();
			}
			usr.setUserPassword(encodedSHAString);
			usr.setUpdatedOn(new Date());
			usr.setUpdatedBy(usr.getLoginId());
			try {
				rgeUserEntityRepository.save(usr);
			} catch (Exception e) {
				throw new RecordNotUpdatedException("Error while changing password for user Id : " + usr.getUserId());
			}
			
		}
	}
}
