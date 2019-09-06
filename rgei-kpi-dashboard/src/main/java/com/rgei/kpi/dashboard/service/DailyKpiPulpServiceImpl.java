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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.DailyKpiPulpEntity;
import com.rgei.kpi.dashboard.entities.MillBuKpiCategoryEntity;
import com.rgei.kpi.dashboard.repository.DailyKpiPulpEntityRepository;
import com.rgei.kpi.dashboard.repository.MillBuKpiCategoryEntityRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineRepository;
import com.rgei.kpi.dashboard.response.model.ProcessLineAnnualResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineSeries;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverter;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverterRZ;

@Service
public class DailyKpiPulpServiceImpl implements DailyKpiPulpService{
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(DailyKpiPulpServiceImpl.class);
	
	@Resource
	DailyKpiPulpEntityRepository dailyKpiPulpEntityRepository;
	
	@Resource
	ProcessLineRepository processLineRepository;
	
	@Resource
	MillBuKpiCategoryEntityRepository millBuKpiCategoryEntityRepository;

	@Override
	public ProcessLineAnnualResponse getAnnualProcessLine(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Inside DailyKpiPulpServiceImpl to fetch annual data for process lines");
		ProcessLineAnnualResponse processLineAnnualResponse=null;
		MillBuKpiCategoryEntity  millBuKpiCategoryEntity  = millBuKpiCategoryEntityRepository.find(DailyKpiPulpConverter.covertToInteger(millId),
				DailyKpiPulpConverter.covertToInteger(kpiCategoryId), DailyKpiPulpConverter.covertToInteger(buId));
		logger.info("Entity data from MillBuKpiCategoryEntity", millBuKpiCategoryEntity);
		List<DailyKpiPulpEntity> dailyKpiEntities = dailyKpiPulpEntityRepository.readForDateRange(DailyKpiPulpConverter.getCurrentYearDate(), DailyKpiPulpConverter.getYesterdayDate(),
				DailyKpiPulpConverter.covertToInteger(millId), DailyKpiPulpConverter.covertToInteger(buId), 
				DailyKpiPulpConverter.covertToInteger(kpiCategoryId), DailyKpiPulpConverter.covertToInteger(kpiId));
		if(millId.equals(DashboardConstant.KRC)) {
			processLineAnnualResponse = DailyKpiPulpConverter.prePareResponse(millBuKpiCategoryEntity, dailyKpiEntities);
		}
		else if(millId.equals(DashboardConstant.RZ)) {
			processLineAnnualResponse = DailyKpiPulpConverterRZ.prePareResponse(millBuKpiCategoryEntity, dailyKpiEntities);
		}
		return processLineAnnualResponse;
	}

	@Override
	public List<ProcessLineSeries> calculateDateWiseTotalProcessLine(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Calculating date wise total for process lines");
		List<ProcessLineSeries> processLineSeries = null;
		List<DailyKpiPulpEntity> dailyKpiEntities = dailyKpiPulpEntityRepository.readForDateRange(DailyKpiPulpConverter.getCurrentYearDate(), DailyKpiPulpConverter.getYesterdayDate(),
				DailyKpiPulpConverter.covertToInteger(millId), DailyKpiPulpConverter.covertToInteger(buId), 
				DailyKpiPulpConverter.covertToInteger(kpiCategoryId), DailyKpiPulpConverter.covertToInteger(kpiId));
		logger.info("Entity data from DailyKpiPulpEntity", dailyKpiEntities);
		if(millId.equals(DashboardConstant.KRC)) {
			processLineSeries = DailyKpiPulpConverter.prePareDailyTargetResponse(dailyKpiEntities);
		}
		else if(millId.equals(DashboardConstant.RZ)) {
			processLineSeries = DailyKpiPulpConverterRZ.prePareDailyTargetResponse(dailyKpiEntities);
		}
		return processLineSeries;
	}
	
	@Override
	public Long getActualTarget(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Fetching actual target for process lines");
		List<DailyKpiPulpEntity> dailyKpiEntities = dailyKpiPulpEntityRepository.readForDateRange(DailyKpiPulpConverter.getCurrentYearDate(), DailyKpiPulpConverter.getYesterdayDate(),
				DailyKpiPulpConverter.covertToInteger(millId), DailyKpiPulpConverter.covertToInteger(buId), 
				DailyKpiPulpConverter.covertToInteger(kpiCategoryId), DailyKpiPulpConverter.covertToInteger(kpiId));
		logger.info("Entity data from DailyKpiPulpEntity", dailyKpiEntities);
		return DailyKpiPulpConverter.calculateActualTarget(dailyKpiEntities);
	}
}
