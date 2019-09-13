package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.List;

import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRole;

public class UserManagementUtility {

	public static List<UserRole> convertToUserRoleResponse(List<UserRoleEntity> entities){
		List<UserRole> responseList = new ArrayList<>();
		UserRole resp = null;
		for(UserRoleEntity entity : entities) {
			resp = new UserRole();
			resp.setUserRoleId(entity.getRoleId().toString());
			resp.setRoleName(entity.getRoleName());
			resp.setActive(entity.getStatus());
			resp.setDescription(entity.getDescription());
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
		newUserRole.setDescription(userRole.getDescription());
		newUserRole.setCreatedBy(userRole.getCreatedBy());
		newUserRole.setCreatedDate(new java.util.Date());
		newUserRole.setUpdatedBy(userRole.getUpdatedBy());
		newUserRole.setUpdatedDate(new java.util.Date());
		newUserRole.setStatus(userRole.getActive());
		return newUserRole;
	}


	public static UserRoleEntity updateFetchedUserRoleEntity(UserRole userRole, UserRoleEntity entity) {
		entity.setRoleName(userRole.getRoleName());
		entity.setDescription(userRole.getDescription());
		entity.setUpdatedBy(userRole.getUpdatedBy());
		entity.setStatus(userRole.getActive());
		entity.setUpdatedDate(new java.util.Date());
		return entity;
	}
}
