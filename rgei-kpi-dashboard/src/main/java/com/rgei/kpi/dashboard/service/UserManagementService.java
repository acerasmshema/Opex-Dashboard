package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.CountryResponse;

public interface UserManagementService {
	List<CountryResponse> getCountryList();
}
