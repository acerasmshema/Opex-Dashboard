package com.rgei.kpi.dashboard.controller;

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
import com.rgei.kpi.dashboard.response.model.BenchmarkingReponse;
import com.rgei.kpi.dashboard.response.model.BenchmarkingRequest;
import com.rgei.kpi.dashboard.service.BenchmarkingService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall/v1")
public class BenchmarkingController {
	
CentralizedLogger logger = RgeiLoggerFactory.getLogger(BenchmarkingController.class);
	
	
	@Resource
	private BenchmarkingService benchmarkingService;

	@PostMapping(value = "/benchmarking/get_selected_data")
	public ResponseEntity<BenchmarkingReponse> getMaintainanceDetails(
			@RequestBody BenchmarkingRequest benchmarkingRequest) {
		logger.info("Inside Benchmarking controller to fetch benchmarking data");
		BenchmarkingReponse response = benchmarkingService.getBenchmarkingData(benchmarkingRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	 
}
