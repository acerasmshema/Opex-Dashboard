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
package com.rgei.kpi.dashboard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.response.model.KpiTypeExtendedResponse;
import com.rgei.kpi.dashboard.response.model.KpiTypeResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLinesResponse;
import com.rgei.kpi.dashboard.service.KPICategoryService;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverter;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class KPICategoryController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KPICategoryController.class);
	
	@Resource
	private KPICategoryService kpiCategoryService;
	
	@GetMapping(value = "/v1/kpi_category/get_kpi_type")
	public ResponseEntity<List<KpiTypeResponse>> getRequestedKPIType(@RequestHeader(value="kpiCategoryId") String kpiCategoryId){
		logger.info("Get kpi types by kpiCategoryId", kpiCategoryId);
		List<KpiTypeResponse> response = kpiCategoryService.getKPICategory(DailyKpiPulpConverter.covertToInteger(kpiCategoryId));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v2/kpi_category/get_kpi_type")
	public ResponseEntity<List<KpiTypeExtendedResponse>> getKPITypeDetails(@RequestHeader(value="kpiCategoryId") String kpiCategoryId){
		logger.info("Get kpi type by kpiCategoryId", kpiCategoryId);
		List<KpiTypeExtendedResponse> response = kpiCategoryService.getKPICategoryDetails(DailyKpiPulpConverter.covertToInteger(kpiCategoryId));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/kpi_category/get_process_lines")
	public ResponseEntity<List<ProcessLinesResponse>> getRequestedProcessLines(@RequestHeader(value="kpiId") String kpiId){
		logger.info("Get kpi type by kpiCategoryId", kpiId);
		List<ProcessLinesResponse> response = kpiCategoryService.getProcessLines(DailyKpiPulpConverter.covertToInteger(kpiId));
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
