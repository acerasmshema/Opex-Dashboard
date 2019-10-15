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
import com.rgei.kpi.dashboard.response.model.ProcessLinesResponse;
import com.rgei.kpi.dashboard.service.KPICategoryService;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverter;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/restCall")
public class KPICategoryController {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KPICategoryController.class);

	@Resource
	private KPICategoryService kpiCategoryService;

	@ApiOperation(value = "getKpiTypeDetails", notes = "Get kpi type by kpiCategoryId", response = KpiTypeExtendedResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping(value = "/v1/kpi_category/get_kpi_type")
	public ResponseEntity<List<KpiTypeExtendedResponse>> getKpiTypeDetails(
			@RequestHeader(value = "kpiCategoryId") List<String> kpiCategoryId) {
		logger.info("Get kpi type by kpiCategoryId", kpiCategoryId);
		List<KpiTypeExtendedResponse> response = kpiCategoryService.getKPICategoryDetails(kpiCategoryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "getRequestedProcessLines", notes = "Retrieve process lines by kpi and mill ids", response = ProcessLinesResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping(value = "/v1/kpi_category/get_process_lines")
	public ResponseEntity<List<ProcessLinesResponse>> getRequestedProcessLines(
			@RequestHeader(value = "kpiId") String kpiId, @RequestHeader(value = "millId") String millId) {
		logger.info("Get kpi type by kpiCategoryId", kpiId);
		List<ProcessLinesResponse> response = kpiCategoryService.getProcessLines(
				DailyKpiPulpConverter.covertToInteger(kpiId), DailyKpiPulpConverter.covertToInteger(millId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
