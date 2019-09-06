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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.response.model.DeleteRequest;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysRequest;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysResponse;
import com.rgei.kpi.dashboard.response.model.UpdateRemarksRequest;
import com.rgei.kpi.dashboard.service.MaintenanceDaysService;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class MaintenanceDaysController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(MaintenanceDaysController.class);
	
	@Resource
	private MaintenanceDaysService maintenanceDaysService;
	
	@GetMapping(value = "/v1/maintenance_days/get_maintenance_days")
	public ResponseEntity<List<MaintenanceDaysResponse>> getMaintainanceDetails(@RequestHeader(value="millId") String millId,
			@RequestHeader(value="buId") String buId) {
		logger.info("Inside MaintenanceDaysController to fetch maintenance days");
		List<MaintenanceDaysResponse>  response = maintenanceDaysService.getMaintainanceDayDetails(millId, buId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/maintenance_days/save_maintenance_days")
	public ResponseEntity<HttpStatus> saveMaintainanceRequest(
			@RequestBody MaintenanceDaysRequest maintenanceDaysRequest) {
		logger.info("Inside MaintenanceDaysController to save maintenance days");
		maintenanceDaysService.saveMaintenanceDays(maintenanceDaysRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/maintenance_days/update_maintenance_days")
	public ResponseEntity<HttpStatus> updateMaintainanceRequest(
			@RequestBody MaintenanceDaysRequest maintenanceDaysRequest) {
		logger.info("Inside MaintenanceDaysController to update maintenance days");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/maintenance_days/delete_maintenance_days")
	public ResponseEntity<HttpStatus> deleteMaintainanceDetails(@RequestBody DeleteRequest request) {
		logger.info("Inside MaintenanceDaysController to delete maintenance days");
		maintenanceDaysService.deleteMaintainanceDayDetails(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/v1/maintenance_days/update_maintenance_days_remarks")
	public ResponseEntity<HttpStatus> updateMaintainanceRemarksRequest(@RequestBody UpdateRemarksRequest request) {
		logger.info("Inside MaintenanceDaysController to update maintenance days remarks");
		maintenanceDaysService.updateMaintainanceDayRemarks(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
