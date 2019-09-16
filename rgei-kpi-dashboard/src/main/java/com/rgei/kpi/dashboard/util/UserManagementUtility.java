package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.DepartmentEntity;
import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.entities.UserRoleMillEntity;
import com.rgei.kpi.dashboard.exception.RecordNotCreatedException;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.Department;
import com.rgei.kpi.dashboard.response.model.MillDetail;
import com.rgei.kpi.dashboard.response.model.MillRole;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.response.model.UserRole;

public class UserManagementUtility {

	public static List<UserRole> convertToUserRoleResponse(List<UserRoleEntity> entities) {
		List<UserRole> responseList = new ArrayList<UserRole>();
		UserRole resp = null;
		for (UserRoleEntity entity : entities) {
			resp = new UserRole();
			resp.setUserRoleId(entity.getRoleId().toString());
			resp.setRoleName(entity.getRoleName());
			resp.setActive(entity.getStatus());
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
		newUserRole.setCreatedBy(userRole.getCreatedBy());
		// newUserRole.setCreatedDate(Timestamp.valueOf(userRole.getCreatedDate()));
		newUserRole.setUpdatedBy(userRole.getUpdatedBy());
		// newUserRole.setUpdatedDate(Timestamp.valueOf(userRole.getUpdatedDate()));
		return newUserRole;
	}

	public static UserRoleEntity updateFetchedUserRoleEntity(UserRole userRole, UserRoleEntity entity) {
		if (Objects.nonNull(userRole.getRoleName()))
			entity.setRoleName(userRole.getRoleName());
		if (Objects.nonNull(userRole.getUpdatedBy()))
			entity.setUpdatedBy(userRole.getUpdatedBy());
		if (Objects.nonNull(userRole.getActive()))
			entity.setStatus(userRole.getActive());
		// entity.setUpdatedDate(userRole.getUpdatedDate().toString());
		return entity;
	}

	public static RgeUserEntity createUserEntity(User user) {
		RgeUserEntity newUser = new RgeUserEntity();
		Date date = new Date();
		try {
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setAddress(user.getAddress());
			newUser.setCountry(user.getCountry());
			newUser.setDepartmentId(user.getDepartment().getDepartmentId());
			newUser.setEmail(user.getEmail());
			newUser.setLoginId(user.getUsername());
			newUser.setPhone(user.getPhone());
			newUser.setUserPassword(user.getPassword());
			newUser.setIsActive(user.getActive());
		} catch (Exception e) {
			throw new RecordNotCreatedException("Error while creating new user role :" + user);
		}
		return newUser;
	}

	public static UserRoleMillEntity createUserRoleMillEntity(MillRole millRole) {
		UserRoleMillEntity UserRoleMill = new UserRoleMillEntity();
		Date date = new Date();
		try {
			UserRoleMill.setMillId(Integer.parseInt(millRole.getSelectedMill().getMillId()));
			UserRoleMill.setRoleId(Long.parseLong(millRole.getSelectedUserRole().getUserRoleId()));
			UserRoleMill.setStatus(Boolean.TRUE);

		} catch (Exception e) {
			throw new RecordNotCreatedException("Error while creating new user role relation :" + millRole);
		}
		return UserRoleMill;
	}

	public static List<Department> convertToDepartmentResponse(List<DepartmentEntity> entities) {
		List<Department> responseList = new ArrayList<Department>();
		Department resp = null;
		for (DepartmentEntity entity : entities) {
			resp = new Department();
			resp.setDepartmentId(entity.getDepartmentId());
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
		List<User> responseList = new ArrayList<User>();
		User user = null;
		for (RgeUserEntity entity : entities) {
			user = new User();
			user.setUserId(CommonFunction.getString(entity.getUserId()));
			user.setFirstName(entity.getFirstName());
			user.setLastName(entity.getLastName());
			user.setUsername(entity.getLoginId());
			user.setCountry(entity.getCountry());
			user.setAddress(entity.getAddress());
			user.setActive(entity.getIsActive());
			user.setEmail(entity.getEmail());
			user.setPhone(entity.getPhone());
			user.setCreatedBy(entity.getCreatedBy());
			user.setCreatedDate(CommonFunction.getString(entity.getCreatedOn()));
			user.setUpdatedBy(entity.getUpdatedBy());
			user.setUpdatedDate(CommonFunction.getString(entity.getUpdatedOn()));
			user.setDepartment(getDepartment(entity.getDepartment()));
			user.setMillRoles(getMillRoles(entity.getUserRoleMills()));
			responseList.add(user);
		}
		return responseList;
	}

	public static Department getDepartment(DepartmentEntity entity) {
		if (entity != null) {
			Department department = new Department();
			department.setDepartmentName(entity.getDepartmentName());
			department.setDepartmentId(entity.getDepartmentId());
			department.setDepartmentCode(entity.getDepartmentCode());
			department.setActive(entity.getActive());
			department.setCreatedBy(entity.getCreatedBy());
			department.setCreatedDate(CommonFunction.getString(entity.getCreatedDate()));
			department.setUpdatedBy(entity.getUpdatedBy());
			department.setUpdatedDate(CommonFunction.getString(entity.getUpdatedDate()));
			return department;
		}
		return null;
	}

	public static List<MillRole> getMillRoles(List<UserRoleMillEntity> userRoleMillEntities) {
		List<MillRole> millRoles = new ArrayList<MillRole>();
		MillRole millRole = null;
		if (Objects.nonNull(userRoleMillEntities)) {
			for (UserRoleMillEntity entity : userRoleMillEntities) {
				millRole = new MillRole();
				millRole.setSelectedMill(getMillDetail(entity.getMill()));
				millRole.setSelectedUserRole(getUserRole(entity.getRole()));
				millRoles.add(millRole);
			}
		}
		return millRoles;
	}

	public static MillDetail getMillDetail(MillEntity millEntity) {
		MillDetail millDetail = new MillDetail();
		if (millEntity != null) {
			millDetail.setMillName(millEntity.getMillName());
			millDetail.setActive(millEntity.getActive());
			millDetail.setCountryId(CommonFunction.getString(millEntity.getCountry().getCountryId()));
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
		UserRole userRole = new UserRole();
		if (roleEntity != null) {
			userRole.setRoleName(roleEntity.getRoleName());
			userRole.setActive(roleEntity.getStatus());
			userRole.setUserRoleId(CommonFunction.getString(roleEntity.getRoleId()));
			userRole.setCreatedBy(roleEntity.getCreatedBy());
			userRole.setCreatedDate(CommonFunction.getString(roleEntity.getCreatedDate()));
			userRole.setUpdatedBy(roleEntity.getUpdatedBy());
			userRole.setUpdatedDate(CommonFunction.getString(roleEntity.getUpdatedDate()));
		}
		return userRole;
	}

}
