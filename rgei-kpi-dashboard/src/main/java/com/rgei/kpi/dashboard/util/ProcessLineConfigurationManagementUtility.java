package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.List;

import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;
import com.rgei.kpi.dashboard.response.model.ProcessLineTargetThreshold;

public class ProcessLineConfigurationManagementUtility {
	private ProcessLineConfigurationManagementUtility() {
	}

	public static List<ProcessLineTargetThreshold> convertToProcessLineThreshold(
			List<ProcessLineConfigurationEntity> ProcessLineConfigurationEntityList) {
		List<ProcessLineTargetThreshold> response = new ArrayList<>();
		for(ProcessLineConfigurationEntity config : ProcessLineConfigurationEntityList) {
			ProcessLineTargetThreshold threshold = new ProcessLineTargetThreshold();
			threshold.setProcessLineTargetThreshold(config.getProcessLineConfigurationId().toString());
			threshold.setBuType(CommonFunction.convertBUEntityToResponse(config.getBuType()));
			threshold.setMill(CommonFunction.convertMillEntityToResponse(config.getMill()));
			threshold.setProcessLine(CommonFunction.convertProcessLineEntityToResponse(config.getProcessLine()));
			threshold.setThreshold(config.getThreshold());
			threshold.setMinimum(config.getMinimum());
			threshold.setMaximum(config.getMaximum());
			threshold.setStartDate(config.getStartDate().toString());
			threshold.setEndDate(config.getEndDate().toString());
			threshold.setActive(config.getActive());
			threshold.setCreatedBy(config.getCreatedBy());
			threshold.setCreatedDate(config.getCreatedDate().toString());
			threshold.setUpdatedBy(config.getUpdatedBy());
			threshold.setUpdatedDate(config.getUpdatedDate().toString());
			response.add(threshold);
		}
		
		return response;
	}

	

}
