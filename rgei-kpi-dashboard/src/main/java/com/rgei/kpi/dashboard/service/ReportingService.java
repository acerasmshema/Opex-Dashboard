package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.LoginDetailResponse;

public interface ReportingService {

	public List<LoginDetailResponse> getAllUserLoginDetails(String startDate, String endDate, String millId);
	
	public List<LoginDetailResponse> getDistinctUserLoginDetails(String startDate, String endDate, String millId);
}
