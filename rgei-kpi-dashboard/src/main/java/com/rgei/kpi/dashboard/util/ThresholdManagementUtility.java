package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.List;

import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;
import com.rgei.kpi.dashboard.response.model.ProductionThreshold;

public class ThresholdManagementUtility {
	private ThresholdManagementUtility() {
	}

	public static List<ProductionThreshold> convertToProductionThreshold(
			List<KpiConfigurationEntity> kpiConfigurationEntity) {
		List<ProductionThreshold> response = new ArrayList<>();
		for(KpiConfigurationEntity config : kpiConfigurationEntity) {
			ProductionThreshold threshold = new ProductionThreshold();
			threshold.setProductionThresholdId(config.getKpiConfigurationId().toString());
		}
		
		return response;
	}

}
