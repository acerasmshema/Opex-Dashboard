package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;
import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;
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
			threshold.setMinimum(Double.parseDouble(CommonFunction.getString(config.getMinimum())));
			threshold.setMaximum(Double.parseDouble(CommonFunction.getString(config.getMaximum())));
			threshold.setStartDate(Utility.dateToStringConvertor(config.getStartDate(), DashboardConstant.EXTENDED_DATE_FORMAT));
			threshold.setEndDate(Utility.dateToStringConvertor(config.getEndDate(), DashboardConstant.EXTENDED_DATE_FORMAT));
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

}
