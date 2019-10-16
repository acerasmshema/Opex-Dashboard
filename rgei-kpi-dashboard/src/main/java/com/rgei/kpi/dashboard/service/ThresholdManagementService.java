package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.ProcessLineTargetThreshold;
import com.rgei.kpi.dashboard.response.model.ProductionThreshold;

public interface ThresholdManagementService {

	List<ProductionThreshold> getProductionTargetsByMillId(Integer millId);

	List<ProcessLineTargetThreshold> getProcessLineTargets(Integer millId, Integer buTypeId,
			Integer kpiId);

}
