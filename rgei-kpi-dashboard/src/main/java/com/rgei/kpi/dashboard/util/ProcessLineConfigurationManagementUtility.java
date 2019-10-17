package com.rgei.kpi.dashboard.util;

import java.sql.Timestamp;
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
			threshold.setProcessLineTargetThresholdId(config.getProcessLineConfigurationId().toString());
			threshold.setBuType(CommonFunction.convertBUEntityToResponse(config.getBuType()));
			threshold.setMillId(config.getMillId());
			threshold.setKpiId(config.getKpiId());
			threshold.setProcessLine(CommonFunction.convertProcessLineEntityToResponse(config.getProcessLine()));
			threshold.setThreshold(config.getThreshold());
			threshold.setMinimum(config.getMinimum());
			threshold.setMaximum(config.getMaximum());
			threshold.setStartDate(Utility.dateToStringConvertor(config.getStartDate(), DashboardConstant.THRESHOLD_DATE_FORMAT));
			threshold.setEndDate(Utility.dateToStringConvertor(config.getEndDate(), DashboardConstant.THRESHOLD_DATE_FORMAT));
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
	
	
	
	public static ProcessLineConfigurationEntity convertToProcessLineEntity(
			ProcessLineTargetThreshold processLineTargetThreshold) {
		ProcessLineConfigurationEntity ProcessLineConfigurationEntity = new ProcessLineConfigurationEntity();
		if(processLineTargetThreshold!=null) {
			ProcessLineConfigurationEntity.setMillId(processLineTargetThreshold.getMillId());
			ProcessLineConfigurationEntity.setBuTypeId(processLineTargetThreshold.getBuType().getBuTypeId());
			ProcessLineConfigurationEntity.setKpiId(processLineTargetThreshold.getKpiId());
			ProcessLineConfigurationEntity.setProcessLineId(processLineTargetThreshold.getProcessLine().getProcessLineId());
			ProcessLineConfigurationEntity.setMaximum(processLineTargetThreshold.getMaximum());
			ProcessLineConfigurationEntity.setMinimum(processLineTargetThreshold.getMinimum());
			ProcessLineConfigurationEntity.setThreshold(processLineTargetThreshold.getThreshold());
			ProcessLineConfigurationEntity.setStartDate( Utility.stringToDateConvertor(processLineTargetThreshold.getStartDate(), DashboardConstant.FORMAT));
			ProcessLineConfigurationEntity.setEndDate( Utility.stringToDateConvertor(processLineTargetThreshold.getEndDate(), DashboardConstant.FORMAT));
			ProcessLineConfigurationEntity.setActive(processLineTargetThreshold.getActive());
			ProcessLineConfigurationEntity.setCreatedBy(processLineTargetThreshold.getCreatedBy());
			ProcessLineConfigurationEntity.setCreatedDate( Utility.stringToDateConvertor(processLineTargetThreshold.getCreatedDate(), DashboardConstant.FORMAT));
			ProcessLineConfigurationEntity.setUpdatedBy(processLineTargetThreshold.getUpdatedBy());
			ProcessLineConfigurationEntity.setUpdatedDate( Utility.stringToDateConvertor(processLineTargetThreshold.getUpdatedDate(), DashboardConstant.FORMAT));
			ProcessLineConfigurationEntity.setIsDefault(Boolean.FALSE);
			
			}
		return ProcessLineConfigurationEntity;
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
