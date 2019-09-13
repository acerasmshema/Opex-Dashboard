package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.entities.UserRoleMillEntity;
import com.rgei.kpi.dashboard.exception.RecordNotCreatedException;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
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
			newUser.setIsActive(Boolean.TRUE);
			newUser.setCreatedBy(user.getCreatedBy());
			newUser.setCreatedOn(date);
			newUser.setUpdatedBy(user.getUpdatedBy());
			newUser.setUpdatedOn(date);
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
			UserRoleMill.setCreatedDate(date);
			UserRoleMill.setUpdatedDate(date);

		} catch (Exception e) {
			throw new RecordNotCreatedException("Error while creating new user role relation :" + millRole);
		}
		return UserRoleMill;
	}

}
