package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.List;

import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRoleResponse;

public class UserManagementUtility {

	public static List<UserRoleResponse> convertToUserRoleResponse(List<UserRoleEntity> entities){
		List<UserRoleResponse> responseList = new ArrayList<UserRoleResponse>();
		UserRoleResponse resp = null;
		for(UserRoleEntity entity : entities) {
			resp = new UserRoleResponse();
			resp.setRoleId(entity.getRoleId());
			resp.setRoleName(entity.getRoleName());
			resp.setStatus(entity.getStatus());
			responseList.add(resp);
		}
		return responseList;
	}
	
	
	public static List<CountryResponse> convertToCountryResponse(List<CountryEntity> entities){
		List<CountryResponse> responseList = new ArrayList<CountryResponse>();
		CountryResponse resp = null;
		for(CountryEntity entity : entities) {
			resp = new CountryResponse();
			resp.setCountryId(entity.getCountryId());
			resp.setCountryName(entity.getCountryName());
			resp.setCountryCode(entity.getCountryCode());
			responseList.add(resp);
		}
		return responseList;
	}
}
