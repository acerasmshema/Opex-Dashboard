package com.rgei.kpi.dashboard.service;

import java.util.List;

import com.rgei.kpi.dashboard.response.model.AnnualConfiguration;
import com.rgei.kpi.dashboard.response.model.ProcessLineTargetThreshold;
import com.rgei.kpi.dashboard.response.model.ProductionThreshold;

public interface ThresholdManagementService {

	List<ProductionThreshold> getProductionTargetsByMillId(Integer millId);

	List<ProcessLineTargetThreshold> getProcessLineTargets(Integer millId, Integer buTypeId, Integer kpiId);


	void createProductionTarget(ProductionThreshold productionTarget);

	void updateProductionTarget(ProductionThreshold productionTarget);


	void updateProcessLineTarget(ProcessLineTargetThreshold targetThreshold);

	void createProcessLineTargets(ProcessLineTargetThreshold processLineTargetThreshold);
	
	List<AnnualConfiguration> getAnnualConfiguration(Integer millId);

	void createAnnualConfiguration(AnnualConfiguration annualConfiguration);

	void updateAnnualConfiguration(AnnualConfiguration annualConfiguration);

	
}
