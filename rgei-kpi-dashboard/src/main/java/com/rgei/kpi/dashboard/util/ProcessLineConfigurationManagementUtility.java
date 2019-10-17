package com.rgei.kpi.dashboard.util;

import java.sql.Timestamp;
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
			threshold.setProcessLineTargetThresholdId(config.getProcessLineConfigurationId().toString());
			threshold.setBuType(CommonFunction.convertBUEntityToResponse(config.getBuType()));
			threshold.setMillId(config.getMillId());
			threshold.setKpiId(config.getKpiId());
			threshold.setProcessLine(CommonFunction.convertProcessLineEntityToResponse(config.getProcessLine()));
			threshold.setThreshold(config.getThreshold());
			threshold.setMinimum(config.getMinimum());
			threshold.setMaximum(config.getMaximum());
			threshold.setStartDate(config.getStartDate().toString());
			threshold.setEndDate(config.getEndDate().toString());
			threshold.setActive(config.getActive());
			threshold.setIsDefaultConfig(config.getIsDefault());
			threshold.setCreatedBy(config.getCreatedBy());
			threshold.setCreatedDate(config.getCreatedDate().toString());
			threshold.setUpdatedBy(config.getUpdatedBy());
			threshold.setUpdatedDate(config.getUpdatedDate().toString());
			response.add(threshold);
		}
		
		return response;
	}

	public static ProcessLineConfigurationEntity getProcessLineConfigurationEntity(ProcessLineTargetThreshold threshold, ProcessLineConfigurationEntity entity) {
		entity.setProcessLineConfigurationId(CommonFunction.covertToInteger(threshold.getProcessLineTargetThresholdId()));
		entity.setMinimum(threshold.getMinimum());
		entity.setMaximum(threshold.getMaximum());
		entity.setCreatedBy(threshold.getCreatedBy());
		entity.setUpdatedBy(threshold.getUpdatedBy());
		entity.setStartDate(Timestamp.valueOf(threshold.getStartDate()));
		entity.setEndDate(Timestamp.valueOf(threshold.getEndDate()));
		entity.setCreatedDate(Timestamp.valueOf(threshold.getCreatedDate()));
		entity.setUpdatedDate(Timestamp.valueOf(threshold.getUpdatedDate()));
		entity.setActive(threshold.getActive());
		entity.setIsDefault(threshold.getIsDefaultConfig());
		entity.setMillId(threshold.getMillId());
		entity.setBuTypeId(threshold.getBuType().getBuTypeId());
		entity.setProcessLineId(threshold.getProcessLine().getProcessLineId());
		entity.setKpiId(threshold.getKpiId());
		return entity;
	}
}
