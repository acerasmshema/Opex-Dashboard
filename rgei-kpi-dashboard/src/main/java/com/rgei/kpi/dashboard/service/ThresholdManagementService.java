package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.ProductionThreshold;

public interface ThresholdManagementService {

	List<ProductionThreshold> getProductionTargetsByMillId(Integer millId);

}
