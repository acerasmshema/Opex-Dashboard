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
import com.rgei.kpi.dashboard.response.model.KpiCategoryTargetDaysRequest;
import com.rgei.kpi.dashboard.response.model.ProcessLineDailyTargetResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineResponse;
import com.rgei.kpi.dashboard.service.MillBuKpiCategoryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class MillBuKpiCategoryController {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(MillBuKpiCategoryController.class);

	@Resource
	private MillBuKpiCategoryService millBuKpiCategoryService;

	@ApiOperation(value = "getYesterdayProcessLineSum", notes = "Retrieve yesterday process line total", response = ProcessLineResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping(value = "/v1/yesterday/total_process_line")
	public ResponseEntity<ProcessLineResponse> getYesterdayProcessLineSum(
			@RequestHeader(value = "millId") String millId, @RequestHeader(value = "buId") String buId,
			@RequestHeader(value = "kpiCategoryId") String kpiCategoryId,
			@RequestHeader(value = "kpiId") String kpiId) {
		logger.info("Fetching yesterday process line total");
		ProcessLineResponse response = millBuKpiCategoryService.yesterdayAvgProductionLine(millId, buId, kpiCategoryId,
				kpiId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "getDailyKpiPulpDataForAreaChart", notes = "Retrieve daily kpi pulp data for area chart")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@PostMapping(value = "/v1/kpi_category/save_target_days")
	public ResponseEntity<HttpStatus> getDailyKpiPulpDataForAreaChart(
			@RequestBody KpiCategoryTargetDaysRequest kpiCategoryTargetDaysRequest) {
		logger.info("Fetching daily kpi pulp data for area chart", kpiCategoryTargetDaysRequest);
		millBuKpiCategoryService.saveKpiCategoryTargetDaysRequest(kpiCategoryTargetDaysRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "getProcessLineDailyTarget", notes = "Retrieve process line daily target for mill, bu and kpi category ids", response = ProcessLineDailyTargetResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping(value = "/v1/process_line/get_daily_target")
	public ResponseEntity<ProcessLineDailyTargetResponse> getProcessLineDailyTarget(
			@RequestHeader(value = "millId") String millId, @RequestHeader(value = "buId") String buId,
			@RequestHeader(value = "kpiCategoryId") String kpiCategoryId) {
		logger.info("Fetching process line daily target");
		ProcessLineDailyTargetResponse response = millBuKpiCategoryService.getProcessLineDailyTarget(millId, buId,
				kpiCategoryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
