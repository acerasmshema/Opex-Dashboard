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
import com.rgei.kpi.dashboard.response.model.ProcessLineTargetThreshold;
import com.rgei.kpi.dashboard.response.model.ProductionThreshold;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.service.ThresholdManagementService;
import com.rgei.kpi.dashboard.util.CommonFunction;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/restCall")
public class ThresholdManagementController {

	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(ThresholdManagementController.class);

	@Resource
	ThresholdManagementService thresholdManagementService;
	
	@ApiOperation(value = "getProductionTargetsByMillId", notes = "Retrieve production target by Mill Id", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping("/v1/production_target")
	public ResponseEntity<List<ProductionThreshold>> getProductionTargetsByMillId(@RequestHeader(value = "millId") String millId) {
		logger.info("Get all users by mill Id : " + millId);
		List<ProductionThreshold> responseList = thresholdManagementService.getProductionTargetsByMillId(CommonFunction.covertToInteger(millId));
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "getProcessLineTargets", notes = "Retrieve process line target by Mill Id", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK") })
	@GetMapping("/v1/process_line_targets")
	public ResponseEntity<List<ProcessLineTargetThreshold >> getProcessLineTargets(@RequestHeader(value = "millId") String millId,@RequestHeader(value = "buTypeId") String buTypeId,@RequestHeader(value = "kpiId") String kpiId) {
		logger.info("Retrieve process line target by Mill Id : " + millId);
		List<ProcessLineTargetThreshold > responseList = thresholdManagementService.getProcessLineTargets(CommonFunction.covertToInteger(millId),CommonFunction.covertToInteger(buTypeId),CommonFunction.covertToInteger(kpiId));
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
}
