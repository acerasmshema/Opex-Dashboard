package com.rgei.kpi.dashboard.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRole;

public class UserManagementUtility {

	public static List<UserRole> convertToUserRoleResponse(List<UserRoleEntity> entities){
		List<UserRole> responseList = new ArrayList<UserRole>();
		UserRole resp = null;
		for(UserRoleEntity entity : entities) {
			resp = new UserRole();
			resp.setUserRoleId(entity.getRoleId().toString());
			resp.setRoleName(entity.getRoleName());
			resp.setActive(entity.getStatus());
			responseList.add(resp);
		}
		return responseList;
	}
	
	
	public static List<CountryResponse> convertToCountryResponse(List<CountryEntity> entities){
		List<CountryResponse> responseList = new ArrayList<>();
		CountryResponse resp = null;
		for(CountryEntity entity : entities) {
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
}
