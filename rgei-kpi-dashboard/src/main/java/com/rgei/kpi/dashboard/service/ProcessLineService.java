package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.MillsResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineDetailsResponse;

public interface ProcessLineService {
	
	public List<ProcessLineDetailsResponse> getProcessLines(String millId);
	
	List<MillsResponse> getMillDetails(List<String> countryIds);
 
}
