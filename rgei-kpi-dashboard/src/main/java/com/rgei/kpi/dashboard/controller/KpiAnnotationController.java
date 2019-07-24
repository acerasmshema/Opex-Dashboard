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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDateRangeSerach;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDateSerachRes;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDeleteRequest;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationRequest;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationResponse;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationSearchRequest;
import com.rgei.kpi.dashboard.service.KpiAnnotationService;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall/v1")
public class KpiAnnotationController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiAnnotationController.class);

	@Resource
	private KpiAnnotationService kpiAnnotationService;


	@PostMapping(value = "/kpi_annotation/save_annotation")
	public ResponseEntity<HttpStatus> saveAnnotationRequest(
			@RequestBody KpiAnnotationRequest kpiAnnotationRequest) {
		logger.info("Save annotation request", kpiAnnotationRequest);
		kpiAnnotationService.saveKpiAnnotationRequest(kpiAnnotationRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/kpi_annotation/get_annotation")
	public ResponseEntity<List<KpiAnnotationResponse>> getAnnotationRequest(
			@RequestBody KpiAnnotationSearchRequest kpiAnnotationSearchRequest) {
		logger.info("Get annotation request", kpiAnnotationSearchRequest);
		List<KpiAnnotationResponse>  response = kpiAnnotationService.getAnnotationDetails(kpiAnnotationSearchRequest);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/kpi_annotation/get_annotation_date")
	public ResponseEntity<KpiAnnotationDateSerachRes> getAnnotationByDate(
			@RequestBody KpiAnnotationDateRangeSerach kpiAnnotationDateRangeSerach) {
		logger.info("Get annotation by date", kpiAnnotationDateRangeSerach);
		KpiAnnotationDateSerachRes response = kpiAnnotationService.kpiAnnotationDateRangeSerach(kpiAnnotationDateRangeSerach);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/kpi_annotation/delete_annotation")
	public ResponseEntity<HttpStatus> deleteAnnotation(
			@RequestBody List<KpiAnnotationDeleteRequest> kpiAnnotationDeleteRequest) {
		logger.info("delete annotation", kpiAnnotationDeleteRequest);
		kpiAnnotationService.deleteAnnotation(kpiAnnotationDeleteRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
