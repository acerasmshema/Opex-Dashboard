package com.rgei.kpi.dashboard.service;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;
import com.rgei.kpi.dashboard.entities.MillBuKpiCategoryEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;
import com.rgei.kpi.dashboard.exception.DateRangeAlreadyExistException;
import com.rgei.kpi.dashboard.exception.RecordNotCreatedException;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.exception.RecordNotUpdatedException;
import com.rgei.kpi.dashboard.repository.KpiConfigurationRepository;
import com.rgei.kpi.dashboard.repository.MillBuKpiCategoryEntityRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineConfigurationRepository;
import com.rgei.kpi.dashboard.response.model.MillBuKpiCategoryResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineTargetThreshold;
import com.rgei.kpi.dashboard.response.model.ProductionThreshold;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.ProcessLineConfigurationManagementUtility;
import com.rgei.kpi.dashboard.util.ThresholdManagementUtility;
import com.rgei.kpi.dashboard.util.Utility;



@Service
public class ThresholdManagementServiceImpl implements ThresholdManagementService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ThresholdManagementServiceImpl.class);

	@Resource
	KpiConfigurationRepository kpiConfigurationRepository;

	@Resource
	ProcessLineConfigurationRepository processLineConfigurationRepository;
	
	@Resource
	MillBuKpiCategoryEntityRepository millBuKpiCategoryRepository;
	
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
		logger.info("Get process line threshold", millId);
		List<ProcessLineConfigurationEntity> processLineConfigurationEntity = null;
		if (buTypeId != null) {
			processLineConfigurationEntity = processLineConfigurationRepository.findByMillIdAndBuTypeIdAndKpiId(millId,
					buTypeId, kpiId);
		} else {
			processLineConfigurationEntity = processLineConfigurationRepository.findByMillIdAndKpiId(millId, kpiId);
		}

		if (processLineConfigurationEntity != null && !processLineConfigurationEntity.isEmpty()) {
			return ProcessLineConfigurationManagementUtility
					.convertToProcessLineThreshold(processLineConfigurationEntity);
		}
		throw new RecordNotFoundException("Data not available in database for Mill Id : " + millId);
	}


	@Override
	public void updateProcessLineTarget(ProcessLineTargetThreshold targetThreshold) {
		logger.info("Update process line target for MillId : ",  targetThreshold.getMillId());
		validateDateRange(targetThreshold);
		ProcessLineConfigurationEntity processLineConfigEntity = null;
		if(targetThreshold != null && targetThreshold.getBuType() != null && targetThreshold.getProcessLine() != null) {
			ProcessLineConfigurationEntity entity = processLineConfigurationRepository.findByProcessLineConfigurationId(CommonFunction.covertToInteger(targetThreshold.getProcessLineTargetThresholdId()));
			if(entity != null) {
				processLineConfigEntity = ProcessLineConfigurationManagementUtility.getProcessLineConfigurationEntity(targetThreshold, entity);
				try {
					processLineConfigurationRepository.save(processLineConfigEntity);
				}catch(RuntimeException e) {
					throw new RecordNotUpdatedException("Error while updating process line target configuration :" + processLineConfigEntity);
				}
			}else {
				throw new RecordNotFoundException("Process line target not found against target id  :" + targetThreshold.getProcessLineTargetThresholdId());
			}
		}else {
			throw new RecordNotFoundException("Process line target not found against target id  :" + targetThreshold.getProcessLineTargetThresholdId());
		}
	}

	public void createProcessLineTargets(ProcessLineTargetThreshold processLineTargetThreshold) {
		logger.info("Inside service call to create new threshold data : " + processLineTargetThreshold);
	
		validateDateRange(processLineTargetThreshold);
		ProcessLineConfigurationEntity processLineConfigurationEntity = ProcessLineConfigurationManagementUtility.convertToProcessLineEntity(processLineTargetThreshold);
		try {
			processLineConfigurationRepository.save(processLineConfigurationEntity);
		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while creating:" + processLineConfigurationEntity);
		}
		
	}

	private void validateDateRange(ProcessLineTargetThreshold processLineTargetThreshold)  {
		ProcessLineConfigurationEntity processLineConfigurationEntity=null;
		if(processLineTargetThreshold!=null) {
			 processLineConfigurationEntity=processLineConfigurationRepository.getAllBetweenDates(Utility.stringToDateConvertor(processLineTargetThreshold.getStartDate(), DashboardConstant.FORMAT),Utility.stringToDateConvertor(processLineTargetThreshold.getEndDate(), DashboardConstant.FORMAT),processLineTargetThreshold.getMillId(),processLineTargetThreshold.getBuType().getBuTypeId(),processLineTargetThreshold.getKpiId(),processLineTargetThreshold.getProcessLine().getProcessLineId());
			 if(processLineConfigurationEntity!=null)
				 throw new DateRangeAlreadyExistException("Record Already exist for same date range");
		}
		
	}
	

	public void createProductionTarget(ProductionThreshold productionTarget) {
		logger.info("Inside service call to create production target for request : " + productionTarget);
		validateProductionDateRange(productionTarget);
		try {
			KpiConfigurationEntity configEntity = ThresholdManagementUtility
					.createConfigurationEntity(productionTarget);
			kpiConfigurationRepository.save(configEntity);
		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while creating production target  :" + productionTarget);
		}
	}

	private void validateProductionDateRange(ProductionThreshold productionThreshold)  {
		KpiConfigurationEntity kpiConfigurationEntity=null;
		if(productionThreshold!=null) {
			kpiConfigurationEntity=kpiConfigurationRepository.getAllBetweenDates(Utility.stringToDateConvertor(productionThreshold.getStartDate(), DashboardConstant.FORMAT),Utility.stringToDateConvertor(productionThreshold.getEndDate(), DashboardConstant.FORMAT),productionThreshold.getMillId(),productionThreshold.getBuType().getBuTypeId(),productionThreshold.getKpiId());
			 if(kpiConfigurationEntity!=null)
				 throw new DateRangeAlreadyExistException("Record Already exist for same date range");
		}
	}
	
	@Override
	public void updateProductionTarget(ProductionThreshold productionTarget) {

		logger.info("Inside service call to update production target for request : " + productionTarget);
		validateProductionDateRange(productionTarget);
		try {
			if (productionTarget != null) {
				KpiConfigurationEntity kpiConfigurationEntity = kpiConfigurationRepository.findByKpiConfigurationId(Integer.parseInt(productionTarget.getProductionThresholdId()));
				if (kpiConfigurationEntity != null) {
					KpiConfigurationEntity updatedThreshold = ThresholdManagementUtility.updateFetchedUserEntity(productionTarget, kpiConfigurationEntity);
					kpiConfigurationRepository.save(updatedThreshold);
				} else {
					throw new RecordNotFoundException("User not found against configuration id  :" + productionTarget.getProductionThresholdId());
				}
			}
		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while updating production target  :" + productionTarget);
		}
			
	}

	@Override
	public List<MillBuKpiCategoryResponse> getAnnualConfiguration(Integer millId, Integer buId) {
		logger.info("Get annual configuration for MillId : ",  millId);
		List<MillBuKpiCategoryEntity> entities = millBuKpiCategoryRepository.findAll(millId, buId);
		if(Objects.nonNull(entities) && !entities.isEmpty()) {
			return ThresholdManagementUtility.getMillBuResponseList(entities);
		}else {
			throw new RecordNotFoundException("Annual configuration not found for mill Id :" + millId);
		}
	}

	@Override
	public void createAnnualConfiguration(MillBuKpiCategoryResponse millBuKpiCategoryResponse) {
		logger.info("Create annual configuration for MillId : ",  millBuKpiCategoryResponse.getMillId());
		try {
			MillBuKpiCategoryEntity entity = ThresholdManagementUtility
					.createMillBuKpiEntity(millBuKpiCategoryResponse);
			millBuKpiCategoryRepository.save(entity);
		} catch (RuntimeException e) {
			throw new RecordNotCreatedException("Error while creating annual configuration  :" + millBuKpiCategoryResponse);
		}
	}
}
