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
import com.rgei.kpi.dashboard.response.model.BuTypeResponse;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.MillsResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineAnnualResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineDetailsResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineProjectedResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineRequest;
import com.rgei.kpi.dashboard.response.model.ResponseObject;
import com.rgei.kpi.dashboard.response.model.TargetProceessLine;
import com.rgei.kpi.dashboard.service.DailyKpiPulpService;
import com.rgei.kpi.dashboard.service.ProcessLinePulpKpiService;
import com.rgei.kpi.dashboard.service.ProcessLineService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restCall")
public class ProcessLineController {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ProcessLineController.class);
	
	@Resource
	DailyKpiPulpService dailyKpiPulpService;
	
	@Resource
	ProcessLinePulpKpiService processLinePulpKpiService;
	
	@Resource
	ProcessLineService processLineService;
	

	@GetMapping(value = "/v1/yesterday/ytd_process_line")
	public ResponseEntity<ProcessLineAnnualResponse> getAnnualProcessLine(@RequestHeader(value="millId") String millId,
			@RequestHeader(value="buId") String buId,@RequestHeader(value="kpiCategoryId") String kpiCategoryId,
			@RequestHeader(value="kpiId") String kpiId) {
		logger.info("Fetching Annual process line data.");
		ProcessLineAnnualResponse response = dailyKpiPulpService.getAnnualProcessLine(millId, buId, kpiCategoryId, kpiId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/yesterday/all_process_lines")
	public ResponseEntity<ResponseObject> getAllProcessLinesForYesterday(
			@RequestBody ProcessLineRequest productionRequest) {
		logger.info("Fetching all process lines data for yesterday.", productionRequest);
		ResponseObject response = processLinePulpKpiService.allProcessLines(productionRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/v1/date_range/daily_kpi_pulp")
	public ResponseEntity<List<DateRangeResponse>> getDailyKpiPulpDataForBarChart(@RequestBody ProcessLineRequest productionRequestDTO) {
		logger.info("Fetching kpi pulp data for bar chart.", productionRequestDTO);
		List<DateRangeResponse> response = processLinePulpKpiService.getDailyKpiPulpDataForBarChart(productionRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/date_range/daily_kpi_pulp_area")
	public ResponseEntity<List<DateRangeResponse>> getDailyKpiPulpDataForAreaChart(@RequestBody ProcessLineRequest productionRequestDTO) {
		logger.info("Fetching kpi pulp data for area chart.", productionRequestDTO);
		List<DateRangeResponse> response = processLinePulpKpiService.getDailyKpiPulpDataForAreaChart(productionRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/date_range/download_data_grid")
	public ResponseEntity<List<Map<String, Object>>> getDownloadDataGrid(@RequestBody ProcessLineRequest productionRequestDTO) {
		logger.info("Fetching grid data for downloading.", productionRequestDTO);
		List<Map<String, Object>> response = processLinePulpKpiService.getDownloadDataGrid(productionRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/date_range/all_process_lines")
	public ResponseEntity<List<DateRangeResponse>> getProcessLinesForDateRange(
			@RequestBody ProcessLineRequest productionRequestDTO) {
		logger.info("Fetching process lines data for requested date range.", productionRequestDTO);
		List<DateRangeResponse> response = processLinePulpKpiService.getLineChartData(productionRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/date_range/all_process_lines_target")
	public ResponseEntity<List<DateRangeResponse>> getProcessLinesTargetForDateRange(
			@RequestBody ProcessLineRequest productionRequestDTO) {
		logger.info("Fetching process lines target for selected date range.", productionRequestDTO);
		List<DateRangeResponse> response = processLinePulpKpiService.getDailyTargetLineData(productionRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v2/yesterday/ytd_process_line")
	public ResponseEntity<TargetProceessLine> getAnnualProcessLineV2(@RequestHeader(value="millId") String millId,
			@RequestHeader(value="buId") String buId,@RequestHeader(value="kpiCategoryId") String kpiCategoryId,
			@RequestHeader(value="kpiId") String kpiId) {
		logger.info("Fetching process line annual value.");
		TargetProceessLine response = processLinePulpKpiService.getAnnualExtendedProcessLine(millId, buId, kpiCategoryId, kpiId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/yesterday/ytd_target_process_line")
	public ResponseEntity<TargetProceessLine> getAnnualTargetProcessLine(@RequestHeader(value="millId") String millId,
			@RequestHeader(value="buId") String buId,@RequestHeader(value="kpiCategoryId") String kpiCategoryId,
			@RequestHeader(value="kpiId") String kpiId) {
		logger.info("Fetching annual target process line data.");
		TargetProceessLine response = processLinePulpKpiService.getAnnualTargetProcessLine(millId, buId, kpiCategoryId, kpiId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v2/yesterday/ytd_target_process_line")
	public ResponseEntity<TargetProceessLine> getAnnualTargetProcessLineV2(@RequestHeader(value="millId") String millId,
			@RequestHeader(value="buId") String buId,@RequestHeader(value="kpiCategoryId") String kpiCategoryId,
			@RequestHeader(value="kpiId") String kpiId) {
		logger.info("Fetching annual target for process lines.");
		TargetProceessLine response = processLinePulpKpiService.getAnnualTargetProcessLineV2(millId, buId, kpiCategoryId, kpiId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/process_line/projected_target_details")
	public ResponseEntity<ProcessLineProjectedResponse> getProjecctedTargetDetails(@RequestHeader(value="millId") String millId,
			@RequestHeader(value="buId") String buId,@RequestHeader(value="kpiCategoryId") String kpiCategoryId,
			@RequestHeader(value="kpiId") String kpiId) {
		logger.info("Fetching projected target details for process lines.");
		ProcessLineProjectedResponse  processLineProjectedResponse = processLinePulpKpiService.getProjectedProcessLineDetails(millId, buId, kpiCategoryId, kpiId);
		return new ResponseEntity<>(processLineProjectedResponse,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/date_range/selected_process_lines")
	public ResponseEntity<List<DateRangeResponse>> getLineChartProcessLinesForFrequecy(
			@RequestBody ProcessLineRequest productionRequestDTO) {
		logger.info("Fetching data for selected process lines on production dashboard.", productionRequestDTO);
		List<DateRangeResponse> response = processLinePulpKpiService.getProcessLinesForFrequency(productionRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/v1/date_range/selected_process_lines_grid_data")
	public ResponseEntity<List<List<Map<String,Object>>>> getDataGridProcessLinesForFrequecy(
			@RequestBody ProcessLineRequest productionRequestDTO) {
		logger.info("Fetching grid data for selected process lines on production dashboard.", productionRequestDTO);
		List<List<Map<String,Object>>> response = processLinePulpKpiService.getDataGridProcessLinesForFrequecy(productionRequestDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/process_line/get_all_process_lines")
	public ResponseEntity<List<ProcessLineDetailsResponse>> getProcessLineResponse(@RequestHeader(value="millId") String millId){
		logger.info("Fetching process line data against the requested location id.", millId);
		List<ProcessLineDetailsResponse> response = processLineService.getProcessLines(millId);
		return new ResponseEntity<List<ProcessLineDetailsResponse>>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/v1/location/all_mills")
	public ResponseEntity<List<MillsResponse>> getMills(@RequestHeader(value="countryIds") List<String> countryIds){
		logger.info("Fetching mills against the requested country ids.", countryIds);
		List<MillsResponse> response = processLineService.getMillDetails(countryIds);
		return new ResponseEntity<List<MillsResponse>>(response,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/v1/location/get_all_bu_type")
	public ResponseEntity<List<BuTypeResponse>> getAllBuType(){
		logger.info("Fetching All BU Types");
		List<BuTypeResponse> response = processLineService.getAllBuType();
		return new ResponseEntity<List<BuTypeResponse>>(response,HttpStatus.OK);
	}
	
	
	
	
}
