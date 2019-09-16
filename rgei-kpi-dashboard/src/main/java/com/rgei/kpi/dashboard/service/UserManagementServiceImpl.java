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
		List<CountryEntity> entities = countryRepository.findAllByActiveOrderByCountryNameAsc(true);
		if (entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToCountryResponse(entities);
		}
		throw new RecordNotFoundException("Country list not available in database");
	}

	@Override
	public List<UserRole> getUserRolesByStatus(Boolean activeRoles) {
		logger.info("Inside service call to get roles by status : " + activeRoles);
		List<UserRoleEntity> entities = null;
		if(Objects.nonNull(activeRoles) && activeRoles) {
			entities = userRoleRepository.findAllByStatusOrderByRoleIdAsc(activeRoles);
		} else {
			entities = userRoleRepository.findAllByOrderByRoleIdAsc();
		}
		if (entities != null && !entities.isEmpty()) {
			return UserManagementUtility.convertToUserRoleResponse(entities);
		}
		throw new RecordNotFoundException("Roles list not available in database");
	}

	@Override
	public void createUserRole(UserRole userRole) {
		logger.info("Inside service call to create new user role for request : " + userRole);
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
		UserRoleEntity entity = userRoleRepository.findByRoleId(Long.parseLong(userRole.getUserRoleId()));
		if (null != entity) {
			entity = UserManagementUtility.updateFetchedUserRoleEntity(userRole, entity);
		} else {
			throw new RecordNotFoundException("User Role not found against role id  :" + userRole.getUserRoleId());
		}
		try {
			userRoleRepository.save(entity);
		} catch (RuntimeException e) {
			throw new RecordNotUpdatedException("Error while updating user role :" + userRole);
		}
	}

	@Transactional
	@Override
	public void createUser(User user) {
		logger.info("Inside service call to get create new user for request : " + user);
		Date date = new Date();
		try {
			RgeUserEntity userEntity = UserManagementUtility.createUserEntity(user);
			rgeUserEntityRepository.save(userEntity);
				user.setUserId(userEntity.getUserId().toString());
				List<MillRole> millRoles = user.getMillRoles();
				for (MillRole millRole : millRoles) {
					UserRoleMillEntity userRoleMillEntity = UserManagementUtility.createUserRoleMillEntity(millRole);
					userRoleMillEntity.setUserId(Long.parseLong(user.getUserId()));
					userRoleMillEntity.setCreatedBy(user.getCreatedBy());
					userRoleMillEntity.setCreatedDate(date);
					userRoleMillEntity.setUpdatedBy(user.getUpdatedBy());
					userRoleMillEntity.setUpdatedDate(date);
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
		List<RgeUserEntity> userEntities = rgeUserEntityRepository.findAllUsersByMillId(millId);
		if (userEntities != null && !userEntities.isEmpty()) {
			return UserManagementUtility.convertToUserFromRgeUserEntity(userEntities);
		}
		throw new RecordNotFoundException("Users list not available in database for Mill Id : " + millId);
	}

	@Transactional
	@Override
	public void updateUser(User user) {
		logger.info("Inside service call to update user for request : " + user);
		Date date = new Date();
		try {
			if (user != null) {
				Optional<RgeUserEntity> userEntity = rgeUserEntityRepository.findById(Long.parseLong(user.getUserId()));
				if (userEntity.isPresent()) {
					RgeUserEntity updatedUser = UserManagementUtility.updateFetchedUserEntity(user, userEntity.get());
					rgeUserEntityRepository.save(updatedUser);
					List<MillRole> millRoles = user.getMillRoles();
					for (MillRole millRole : millRoles) {
						UserRoleMillEntity userRoleMillEntity = UserManagementUtility
								.createUserRoleMillEntity(millRole);
						userRoleMillEntity.setRgeUserRoleId(Long.parseLong(millRole.getMillRoleId()));
						userRoleMillEntity.setUserId(Long.parseLong(user.getUserId()));
						userRoleMillEntity.setUpdatedBy(user.getUpdatedBy());
						userRoleMillEntity.setUpdatedDate(date);
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
	public void changePassword(String userId, String password) throws NoSuchAlgorithmException {
		logger.info("Inside service call to change password");
		String decodedString = new String(Base64.getDecoder().decode(password));
		String encodedSHAString = "";
		try {
			encodedSHAString = UserManagementUtility.toHexString(UserManagementUtility.getSHA(decodedString));
		} catch (Exception e) {
			throw new NoSuchAlgorithmException();
		}
		RgeUserEntity rgeUserEntity = null;
		try {
			rgeUserEntity = rgeUserEntityRepository.findByUserId(Long.parseLong(userId));
		} catch (Exception e) {
			throw new RecordNotFoundException("No record found for user Id : " + userId);
		}
		if (rgeUserEntity != null) {
			rgeUserEntity.setUserPassword(encodedSHAString);
			rgeUserEntity.setUpdatedOn(new Date());
			rgeUserEntity.setUpdatedBy(rgeUserEntity.getLoginId());
			try {
				rgeUserEntityRepository.save(rgeUserEntity);
			} catch (Exception e) {
				throw new RecordNotUpdatedException("Error while changing password for user Id : " + userId);
			}
		} else {
			throw new RecordNotFoundException("No record found for user Id : " + userId);
		}

	}
}
