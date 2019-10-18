package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;
import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;
import com.rgei.kpi.dashboard.exception.RecordNotUpdatedException;
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
			configEntity.setBuTypeId(productionTarget.getBuType().getBuId());
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
			configEntity.setBuTypeId(productionTarget.getBuType().getBuId());
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
}
