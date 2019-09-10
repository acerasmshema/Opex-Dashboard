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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.KpiTypeEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.KPICategoryEntityRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineRepository;
import com.rgei.kpi.dashboard.response.model.KpiTypeExtendedResponse;
import com.rgei.kpi.dashboard.response.model.KpiTypeResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLinesResponse;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverter;
import com.rgei.kpi.dashboard.util.KPICategoryConverter;

@Service
public class KPICategoryServiceImpl implements KPICategoryService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KPICategoryServiceImpl.class);

	@Resource
	private KPICategoryEntityRepository kpiCategoryEntityRepository;

	@Resource
	private ProcessLineRepository processLineRepository;

	@Override
	public List<KpiTypeResponse> getKPICategory(Integer kpiCategoryId) {
		logger.info("Fetching KPI Category for id", kpiCategoryId);
		List<KpiTypeEntity> kpiTypeEntities = kpiCategoryEntityRepository.findByKpiCategoryId(kpiCategoryId);
		return KPICategoryConverter.covertToResponse(kpiTypeEntities);
	}

	@Override
	public List<ProcessLinesResponse> getProcessLines(Integer kpiId, Integer millId) {
		logger.info("Fetching Process Lines KPI id", kpiId);
		boolean status = Boolean.TRUE;
		try {
			List<ProcessLineEntity> processLinesEntities = processLineRepository.findByKpiId(kpiId, status, millId);
			return KPICategoryConverter.covertToProcessLineResponse(processLinesEntities);
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while retrieving process lines not found for Kpi Id : "+kpiId+" and Mill Id : "+millId);
		}
	}

	@Override
	public List<KpiTypeExtendedResponse> getKPICategoryDetails(List<String> kpiCategoryId) {
		logger.info("Fetching KPI Category for ids", kpiCategoryId);
		List<KpiTypeEntity> kpiTypes = new ArrayList<>();
		Collections.sort(kpiCategoryId);
		for (String categoryId : kpiCategoryId) {
			try {
				List<KpiTypeEntity> findByKpiCategoryId = kpiCategoryEntityRepository
						.findByKpiCategoryId(DailyKpiPulpConverter.covertToInteger(categoryId));
				kpiTypes.addAll(findByKpiCategoryId);
			} catch (Exception e) {
				throw new RecordNotFoundException("Record not found for Category Id : " + categoryId);
			}
		}
		if (kpiTypes.isEmpty()) {
			throw new RecordNotFoundException("Records not found for Category Ids [] : " + kpiCategoryId);
		}
		return KPICategoryConverter.convertToResponse(kpiTypes);
	}
}
