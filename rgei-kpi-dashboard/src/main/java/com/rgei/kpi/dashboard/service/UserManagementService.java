package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRoleResponse;

public interface UserManagementService {
	List<CountryResponse> getCountryList();
	
	List<UserRoleResponse> getUserRolesByStatus(Boolean status);
}
