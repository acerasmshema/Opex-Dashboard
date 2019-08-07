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
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.KpiCategoryResponse;
import com.rgei.kpi.dashboard.response.model.KpiDashboardCategoryRequest;
import com.rgei.kpi.dashboard.service.KpiDashboardCategoryService;

/**
 * @author dixit.sharma
 *
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class KpiDashboardCategoryController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiDashboardCategoryController.class);
	
	@Resource
	KpiDashboardCategoryService kpiDashboardCategoryService;

	@PostMapping(value = "/v1/kpi_category/selected_kpi_process_lines")
	public ResponseEntity<List<DateRangeResponse>> getKpiCategoryData(
			@RequestBody KpiDashboardCategoryRequest kpiDashboardCategoryRequest) {
		logger.info("Inside KPIDashboardCategoryController to fetch KPI data", kpiDashboardCategoryRequest);
		List<DateRangeResponse> response = kpiDashboardCategoryService.getKpiCategoryData(kpiDashboardCategoryRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v2/kpi_category/selected_kpi_process_lines")
	public ResponseEntity<List<DateRangeResponse>> getKpiCategoryLineChartData(
			@RequestBody KpiDashboardCategoryRequest kpiDashboardCategoryRequest) {
		logger.info("Inside KPIDashboardCategoryController to fetch KPI data for line charts", kpiDashboardCategoryRequest);
		List<DateRangeResponse> response = kpiDashboardCategoryService.getKpiCategoryLineChartData(kpiDashboardCategoryRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/kpi_category/selected_kpi_grid_data")
	public ResponseEntity<List<List<Map<String,Object>>>> getKpiCategoryDownloadGridData(
			@RequestBody KpiDashboardCategoryRequest kpiDashboardCategoryRequest) {
		logger.info("Inside KPIDashboardCategoryController to fetch KPI data for grid", kpiDashboardCategoryRequest);
		List<List<Map<String,Object>>> response = kpiDashboardCategoryService.getKpiCategoryDownloadGridData(kpiDashboardCategoryRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/kpi_category/yesterday_all_process_lines_data")
	public ResponseEntity<List<KpiCategoryResponse>> getYesterdayValuesForKpiCategory(@RequestHeader(value="kpiCategoryId") Integer kpiCategoryId,@RequestHeader(value="millId") Integer millId ) {
		logger.info("Inside KPIDashboardCategoryController to fetch yesterday values for kpi category", kpiCategoryId);
		List<KpiCategoryResponse> response = kpiDashboardCategoryService.getYesterdayValuesForKpiCategory(kpiCategoryId, millId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
