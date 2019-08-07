package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.ProcessLineDetailsResponse;

public interface ProcessLineService {
	
	public List<ProcessLineDetailsResponse> getProcessLines(String millId);

}
