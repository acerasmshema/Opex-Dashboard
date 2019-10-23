/*******************************************************************************
 * Copyright (c) 2019 Ace Resource Advisory Services Sdn. Bhd., Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Ace Resource Advisory Services Sdn. Bhd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Ace Resource Advisory Services Sdn. Bhd.
 * 
 * Ace Resource Advisory Services Sdn. Bhd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Ace Resource Advisory Services Sdn. Bhd. SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 ******************************************************************************/
package com.rgei.kpi.dashboard.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.DailyKpiPulpEntity;
import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;
import com.rgei.kpi.dashboard.entities.MillBuKpiCategoryEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.exception.RecordNotCreatedException;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.DailyKpiPulpEntityRepository;
import com.rgei.kpi.dashboard.repository.KpiConfigurationRepository;
import com.rgei.kpi.dashboard.repository.MillBuKpiCategoryEntityRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineRepository;
import com.rgei.kpi.dashboard.response.model.KpiCategoryTargetDaysRequest;
import com.rgei.kpi.dashboard.response.model.ProcessLineDailyTargetResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineResponse;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverter;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverterRZ;
import com.rgei.kpi.dashboard.util.ProcessLineUtility;

@Service
public class MillBuKpiCategoryServiceImpl implements MillBuKpiCategoryService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(MillBuKpiCategoryServiceImpl.class);

	@Resource
	private MillBuKpiCategoryEntityRepository millBuKpiCategoryEntityRepository;

	@Resource
	DailyKpiPulpEntityRepository dailyKpiPulpEntityRepository;

	@Resource
	ProcessLineRepository processLineRepository;
	
	@Resource
	KpiConfigurationRepository kpiConfigurationRepository;

	@Override
	public ProcessLineResponse yesterdayAvgProductionLine(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Yesterday avg production line");
		ProcessLineResponse processLineResponse = null;
		Date yesterdayDate = ProcessLineUtility.getYesterdayDate();
		List<ProcessLineEntity> processLineEntites = processLineRepository
				.findByProcessLineNameIn(ProcessLineUtility.getRequestedProcessLines());
		MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(
				CommonFunction.covertToInteger(millId), CommonFunction.covertToInteger(kpiCategoryId),
				CommonFunction.covertToInteger(buId));
		List<DailyKpiPulpEntity> dailyKpiPulpEntities = dailyKpiPulpEntityRepository.readByRequestedParameters(
				CommonFunction.getYesterdayDate(), CommonFunction.covertToInteger(millId),
				CommonFunction.covertToInteger(buId), CommonFunction.covertToInteger(kpiCategoryId),
				CommonFunction.covertToInteger(kpiId));
		List<KpiConfigurationEntity> kpiConfigurationEntityList=kpiConfigurationRepository.findKpiConfigurationForCurrentDate(CommonFunction.covertToInteger(millId),CommonFunction.covertToInteger(buId),CommonFunction.covertToInteger(kpiId),yesterdayDate);
		KpiConfigurationEntity kpiConfigurationEntity=findApplicableConfiguration(kpiConfigurationEntityList);
		if (millBuKpiCategoryEntity == null) {
			throw new RecordNotFoundException("No MillBuKpiCategory entity found for millId : " + millId);
		}
		if (millId.equals(DashboardConstant.KRC)) {
			processLineResponse = DailyKpiPulpConverter.prePareResponse(processLineEntites, dailyKpiPulpEntities,
					millBuKpiCategoryEntity,kpiConfigurationEntity);
		} else if (millId.equals(DashboardConstant.RZ)) {
			processLineResponse = DailyKpiPulpConverterRZ.prePareResponse(processLineEntites, dailyKpiPulpEntities,
					millBuKpiCategoryEntity,kpiConfigurationEntity);
		}
		return processLineResponse;
	}

	private KpiConfigurationEntity findApplicableConfiguration(
			List<KpiConfigurationEntity> kpiConfigurationEntityList) {
		KpiConfigurationEntity kpiConfigurationApplicableEntity=null;
		for(KpiConfigurationEntity kpiConfigurationEntity:kpiConfigurationEntityList ) {
			
			if(kpiConfigurationEntity.getIsDefault()) {
				kpiConfigurationApplicableEntity= kpiConfigurationEntity;
			}else 
				kpiConfigurationApplicableEntity= kpiConfigurationEntity;
			}
		
		return kpiConfigurationApplicableEntity;
	}

	@Override
	public void saveKpiCategoryTargetDaysRequest(KpiCategoryTargetDaysRequest kpiCategoryTargetDaysRequest) {
		logger.info("Saving kpi category target days", kpiCategoryTargetDaysRequest);
		MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(
				kpiCategoryTargetDaysRequest.getMillId(), kpiCategoryTargetDaysRequest.getKpiCategoryId(),
				kpiCategoryTargetDaysRequest.getBuId());
		if (millBuKpiCategoryEntity != null) {
			try {
				millBuKpiCategoryEntity.setTargetDays(kpiCategoryTargetDaysRequest.getNoOfTargetDays());
				millBuKpiCategoryEntityRepository.save(millBuKpiCategoryEntity);
			} catch (Exception e) {
				throw new RecordNotCreatedException("Error while storing record for Kpi Category Target Days request");
			}
		} else {
			throw new RecordNotFoundException("No record found while storing Kpi Category Target Days request");
		}
	}

	@Override
	public ProcessLineDailyTargetResponse getProcessLineDailyTarget(String millId, String buId, String kpiCategoryId) {
		logger.info("Getting process line daily target");
		ProcessLineDailyTargetResponse response = new ProcessLineDailyTargetResponse();
		MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(
				CommonFunction.covertToInteger(millId), CommonFunction.covertToInteger(kpiCategoryId),
				CommonFunction.covertToInteger(buId));
		if (millBuKpiCategoryEntity != null && millBuKpiCategoryEntity.getDailyTarget() != null)
			response.setDailyTarget(millBuKpiCategoryEntity.getDailyTarget());
		else
			throw new RecordNotFoundException("No record found for process line daily target for mill Id : "+millId);
		return response;
	}

}