package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.List;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;
import com.rgei.kpi.dashboard.response.model.ProcessLineTargetThreshold;

public class ProcessLineConfigurationManagementUtility {
	private ProcessLineConfigurationManagementUtility() {
	}

	public static List<ProcessLineTargetThreshold> convertToProcessLineThreshold(
			List<ProcessLineConfigurationEntity> processLineConfigurationEntityList) {
		List<ProcessLineTargetThreshold> response = new ArrayList<>();
		for(ProcessLineConfigurationEntity config : processLineConfigurationEntityList) {
			ProcessLineTargetThreshold threshold = new ProcessLineTargetThreshold();
			threshold.setProcessLineTargetThreshold(config.getProcessLineConfigurationId().toString());
			threshold.setBuType(CommonFunction.convertBUEntityToResponse(config.getBuType()));
			threshold.setMill(CommonFunction.convertMillEntityToResponse(config.getMill()));
			threshold.setProcessLine(CommonFunction.convertProcessLineEntityToResponse(config.getProcessLine()));
			threshold.setThreshold(config.getThreshold());
			threshold.setMinimum(config.getMinimum());
			threshold.setMaximum(config.getMaximum());
			threshold.setStartDate(Utility.dateToStringConvertor(config.getStartDate(), DashboardConstant.THRESHOLD_DATE_FORMAT));
			threshold.setEndDate(Utility.dateToStringConvertor(config.getEndDate(), DashboardConstant.THRESHOLD_DATE_FORMAT));
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
