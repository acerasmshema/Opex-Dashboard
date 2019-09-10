package com.rgei.kpi.dashboard.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.service.CountryService;

@RestController
@RequestMapping("/restCall")
public class CountryController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(CountryController.class);
	
	@Resource
	CountryService countryService;
	
	@GetMapping("/v1/get_countries")
	public ResponseEntity<List<CountryResponse>> getAllCountries(){
		logger.info("Get all countries list");
		List<CountryResponse> responseList = countryService.getCountryList();
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
}
