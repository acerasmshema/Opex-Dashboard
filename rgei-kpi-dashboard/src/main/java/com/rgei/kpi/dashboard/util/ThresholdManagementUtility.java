package com.rgei.kpi.dashboard.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.AnnualConfigurationEntity;
import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;
import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.exception.RecordNotUpdatedException;
import com.rgei.kpi.dashboard.response.model.AnnualConfiguration;
import com.rgei.kpi.dashboard.response.model.BuTypeResponse;
import com.rgei.kpi.dashboard.response.model.ProductionThreshold;

public class ThresholdManagementUtility {
	private ThresholdManagementUtility() {
	}

	public static List<ProductionThreshold> convertToProductionThreshold(
			List<KpiConfigurationEntity> kpiConfigurationEntity) {
		List<ProductionThreshold> response = new ArrayList<>();
		for(KpiConfigurationEntity config : kpiConfigurationEntity) {
			ProductionThreshold threshold = new ProductionThreshold();
			threshold.setProductionThresholdId(CommonFunction.getString(config.getKpiConfigurationId()));
			threshold.setThreshold(Double.parseDouble(CommonFunction.getString(config.getThreshold())));
			threshold.setBuType(fetchBuType(config.getBuType()));
			threshold.setMinimum(0.0);
			threshold.setMaximum(Double.parseDouble(CommonFunction.getString(config.getMaximum())));
			threshold.setKpiId(config.getKpiId());
			threshold.setMillId(config.getMillId());
			threshold.setStartDate(Utility.dateToStringConvertor(config.getStartDate(), DashboardConstant.THRESHOLD_DATE_FORMAT));
			threshold.setEndDate(Utility.dateToStringConvertor(config.getEndDate(), DashboardConstant.THRESHOLD_DATE_FORMAT));
			threshold.setIsDefault(config.getIsDefault());
			threshold.setActive(config.getActive());
			threshold.setCreatedBy(CommonFunction.getString(config.getCreatedBy()));
			threshold.setCreatedDate(CommonFunction.getString(config.getCreatedDate()));
			threshold.setUpdatedBy(CommonFunction.getString(config.getUpdatedBy()));
			threshold.setUpdatedDate(CommonFunction.getString(config.getUpdatedDate()));
			response.add(threshold);
		}
		
		return response;
	}

	private static BuTypeResponse fetchBuType(BusinessUnitTypeEntity buType) {
		BuTypeResponse buTypeResponse = null;
		if(Objects.nonNull(buType)) {
			buTypeResponse = new BuTypeResponse();
			buTypeResponse.setBuTypeId(buType.getBusinessUnitTypeId());
			buTypeResponse.setBuId(buType.getBusinessUnit().getBusinessUnitId());
			buTypeResponse.setBuTypeName(buType.getBusinessUnitTypeName());
			buTypeResponse.setBuTypeCode(buType.getBusinessUnitTypeCode());
		}
		return buTypeResponse;
	}

	public static KpiConfigurationEntity createConfigurationEntity(ProductionThreshold productionTarget) {
		KpiConfigurationEntity configEntity = new KpiConfigurationEntity();
		if(Objects.nonNull(productionTarget)) {
			configEntity.setMinimum(0.0);
			configEntity.setMaximum(productionTarget.getMaximum());
			configEntity.setThreshold(productionTarget.getThreshold());
			configEntity.setBuTypeId(productionTarget.getBuType().getBuTypeId());
			configEntity.setKpiId(productionTarget.getKpiId());
			configEntity.setMillId(productionTarget.getMillId());
			configEntity.setStartDate(Utility.stringToDateConvertor(productionTarget.getStartDate(), DashboardConstant.FORMAT));
			configEntity.setEndDate(Utility.stringToDateConvertor(productionTarget.getEndDate(), DashboardConstant.FORMAT));
			configEntity.setIsDefault(Boolean.FALSE);
			configEntity.setActive(Boolean.TRUE);
			configEntity.setCreatedBy(productionTarget.getCreatedBy());
			configEntity.setCreatedDate(new Date());
			configEntity.setUpdatedBy(productionTarget.getUpdatedBy());
			configEntity.setUpdatedDate(new Date());
		}
		return configEntity;
	}

	public static KpiConfigurationEntity updateFetchedUserEntity(ProductionThreshold productionTarget,
			KpiConfigurationEntity configEntity) {
		Date date = new Date();
		try {
			configEntity.setThreshold(productionTarget.getThreshold());
			configEntity.setMinimum(0.0);
			configEntity.setMaximum(productionTarget.getMaximum());
			configEntity.setThreshold(productionTarget.getThreshold());
			configEntity.setBuTypeId(productionTarget.getBuType().getBuTypeId());
			configEntity.setKpiId(productionTarget.getKpiId());
			configEntity.setMillId(productionTarget.getMillId());
			configEntity.setStartDate(Utility.stringToDateConvertor(productionTarget.getStartDate(), DashboardConstant.FORMAT));
			configEntity.setEndDate(Utility.stringToDateConvertor(productionTarget.getEndDate(), DashboardConstant.FORMAT));
			configEntity.setIsDefault(productionTarget.getIsDefault());
			configEntity.setActive(Boolean.TRUE);
			configEntity.setUpdatedBy(productionTarget.getUpdatedBy());
			configEntity.setUpdatedDate(date);
		} catch (Exception e) {
			throw new RecordNotUpdatedException("Error while updating production target :" + productionTarget);
		}
		return configEntity;
	}
	
	public static List<AnnualConfiguration> fetchAnnualConfigurations(List<AnnualConfigurationEntity> entities) {
		List<AnnualConfiguration> responseList = new ArrayList<>();
		AnnualConfiguration resp = null;
		for(AnnualConfigurationEntity entity : entities) {
			resp = new AnnualConfiguration();
			resp.setAnnualConfigurationId(entity.getAnnualConfigurationId());
			resp.setYear(entity.getYear());
			resp.setWorkingDays(entity.getWorkingDays());
			resp.setBuType(fetchBuType(entity.getBuType()));
			resp.setAnnualTarget(entity.getAnnualTarget());
			resp.setThreshold(entity.getThreshold());
			resp.setMillId(entity.getMillId());
			resp.setKpiId(entity.getKpiId());
			resp.setIsDefault(entity.getIsDefault());
			resp.setActive(entity.getActive());
			resp.setCreatedBy(entity.getCreatedBy());
			resp.setCreatedDate(CommonFunction.getString(entity.getCreatedDate()));
			resp.setUpdatedBy(entity.getUpdatedBy());
			resp.setUpdatedDate(CommonFunction.getString(entity.getUpdatedDate()));				
			responseList.add(resp);
		}
		return responseList;
	}
	
	public static AnnualConfigurationEntity createAnnualConfigurationEntity(AnnualConfiguration config) {
		AnnualConfigurationEntity entity = new AnnualConfigurationEntity();
		if(Objects.nonNull(config)) {
			entity.setYear(config.getYear());
			entity.setWorkingDays(config.getWorkingDays());
			entity.setBuTypeId(config.getBuType().getBuTypeId());
			entity.setAnnualTarget(config.getAnnualTarget());
			entity.setThreshold(calculateThreshold(config));
			entity.setMillId(config.getMillId());
			entity.setKpiId(config.getKpiId());
			entity.setIsDefault(Boolean.FALSE);			
			entity.setActive(Boolean.TRUE);
			entity.setCreatedBy(config.getCreatedBy());
			entity.setCreatedDate(new Date());
			entity.setUpdatedBy(config.getUpdatedBy());
			entity.setUpdatedDate(new Date());
		}
		return entity;
	}

	private static Double calculateThreshold(AnnualConfiguration config) {
		Double threshold = null;
		if(null != config.getAnnualTarget() && null != config.getWorkingDays()) {
			threshold = BigDecimal.valueOf(Double.valueOf(config.getAnnualTarget())/config.getWorkingDays()).setScale(0, RoundingMode.CEILING).doubleValue();
		}else {
			throw new RecordNotFoundException("Annual target or working days missing in request"+ config);
		}
		return threshold;
	}

	public static AnnualConfigurationEntity updateFetchedAnnualConfigEntity(AnnualConfiguration annualConfiguration,
			AnnualConfigurationEntity entity) {
		if(Objects.nonNull(annualConfiguration)) {
			entity.setYear(annualConfiguration.getYear());
			entity.setWorkingDays(annualConfiguration.getWorkingDays());
			entity.setBuTypeId(annualConfiguration.getBuType().getBuTypeId());
			entity.setAnnualTarget(annualConfiguration.getAnnualTarget());
			entity.setThreshold(calculateThreshold(annualConfiguration));
			entity.setMillId(annualConfiguration.getMillId());
			entity.setKpiId(annualConfiguration.getKpiId());
			entity.setIsDefault(annualConfiguration.getIsDefault());			
			entity.setActive(Boolean.TRUE);
			entity.setUpdatedBy(annualConfiguration.getUpdatedBy());
			entity.setUpdatedDate(new Date());
		}
		return entity;
	}
}
