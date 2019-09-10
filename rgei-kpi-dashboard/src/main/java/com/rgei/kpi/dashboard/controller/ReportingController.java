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
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.response.model.LoginDetailResponse;
import com.rgei.kpi.dashboard.service.ReportingService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class ReportingController {
	
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(RgeUserController.class);
	
	@Resource
	ReportingService userReportService;
	
	@GetMapping(value = "/v1/login_report/get_all")
	public ResponseEntity<List<LoginDetailResponse>> getAllUserLoginDetails(@RequestHeader(value = "startDate") String startDate, @RequestHeader(value = "endDate") String endDate, @RequestHeader(value="millId") String millId){
		logger.info("Entering into the get datewise login details by startDate:{}",startDate);
		List<LoginDetailResponse> loginDetailResponse = userReportService.getAllUserLoginDetails(startDate, endDate, millId);
		if(!loginDetailResponse.isEmpty()) {
			return new ResponseEntity<>(loginDetailResponse, HttpStatus.OK);
		}else {
			throw new RecordNotFoundException("Records not found for Mill Id - "+millId);
		}
	}
	
	@GetMapping(value = "/v1/login_report/get_distinct")
	public ResponseEntity<List<LoginDetailResponse>> getDistinctDatewiseLoginDetails(@RequestHeader(value = "startDate") String startDate, @RequestHeader(value = "endDate") String endDate, @RequestHeader(value="millId") String millId){
		logger.info("Entering into the get datewise login details by startDate:{}",startDate);
		List<LoginDetailResponse> loginDetailResponse = userReportService.getDistinctUserLoginDetails(startDate, endDate, millId);
		if(!loginDetailResponse.isEmpty()) {
			return new ResponseEntity<>(loginDetailResponse, HttpStatus.OK);
		}else {
			throw new RecordNotFoundException("Records not found for Mill Id - "+millId);
		}
	}
}
