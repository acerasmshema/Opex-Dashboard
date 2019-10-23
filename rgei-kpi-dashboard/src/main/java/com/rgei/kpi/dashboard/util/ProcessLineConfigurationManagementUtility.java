package com.rgei.kpi.dashboard.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
		ProcessLineConfigurationEntity processLineConfigurationEntity = new ProcessLineConfigurationEntity();
		if(processLineTargetThreshold!=null) {
			processLineConfigurationEntity.setMillId(processLineTargetThreshold.getMillId());
			processLineConfigurationEntity.setBuTypeId(processLineTargetThreshold.getBuType().getBuTypeId());
			processLineConfigurationEntity.setKpiId(processLineTargetThreshold.getKpiId());
			processLineConfigurationEntity.setProcessLineId(processLineTargetThreshold.getProcessLine().getProcessLineId());
			processLineConfigurationEntity.setMaximum(processLineTargetThreshold.getMaximum());
			processLineConfigurationEntity.setMinimum(processLineTargetThreshold.getMinimum());
			processLineConfigurationEntity.setThreshold(processLineTargetThreshold.getThreshold());
			processLineConfigurationEntity.setStartDate(Utility.stringToDateConvertor(processLineTargetThreshold.getStartDate(), DashboardConstant.FORMAT));
			processLineConfigurationEntity.setEndDate(Utility.stringToDateConvertor(processLineTargetThreshold.getEndDate(), DashboardConstant.FORMAT));
			processLineConfigurationEntity.setCreatedBy(processLineTargetThreshold.getCreatedBy());
			processLineConfigurationEntity.setCreatedDate(new Date());
			processLineConfigurationEntity.setUpdatedBy(processLineTargetThreshold.getUpdatedBy());
			processLineConfigurationEntity.setUpdatedDate(new Date());
			processLineConfigurationEntity.setIsDefault(Boolean.FALSE);
			processLineConfigurationEntity.setActive(Boolean.TRUE);
			}
		return processLineConfigurationEntity;
	}

	public static ProcessLineConfigurationEntity getProcessLineConfigurationEntity(ProcessLineTargetThreshold threshold, ProcessLineConfigurationEntity entity) {
		entity.setProcessLineConfigurationId(CommonFunction.covertToInteger(threshold.getProcessLineTargetThresholdId()));
		entity.setMinimum(threshold.getMinimum());
		entity.setMaximum(threshold.getMaximum());
		entity.setUpdatedBy(threshold.getUpdatedBy());
		entity.setStartDate(Timestamp.valueOf(threshold.getStartDate()));
		entity.setEndDate(Timestamp.valueOf(threshold.getEndDate()));
		entity.setUpdatedDate(new Date());
		entity.setActive(Boolean.TRUE);
		entity.setIsDefault(threshold.getIsDefaultConfig());
		entity.setMillId(threshold.getMillId());
		entity.setBuTypeId(threshold.getBuType().getBuTypeId());
		entity.setProcessLineId(threshold.getProcessLine().getProcessLineId());
		entity.setKpiId(threshold.getKpiId());
		return entity;
	}
}
