package com.rgei.kpi.dashboard.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.KpiConfigurationRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineConfigurationRepository;
import com.rgei.kpi.dashboard.repository.RgeUserEntityRepository;
import com.rgei.kpi.dashboard.response.model.ProcessLineTargetThreshold;
import com.rgei.kpi.dashboard.response.model.ProductionThreshold;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.util.ProcessLineConfigurationManagementUtility;
import com.rgei.kpi.dashboard.util.ThresholdManagementUtility;
import com.rgei.kpi.dashboard.util.UserManagementUtility;

@Service
public class ThresholdManagementServiceImpl implements ThresholdManagementService{

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ThresholdManagementServiceImpl.class);
	
	@Resource
	KpiConfigurationRepository kpiConfigurationRepository;
	
	@Resource
	ProcessLineConfigurationRepository processLineConfigurationRepository;
	
	@Override
	public List<ProductionThreshold> getProductionTargetsByMillId(Integer millId) {
		logger.info("Get production threshold by mill id ", millId);
		List<KpiConfigurationEntity> thresholdConfigurations = kpiConfigurationRepository.findByMillId(millId);
		if (thresholdConfigurations != null && !thresholdConfigurations.isEmpty()) {
			return ThresholdManagementUtility.convertToProductionThreshold(thresholdConfigurations);
		}
		throw new RecordNotFoundException("Users list not available in database for Mill Id : " + millId);
	}

	@Override
	public List<ProcessLineTargetThreshold> getProcessLineTargets(Integer millId, Integer buTypeId, Integer kpiId) {
		logger.info("Get process line threshold",  millId);
		List<ProcessLineConfigurationEntity> processLineConfigurationEntity = processLineConfigurationRepository.findByMillIdAndBuTypeIdAndKpiId(millId,buTypeId,kpiId);
		if (processLineConfigurationEntity != null && !processLineConfigurationEntity.isEmpty()) {
			return ProcessLineConfigurationManagementUtility.convertToProcessLineThreshold(processLineConfigurationEntity);
		}
		throw new RecordNotFoundException("Data not available in database for Mill Id : " + millId);
	}

}
