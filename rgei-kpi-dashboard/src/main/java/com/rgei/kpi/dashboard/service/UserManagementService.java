package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.UserRole;

public interface UserManagementService {
	List<CountryResponse> getCountryList();
	
	List<UserRole> getUserRolesByStatus(Boolean activeRoles);
}
