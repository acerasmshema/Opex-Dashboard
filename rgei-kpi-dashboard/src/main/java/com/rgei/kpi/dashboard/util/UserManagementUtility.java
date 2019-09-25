package com.rgei.kpi.dashboard.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.DepartmentEntity;
import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.entities.UserRoleMillEntity;
import com.rgei.kpi.dashboard.exception.RecordExistException;
import com.rgei.kpi.dashboard.exception.RecordNotCreatedException;
import com.rgei.kpi.dashboard.exception.RecordNotUpdatedException;
import com.rgei.kpi.dashboard.response.model.ChangePasswordRequest;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.Department;
import com.rgei.kpi.dashboard.response.model.MillDetail;
import com.rgei.kpi.dashboard.response.model.MillRole;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.response.model.UserRole;

public class UserManagementUtility {

	// no-arg constructor
	private UserManagementUtility() {
	}

	public static List<UserRole> convertToUserRoleResponse(List<UserRoleEntity> entities) {
		List<UserRole> responseList = new ArrayList<>();
		UserRole resp = null;
		for (UserRoleEntity entity : entities) {
			resp = new UserRole();
			resp.setUserRoleId(entity.getRoleId().toString());
			resp.setRoleName(entity.getRoleName());
			resp.setActive(entity.getStatus());
			resp.setShowUserManagement(entity.getShowUserManagement());
			resp.setDescription(entity.getDescription());
			resp.setCreatedBy(entity.getCreatedBy());
			resp.setCreatedDate(CommonFunction.getString(entity.getCreatedDate()));
			resp.setUpdatedBy(entity.getUpdatedBy());
			resp.setUpdatedDate(CommonFunction.getString(entity.getUpdatedDate()));
			responseList.add(resp);
		}
		return responseList;
	}

	public static List<CountryResponse> convertToCountryResponse(List<CountryEntity> entities) {
		List<CountryResponse> responseList = new ArrayList<>();
		CountryResponse resp = null;
		for (CountryEntity entity : entities) {
			resp = new CountryResponse();
			resp.setCountryId(entity.getCountryId().toString());
			resp.setCountryName(entity.getCountryName());
			resp.setCountryCode(entity.getCountryCode());
			resp.setActive(entity.getActive());
			resp.setCreatedBy(entity.getCreatedBy());
			resp.setCreatedDate(CommonFunction.getString(entity.getCreatedDate()));
			resp.setUpdatedBy(entity.getUpdatedBy());
			resp.setUpdatedDate(CommonFunction.getString(entity.getUpdatedDate()));
			responseList.add(resp);
		}
		return responseList;
	}

	public static UserRoleEntity fetchUserRoleEntity(UserRole userRole) {
		UserRoleEntity newUserRole = new UserRoleEntity();
		newUserRole.setRoleName(userRole.getRoleName());
		newUserRole.setDescription(userRole.getDescription());
		newUserRole.setCreatedBy(userRole.getCreatedBy());
		newUserRole.setCreatedDate(new java.util.Date());
		newUserRole.setUpdatedBy(userRole.getUpdatedBy());
		newUserRole.setUpdatedDate(new java.util.Date());
		newUserRole.setShowUserManagement(Boolean.FALSE);
		newUserRole.setAceAdmin(Boolean.FALSE);
		newUserRole.setStatus(userRole.getActive());
		return newUserRole;
	}

	public static UserRoleEntity updateFetchedUserRoleEntity(UserRole userRole, UserRoleEntity entity) {
		entity.setRoleName(userRole.getRoleName());
		entity.setDescription(userRole.getDescription());
		entity.setUpdatedBy(userRole.getUpdatedBy());
		entity.setUpdatedDate(new java.util.Date());
		if (!entity.getRgeUsers().isEmpty()) {
			for (RgeUserEntity user : entity.getRgeUsers()) {
				if (!user.getIsActive()) {
					entity.setStatus(userRole.getActive());
				} else {
					throw new RecordExistException("Users exists for this role :" + userRole);
				}
			}
		} else {
			entity.setStatus(userRole.getActive());
		}

		return entity;
	}

	public static RgeUserEntity createUserEntity(User user) {
		RgeUserEntity newUser = new RgeUserEntity();
		Date date = new Date();
		try {
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setAddress(user.getAddress());
			newUser.setDepartmentId(
					(user.getDepartment() != null) ? Integer.parseInt(user.getDepartment().getDepartmentId()) : null);
			newUser.setCountry((user.getCountry() != null) ? Integer.parseInt(user.getCountry().getCountryId()) : null);
			newUser.setEmail(user.getEmail());
			newUser.setLoginId(user.getUsername());
			newUser.setPhone(user.getPhone());
			newUser.setUserPassword(encryptPassword(user));
			newUser.setIsActive(Boolean.TRUE);
			newUser.setCreatedBy(user.getCreatedBy());
			newUser.setCreatedOn(date);
			newUser.setUpdatedBy(user.getUpdatedBy());
			newUser.setUpdatedOn(date);
		} catch (Exception e) {
			throw new RecordNotCreatedException("Error while creating new user :" + user);
		}
		return newUser;
	}

	private static String encryptPassword(User user) throws NoSuchAlgorithmException {
		String decodedString = new String(Base64.getDecoder().decode(user.getPassword()));
		String passwordStringForRequest = user.getUsername() + "_" + decodedString;
		return toHexString(getSHA(passwordStringForRequest));
	}

	public static String encryptPassword(ChangePasswordRequest changePasswordRequest, RgeUserEntity rgeUserEntity)
			throws NoSuchAlgorithmException {
		String newPassword = null;
		String decodedString = new String(Base64.getDecoder().decode(changePasswordRequest.getNewPassword()));
		String passwordString = rgeUserEntity.getLoginId() + "_" + decodedString;
		try {
			newPassword = toHexString(UserManagementUtility.getSHA(passwordString));
		} catch (Exception e) {
			throw new NoSuchAlgorithmException();
		}
		return newPassword;
	}

	public static RgeUserEntity updateFetchedUserEntity(User user, RgeUserEntity userEntity) {
		Date date = new Date();
		try {
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());
			userEntity.setAddress(user.getAddress());
			userEntity.setCountry(
					(user.getCountry() != null) ? Integer.parseInt(user.getCountry().getCountryId()) : null);
			userEntity.setDepartmentId(
					(user.getDepartment() != null) ? Integer.parseInt(user.getDepartment().getDepartmentId()) : null);
			userEntity.setEmail(user.getEmail());
			userEntity.setLoginId(user.getUsername());
			userEntity.setPhone(user.getPhone());
			userEntity.setIsActive(user.getActive());
			userEntity.setUpdatedBy(user.getUpdatedBy());
			userEntity.setUpdatedOn(date);
		} catch (Exception e) {
			throw new RecordNotUpdatedException("Error while updating existing user :" + user);
		}
		return userEntity;
	}

	public static UserRoleMillEntity createUserRoleMillEntity(MillRole millRole, User user) {
		UserRoleMillEntity userRoleMill = new UserRoleMillEntity();
		Date date = new Date();
		try {
			userRoleMill.setMillId(Integer.parseInt(millRole.getSelectedMill().getMillId()));
			userRoleMill.setRoleId(Long.parseLong(millRole.getSelectedUserRole().getUserRoleId()));
			userRoleMill.setUserId(Long.parseLong(user.getUserId()));
			userRoleMill.setCreatedBy(user.getCreatedBy());
			userRoleMill.setCreatedDate(date);
			userRoleMill.setUpdatedBy(user.getUpdatedBy());
			userRoleMill.setUpdatedDate(date);
			userRoleMill.setStatus(Boolean.TRUE);
		} catch (Exception e) {
			throw new RecordNotCreatedException("Error while creating new user role relation :" + millRole);
		}
		return userRoleMill;
	}

	public static UserRoleMillEntity updateUserRoleMillEntity(UserRoleMillEntity millRole) {
		Date date = new Date();
		try {
			millRole.setStatus(Boolean.FALSE);
			millRole.setUpdatedDate(date);
		} catch (Exception e) {
			throw new RecordNotCreatedException("Error while updating new user role relation :" + millRole);
		}
		return millRole;
	}

	public static List<Department> convertToDepartmentResponse(List<DepartmentEntity> entities) {
		List<Department> responseList = new ArrayList<>();
		Department resp = null;
		for (DepartmentEntity entity : entities) {
			resp = new Department();
			resp.setDepartmentId(entity.getDepartmentId().toString());
			resp.setDepartmentName(entity.getDepartmentName());
			resp.setDepartmentCode(entity.getDepartmentCode());
			resp.setActive(entity.getActive());
			resp.setCreatedBy(entity.getCreatedBy());
			resp.setCreatedDate(CommonFunction.getString(entity.getCreatedDate()));
			resp.setUpdatedBy(entity.getUpdatedBy());
			resp.setUpdatedDate(CommonFunction.getString(entity.getUpdatedDate()));
			responseList.add(resp);
		}
		return responseList;
	}

	public static List<User> convertToUserFromRgeUserEntity(List<RgeUserEntity> entities) {
		List<User> responseList = new ArrayList<>();
		User user = null;
		for (RgeUserEntity entity : entities) {
			user = new User();
			user.setUserId(CommonFunction.getString(entity.getUserId()));
			user.setFirstName(CommonFunction.getString(entity.getFirstName()));
			user.setLastName(CommonFunction.getString(entity.getLastName()));
			user.setUsername(CommonFunction.getString(entity.getLoginId()));
			user.setCountry(CommonFunction.convertCountryEntityToResponse(entity.getCountryEntity()));
			user.setAddress(CommonFunction.getString(entity.getAddress()));
			user.setActive(entity.getIsActive());
			user.setEmail(CommonFunction.getString(entity.getEmail()));
			user.setPhone(CommonFunction.getString(entity.getPhone()));
			user.setCreatedBy(CommonFunction.getString(entity.getCreatedBy()));
			user.setCreatedDate(CommonFunction.getString(entity.getCreatedOn()));
			user.setUpdatedBy(CommonFunction.getString(entity.getUpdatedBy()));
			user.setUpdatedDate(CommonFunction.getString(entity.getUpdatedOn()));
			user.setDepartment(getDepartment(entity.getDepartment()));
			user.setMillRoles(getMillRoles(entity.getUserRoleMills()));
			if (!user.getMillRoles().isEmpty()) {
				responseList.add(user);
			}
		}
		return responseList;
	}

	public static Department getDepartment(DepartmentEntity entity) {
		if (entity != null) {
			Department department = new Department();
			department.setDepartmentName(entity.getDepartmentName());
			department.setDepartmentId(entity.getDepartmentId().toString());
			department.setDepartmentCode(entity.getDepartmentCode());
			department.setActive(entity.getActive());
			department.setCreatedBy(CommonFunction.getString(entity.getCreatedBy()));
			department.setCreatedDate(CommonFunction.getString(entity.getCreatedDate()));
			department.setUpdatedBy(CommonFunction.getString(entity.getUpdatedBy()));
			department.setUpdatedDate(CommonFunction.getString(entity.getUpdatedDate()));
			return department;
		}
		return null;
	}

	public static List<MillRole> getMillRoles(List<UserRoleMillEntity> userRoleMillEntities) {
		List<MillRole> millRoles = new ArrayList<>();
		MillRole millRole = null;
		if (Objects.nonNull(userRoleMillEntities)) {
			for (UserRoleMillEntity entity : userRoleMillEntities) {
				millRole = new MillRole();
				if (Boolean.TRUE.equals(entity.getStatus()) && Boolean.FALSE.equals(entity.getRole().getAceAdmin())) {
					millRole.setMillRoleId(entity.getRgeUserRoleId().toString());
					millRole.setSelectedMill(getMillDetail(entity.getMill()));
					millRole.setSelectedUserRole(getUserRole(entity.getRole()));
					millRole.setActive(entity.getStatus());
					millRoles.add(millRole);
				}
			}
		}
		return millRoles;
	}

	public static MillDetail getMillDetail(MillEntity millEntity) {
		MillDetail millDetail = null;
		if (millEntity != null) {
			millDetail = new MillDetail();
			millDetail.setMillName(millEntity.getMillName());
			millDetail.setActive(millEntity.getActive());
			millDetail.setCountry(CommonFunction.convertCountryEntityToResponse(millEntity.getCountry()));
			millDetail.setMillCode(millEntity.getMillCode());
			millDetail.setMillId(CommonFunction.getString(millEntity.getMillId()));
			millDetail.setCreatedBy(millEntity.getCreatedBy());
			millDetail.setCreatedDate(CommonFunction.getString(millEntity.getCreatedDate()));
			millDetail.setUpdatedBy(millEntity.getUpdatedBy());
			millDetail.setUpdatedDate(CommonFunction.getString(millEntity.getUpdatedDate()));
		}
		return millDetail;
	}

	public static UserRole getUserRole(UserRoleEntity roleEntity) {
		UserRole userRole = null;
		if (roleEntity != null && Boolean.TRUE.equals(roleEntity.getStatus())) {
			userRole = new UserRole();
			userRole.setRoleName(roleEntity.getRoleName());
			userRole.setActive(roleEntity.getStatus());
			userRole.setUserRoleId(CommonFunction.getString(roleEntity.getRoleId()));
			userRole.setDescription(CommonFunction.getString(roleEntity.getDescription()));
			userRole.setCreatedBy(CommonFunction.getString(roleEntity.getCreatedBy()));
			userRole.setCreatedDate(CommonFunction.getString(roleEntity.getCreatedDate()));
			userRole.setUpdatedBy(CommonFunction.getString(roleEntity.getUpdatedBy()));
			userRole.setUpdatedDate(CommonFunction.getString(roleEntity.getUpdatedDate()));
		}
		return userRole;
	}

	public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		// Static getInstance method is called with hashing SHA
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		// digest() method called
		// to calculate message digest of an input
		// and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public static String toHexString(byte[] hash) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public static User convertToUserFromRgeUserEntity(RgeUserEntity entity) {
		User user = null;
		if (Objects.nonNull(entity)) {
			user = new User();
			user.setUserId(CommonFunction.getString(entity.getUserId()));
			user.setFirstName(CommonFunction.getString(entity.getFirstName()));
			user.setLastName(CommonFunction.getString(entity.getLastName()));
			user.setUsername(CommonFunction.getString(entity.getLoginId()));
			user.setCountry(CommonFunction.convertCountryEntityToResponse(entity.getCountryEntity()));
			user.setAddress(CommonFunction.getString(entity.getAddress()));
			user.setActive(entity.getIsActive());
			user.setEmail(CommonFunction.getString(entity.getEmail()));
			user.setPhone(CommonFunction.getString(entity.getPhone()));
			user.setCreatedBy(CommonFunction.getString(entity.getCreatedBy()));
			user.setCreatedDate(CommonFunction.getString(entity.getCreatedOn()));
			user.setUpdatedBy(CommonFunction.getString(entity.getUpdatedBy()));
			user.setUpdatedDate(CommonFunction.getString(entity.getUpdatedOn()));
			user.setDepartment(getDepartment(entity.getDepartment()));
			user.setMillRoles(getMillRoles(entity.getUserRoleMills()));
		}
		return user;
	}

}
