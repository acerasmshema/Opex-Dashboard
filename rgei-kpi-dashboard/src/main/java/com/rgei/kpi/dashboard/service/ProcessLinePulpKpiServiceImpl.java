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
package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.DailyKpiPulpEntity;
import com.rgei.kpi.dashboard.entities.MillBuKpiCategoryEntity;
import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.DailyKpiPulpEntityRepository;
import com.rgei.kpi.dashboard.repository.MillBuKpiCategoryEntityRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineFrequencyRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineRepository;
import com.rgei.kpi.dashboard.response.model.DailyKpiPulp;
import com.rgei.kpi.dashboard.response.model.DailyKpiPulpResponse;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLine;
import com.rgei.kpi.dashboard.response.model.ProcessLineDailyTargetResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineProjectedResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineRequest;
import com.rgei.kpi.dashboard.response.model.ResponseObject;
import com.rgei.kpi.dashboard.response.model.TargetProceessLine;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverter;
import com.rgei.kpi.dashboard.util.DailyKpiPulpConverterRZ;
import com.rgei.kpi.dashboard.util.ProcessLineExtendedUtil;
import com.rgei.kpi.dashboard.util.ProcessLineFrequencyDataGridUtility;
import com.rgei.kpi.dashboard.util.ProcessLineFrequencyDataGridUtilityRZ;
import com.rgei.kpi.dashboard.util.ProcessLineFrequencyUtility;
import com.rgei.kpi.dashboard.util.ProcessLineFrequencyUtilityRZ;
import com.rgei.kpi.dashboard.util.ProcessLineTargetUtil;
import com.rgei.kpi.dashboard.util.ProcessLineUtility;
import com.rgei.kpi.dashboard.util.ProcessLineUtilityRZ;
import com.rgei.kpi.dashboard.util.Utility;

@Service
public class ProcessLinePulpKpiServiceImpl implements ProcessLinePulpKpiService{
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ProcessLinePulpKpiServiceImpl.class);
	
	@Resource
	ProcessLineRepository processLineRepository;
	
	@Resource
	DailyKpiPulpEntityRepository dailyKpiPulpEntityRepository;
	
	@Resource
	MillBuKpiCategoryEntityRepository millBuKpiCategoryEntityRepository; 
	
	@Resource
	DailyKpiPulpServiceImpl dailyKpiPulpService;

	@Resource
	private MaintenanceDaysService maintenanceDaysService;
	
	@Resource
	private MillBuKpiCategoryService millBuKpiCategoryService;
	
	@Resource
	ProcessLineFrequencyRepository processLineFrequencyRepository;
	
	

	@Override
	public ResponseObject allProcessLines(ProcessLineRequest productionRequest) {
		logger.info("Fetching all process lines data for request", productionRequest);
		Date yesterdayDate = ProcessLineUtility.getYesterdayDate();
		ResponseObject response = new ResponseObject();
		List<DailyKpiPulpResponse> responeObject = null;
		List<ProcessLine> processLine = null;
		List<DailyKpiPulp> dailyKpiPulp = null;
		MillEntity mill = new MillEntity();
		mill.setMillId(productionRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLine = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}else {
			throw new RecordNotFoundException("Record not found of process line for mill Id : "+productionRequest.getMillId());
		}
		Optional<List<DailyKpiPulpEntity>> dailyKpiEntity = Optional.ofNullable(
				dailyKpiPulpEntityRepository.fetchData(yesterdayDate, productionRequest.getMillId(), productionRequest.getKpiCategoryId(),
						productionRequest.getBuId(), productionRequest.getBuTypeId(), productionRequest.getKpiId()));
		if (dailyKpiEntity.isPresent()) {
			dailyKpiPulp = DailyKpiPulpConverter.convertToDailyKpiPulpDTO(dailyKpiEntity.get());
		}
		if(DashboardConstant.KRC.equals(productionRequest.getMillId().toString())) {
			responeObject = DailyKpiPulpConverter.createResponseObject(processLine, dailyKpiPulp);
		}
		else if(DashboardConstant.RZ.equals(productionRequest.getMillId().toString())) {
			responeObject = DailyKpiPulpConverterRZ.createResponseObject(processLine, dailyKpiPulp);
		}
		response.setDailyKpiPulp(responeObject);
		return response;
	}

	@Override
	public List<DateRangeResponse> getDailyKpiPulpDataForBarChart(ProcessLineRequest productionRequest) {
		logger.info("Getting daily kpi pulp data for bar charts", productionRequest);
		List<DateRangeResponse> dateRangeResponse = null;
		List<DailyKpiPulpEntity> dailyKpiPulpEntities;
		try {
			dailyKpiPulpEntities = dailyKpiPulpEntityRepository.findByDate(Utility.stringToDateConvertor(productionRequest.getStartDate(), DashboardConstant.FORMAT), 
					Utility.stringToDateConvertor(productionRequest.getEndDate(),DashboardConstant.FORMAT),
					productionRequest.getMillId(),
					productionRequest.getBuId(),
					productionRequest.getBuTypeId(),
					productionRequest.getKpiCategoryId(),
					productionRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Record not found for Daily Kpi Entities for bar chart of mill Id : "+productionRequest.getMillId());
		}
		if(dailyKpiPulpEntities == null || dailyKpiPulpEntities.isEmpty()) {
			throw new RecordNotFoundException("Record not found for Daily Kpi Entities for bar chart of mill Id : "+productionRequest.getMillId());
		}
		if(DashboardConstant.KRC.equals(productionRequest.getMillId().toString())) {
			dateRangeResponse = DailyKpiPulpConverter.createDailyKpiPulpResponseForBarChart(dailyKpiPulpEntities);
		}
		else if(DashboardConstant.RZ.equals(productionRequest.getMillId().toString())) {
			dateRangeResponse = DailyKpiPulpConverterRZ.createDailyKpiPulpResponseForBarChart(dailyKpiPulpEntities);
		}
		return dateRangeResponse;
	}

	@Override
	public List<DateRangeResponse> getDailyKpiPulpDataForAreaChart(ProcessLineRequest productionRequest) {
		logger.info("Getting daily kpi pulp data for area chart", productionRequest);
		List<DateRangeResponse> dateRangeResponse = null;
		List<DailyKpiPulpEntity> dailyKpiPulpEntities;
		try {
			dailyKpiPulpEntities = dailyKpiPulpEntityRepository.findByDate(Utility.stringToDateConvertor(productionRequest.getStartDate(), DashboardConstant.FORMAT), 
					Utility.stringToDateConvertor(productionRequest.getEndDate(),DashboardConstant.FORMAT),
					productionRequest.getMillId(),
					productionRequest.getBuId(),
					productionRequest.getBuTypeId(),
					productionRequest.getKpiCategoryId(),
					productionRequest.getKpiId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RecordNotFoundException("Record not found for Daily Kpi Entities for area chart of mill Id : "+productionRequest.getMillId());
		}
		if(dailyKpiPulpEntities == null || dailyKpiPulpEntities.isEmpty()) {
			throw new RecordNotFoundException("Record not found for Daily Kpi Entities for area chart of mill Id : "+productionRequest.getMillId());
		}
		if(DashboardConstant.KRC.equals(productionRequest.getMillId().toString())) {
			dateRangeResponse = DailyKpiPulpConverter.createDailyKpiPulpResponseForAreaChart(dailyKpiPulpEntities);
		}
		else if(DashboardConstant.RZ.equals(productionRequest.getMillId().toString())) {
			dateRangeResponse = DailyKpiPulpConverterRZ.createDailyKpiPulpResponseForAreaChart(dailyKpiPulpEntities);
		}
		return dateRangeResponse;
	}
	
	@Override
	public List<Map<String, Object>> getDownloadDataGrid(ProcessLineRequest productionRequest) {
		logger.info("Download grid data response", productionRequest);
		List<Map<String, Object>> downloadGridResponse = dailyKpiPulpEntityRepository.findByDateForDataGrid(Utility.stringToDateConvertor(productionRequest.getStartDate(), DashboardConstant.FORMAT), 
				Utility.stringToDateConvertor(productionRequest.getEndDate(),DashboardConstant.FORMAT),
				productionRequest.getMillId(),
				productionRequest.getBuId(),
				productionRequest.getBuTypeId(),
				productionRequest.getKpiCategoryId(),
				productionRequest.getKpiId());
		
		return ProcessLineUtility.parseCurrencyFormat(downloadGridResponse);
	}
	
	@Override
	public List<DateRangeResponse> getLineChartData(ProcessLineRequest productionRequest) {
		logger.info("Getting line chart data for process lines", productionRequest);
		List<DailyKpiPulpEntity> dailyKpiPulpEntities = dailyKpiPulpEntityRepository.findByDateForLineCharts(
				Utility.stringToDateConvertor(productionRequest.getStartDate(),DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(productionRequest.getEndDate(),DashboardConstant.FORMAT),
				productionRequest.getMillId(), productionRequest.getBuId(), productionRequest.getBuTypeId(),
				productionRequest.getKpiCategoryId(), productionRequest.getKpiId());
		return ProcessLineUtility.createDailyKpiPulpResponseForLineChart(dailyKpiPulpEntities);

	}
	
	@Override
	public List<DateRangeResponse> getDailyTargetLineData(ProcessLineRequest processLineRequest) {
		logger.info("Getting daily target line data", processLineRequest);
		List<DateRangeResponse> dateRangeResponse = null;
		List<ProcessLine> processLine = null;
		MillEntity mill = new MillEntity();
		mill.setMillId(processLineRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLine = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}
		List<DailyKpiPulpEntity> dailyKpiPulpEntities;
		try {
			dailyKpiPulpEntities = dailyKpiPulpEntityRepository.findByDateForLineCharts(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(),DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(),DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Record not found for Daily Target Line data of mill Id : "+processLineRequest.getMillId());
		}
		if(dailyKpiPulpEntities == null || dailyKpiPulpEntities.isEmpty()) {
			throw new RecordNotFoundException("Record not found for Daily Target Line data of mill Id : "+processLineRequest.getMillId());
		}
		if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
			dateRangeResponse = ProcessLineUtility.createDailyTargetLineResponse(processLine, dailyKpiPulpEntities);
		}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
			dateRangeResponse = ProcessLineUtilityRZ.createDailyTargetLineResponse(processLine, dailyKpiPulpEntities);	
		}
		return dateRangeResponse;
		 
	}
	
	@Override
	public TargetProceessLine getAnnualExtendedProcessLine(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Getting annual extended process line response");
		return ProcessLineExtendedUtil.generateExtendedResponse(dailyKpiPulpService.calculateDateWiseTotalProcessLine(millId, buId, kpiCategoryId, kpiId));
	}

	@Override
	public TargetProceessLine getAnnualTargetProcessLine(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Getting annual target process line");
		MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(CommonFunction.covertToInteger(millId),
				CommonFunction.covertToInteger(kpiCategoryId), CommonFunction.covertToInteger(buId));
		return ProcessLineExtendedUtil.generateTargetResponse(millBuKpiCategoryEntity);
	}
	
	@Override
	public TargetProceessLine getAnnualTargetProcessLineV2(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Getting annual target for process lines");
		MillBuKpiCategoryEntity millBuKpiCategoryEntity;
		List<DailyKpiPulpEntity> dailyKpiEntities;
		try {
			millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(CommonFunction.covertToInteger(millId),
					CommonFunction.covertToInteger(kpiCategoryId), CommonFunction.covertToInteger(buId));
			
			dailyKpiEntities = dailyKpiPulpEntityRepository.readForDateRange(DailyKpiPulpConverter.getCurrentYearDate(), DailyKpiPulpConverter.getYesterdayDate(),
					DailyKpiPulpConverter.covertToInteger(millId), DailyKpiPulpConverter.covertToInteger(buId), 
					DailyKpiPulpConverter.covertToInteger(kpiCategoryId), DailyKpiPulpConverter.covertToInteger(kpiId));
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for annual target process line V2");
		}
		if(millBuKpiCategoryEntity == null) {
			throw new RecordNotFoundException("Error while fetching records for annual target process line V2");
		}
		return ProcessLineExtendedUtil.generateTargetResponseV2(millBuKpiCategoryEntity, dailyKpiEntities);
	}
	
	@Override
	public Long getAnnualTargetValue(String millId, String buId, String kpiCategoryId,
			String kpiId) {
		logger.info("Getting annual target value for process lines");
		MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(CommonFunction.covertToInteger(millId),
				CommonFunction.covertToInteger(kpiCategoryId), CommonFunction.covertToInteger(buId));
		return ProcessLineExtendedUtil.calculateTargetValue(millBuKpiCategoryEntity);
	}
	
	
	@Override
	public ProcessLineProjectedResponse getProjectedProcessLineDetails(String millId, String buId, String kpiCategoryId,
			String kpiId, Boolean annualTargetRequired) {
		logger.info("Getting projected process line details");
		Long tarDiff = calculateTargetDifference(millId, buId, kpiCategoryId, kpiId);
		Integer targetDays = getTargetDays(millId, buId, kpiCategoryId);
		Integer dailyTargetValue = getDailyTargetValues(millId, buId, kpiCategoryId);
		List<MaintenanceDaysResponse>  maintainanceDays = maintenanceDaysService.getMaintainanceDayDetails(millId, buId);
		List<Date> dates = ProcessLineTargetUtil.processDateRange(targetDays);
		Integer finalTargetDays = ProcessLineTargetUtil.processTargetDaysAsPerMaintainanceDays(dates, maintainanceDays, targetDays);
		Long projectedTargetValue = ProcessLineTargetUtil.setProjectedTaeget(tarDiff,targetDays, dailyTargetValue);
		String endDate = new ProcessLineTargetUtil().getProjectedDate(finalTargetDays);
		
		//Merger with Api GET /restCall/v1/yesterday/ytd_process_line 
		String annualTarget = "";
		//Fetch annualTarget value if annualTargetRequired is true
		if(annualTargetRequired) {
			MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(CommonFunction.covertToInteger(millId),
					CommonFunction.covertToInteger(kpiCategoryId), CommonFunction.covertToInteger(buId));
			if(millBuKpiCategoryEntity != null) {
				annualTarget = millBuKpiCategoryEntity.getAnnualTarget();
			}
		}
		
		return ProcessLineTargetUtil.populateResponse(projectedTargetValue, targetDays, endDate, annualTarget);
	}
	
	@Override
	public List<DateRangeResponse> getProcessLinesForFrequency(ProcessLineRequest processLineRequest) {
		logger.info("Getting process line data for specific frequencies", processLineRequest);
		List<DateRangeResponse> resultList = new ArrayList<>();
		List<ProcessLine> processLines = null;
		MillEntity mill = new MillEntity();
		mill.setMillId(processLineRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional
				.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLines = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}
		List<String> lineList = null;
		List<String> processLinesList = Arrays.asList(processLineRequest.getProcessLines());
		lineList = ProcessLineUtility.fetchProcessLines(processLines, processLinesList);
		 
		if (!Objects.nonNull(processLineRequest.getFrequency())) {
			processLineRequest.setFrequency(0);
		}

		switch (processLineRequest.getFrequency()) {
		case 0:
			getDailyFrequencyResponse(processLineRequest, resultList, lineList);
			break;
		case 1:
			getMonthlyFrequencyResponse(processLineRequest, resultList, lineList);
			break;
		case 2:
			getQuarterlyFrequencyResponse(processLineRequest, resultList, lineList);
			break;
		case 3:
			getYearlyFrequencyResponse(processLineRequest, resultList, lineList);
			break;
		default:
			getDailyFrequencyResponse(processLineRequest, resultList, lineList);
		}
		return resultList;
	}

	//Processing for grid data filter by date-range, process lines and frequency
	@Override
	public List<List<Map<String, Object>>> getDataGridProcessLinesForFrequecy(ProcessLineRequest processLineRequest) {
		logger.info("Getting process line grid data for specific frequencies", processLineRequest);
		List<ProcessLine> processLines = null;
		MillEntity mill = new MillEntity();
		mill.setMillId(processLineRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional
				.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLines = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}
		List<String> lineList = null;
		List<String> processLinesList = Arrays.asList(processLineRequest.getProcessLines());
		lineList = ProcessLineUtility.fetchProcessLines(processLines, processLinesList);
		
		List<List<Map<String, Object>>> responseData = new ArrayList<>();
		
		switch (processLineRequest.getFrequency()) {
		case 0:
			getDailyFrequencyGridData(processLineRequest, lineList, responseData);
			break;
		case 1:
			getMonthlyFrequencyGridData(processLineRequest, lineList, responseData);
			break;
		case 2:
			getQuarterlyFrequencyGridData(processLineRequest, lineList, responseData);
			break;
		case 3:
			getYearlyFrequencyGridData(processLineRequest, lineList, responseData);
			break;
		default:
			getDailyFrequencyGridData(processLineRequest, lineList, responseData);
		}
		return responseData;
	}

	private Integer getDailyTargetValues(String millId, String buId, String kpiCategoryId) {
		logger.info("Getting daily target data");
		ProcessLineDailyTargetResponse  processLineDailyTargetResponse  = millBuKpiCategoryService.getProcessLineDailyTarget(millId, buId, kpiCategoryId);
		return processLineDailyTargetResponse.getDailyTarget();
	}

	private Long calculateTargetDifference(String millId, String buId, String kpiCategoryId, String kpiId) {
		logger.info("Calculating the target difference");
		Long projectedTarget = getAnnualTargetValue(millId, buId, kpiCategoryId, kpiId);
		Long actualTarget = dailyKpiPulpService.getActualTarget(millId, buId, kpiCategoryId, kpiId);
		return ProcessLineTargetUtil.calculateTargetDifference(projectedTarget,actualTarget);
	}

	private Integer getTargetDays(String millId, String buId, String kpiCategoryId) {
		logger.info("Getting target days");
		MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(
				CommonFunction.covertToInteger(millId),
				CommonFunction.covertToInteger(kpiCategoryId),
				CommonFunction.covertToInteger(buId));
		return millBuKpiCategoryEntity.getTargetDays();
	}

	private void getYearlyFrequencyResponse(ProcessLineRequest processLineRequest, List<DateRangeResponse> resultList,
			List<String> lineList) {
		logger.info("Creating yearly frequency response for process line request ", processLineRequest);
		List<Object[]> responseEntity;
		try {
			responseEntity = processLineFrequencyRepository.getProcessLinesTotalYearly(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for yearly frequency response");
		}

		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
			val.setSeries(ProcessLineFrequencyUtility.getYearlySeriesResponse(processLine, responseEntity));
			}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
			val.setSeries(ProcessLineFrequencyUtilityRZ.getYearlySeriesResponse(processLine, responseEntity));	
			}
			resultList.add(val);
		}
	}

	private void getQuarterlyFrequencyResponse(ProcessLineRequest processLineRequest,
			List<DateRangeResponse> resultList, List<String> lineList) {
		logger.info("Creating quarterly frequency response for process line request ", processLineRequest);
		List<Object[]> responseEntity;
		try {
			responseEntity = processLineFrequencyRepository.getProcessLinesTotalQuarterly(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for quarterly frequency response");
		}

		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
			val.setSeries(ProcessLineFrequencyUtility.getQuarterlySeriesResponse(processLine, responseEntity));
			}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
			val.setSeries(ProcessLineFrequencyUtilityRZ.getQuarterlySeriesResponse(processLine, responseEntity));	
			}
			resultList.add(val);
		}
	}

	private void getMonthlyFrequencyResponse(ProcessLineRequest processLineRequest, List<DateRangeResponse> resultList,
			List<String> lineList) {
		logger.info("Creating monthly frequency response for process line request ", processLineRequest);
		List<Object[]> responseEntity;
		try {
			responseEntity = processLineFrequencyRepository.getProcessLinesTotalMonthly(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for monthly frequency response");
		}
		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
			val.setSeries(ProcessLineFrequencyUtility.getMonthlySeriesResponse(processLine, responseEntity));
			}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
			val.setSeries(ProcessLineFrequencyUtilityRZ.getMonthlySeriesResponse(processLine, responseEntity));	
			}
			resultList.add(val);
		}
	}

	private void getDailyFrequencyResponse(ProcessLineRequest processLineRequest, List<DateRangeResponse> resultList,
			List<String> lineList) {
		logger.info("Creating daily frequency response for process line request ", processLineRequest);
		List<Object[]> responseEntity;
		try {
			responseEntity = processLineFrequencyRepository.getProcessLinesDailyTotal(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for daily frequency response");
		}
		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
			val.setSeries(ProcessLineFrequencyUtility.getDailySeriesResponse(processLine, responseEntity));
			}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
			val.setSeries(ProcessLineFrequencyUtilityRZ.getDailySeriesResponse(processLine, responseEntity));	
			}
			resultList.add(val);
		}
	}
	
	private void getYearlyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		logger.info("Creating yearly frequency grid data for process line request ", processLineRequest);
		List<Map<String, Object>> response = null;
		List<Object[]> responseEntity;
		try {
			responseEntity = processLineFrequencyRepository.getProcessLinesTotalYearly(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for yearly frequency grid data");
		}
		if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
		response = ProcessLineFrequencyDataGridUtility.getGridDataYearly(lineList, responseEntity);
		}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
		response = ProcessLineFrequencyDataGridUtilityRZ.getGridDataYearly(lineList, responseEntity);	
		}
		responseData.add(response);
	}

	private void getQuarterlyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		logger.info("Creating quarterly frequency grid data for process line request ", processLineRequest);
		List<Map<String, Object>> response = null;
		List<Object[]> responseEntity;
		try {
			responseEntity = processLineFrequencyRepository.getProcessLinesTotalQuarterly(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for quarterly frequency grid data");
		}
		if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
		response = ProcessLineFrequencyDataGridUtility.getGridDataQuarterly(lineList, responseEntity);
		}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
		response = ProcessLineFrequencyDataGridUtilityRZ.getGridDataQuarterly(lineList, responseEntity);	
		}
		responseData.add(response);
	}

	private void getMonthlyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		logger.info("Creating monthly frequency grid data for process line request ", processLineRequest);
		List<Map<String, Object>> response = null;
		List<Object[]> responseEntity;
		try {
			responseEntity = processLineFrequencyRepository.getProcessLinesTotalMonthly(
					Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
					processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
					processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		} catch (Exception e) {
			throw new RecordNotFoundException("Error while fetching records for monthly frequency grid data");
		}
		if(DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())){
		response = ProcessLineFrequencyDataGridUtility.getGridDataMonthly(lineList, responseEntity);
		}else if(DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
		response = ProcessLineFrequencyDataGridUtilityRZ.getGridDataMonthly(lineList, responseEntity);	
		}
		responseData.add(response);
	}

	private void getDailyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		logger.info("Creating daily frequency grid data for process line request ", processLineRequest);
		List<Map<String, Object>> response= null;
		if (DashboardConstant.KRC.equals(processLineRequest.getMillId().toString())) {
			List<Map<String, Object>> responseEntity;
			try {
				responseEntity = processLineFrequencyRepository.findByDateForDataGrid(
						Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
						Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
						processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
						processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
			} catch (Exception e) {
				throw new RecordNotFoundException("Error while fetching records for daily frequency grid data");
			}
			response = ProcessLineFrequencyDataGridUtility.getGridDataDailyResponse(lineList, responseEntity);
		} else if (DashboardConstant.RZ.equals(processLineRequest.getMillId().toString())) {
			List<Map<String, Object>> responseEntity;
			try {
				responseEntity = processLineFrequencyRepository.findByDateForDataGridRZ(
						Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
						Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
						processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
						processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
			} catch (Exception e) {
				throw new RecordNotFoundException("Error while fetching records for daily frequency grid data");
			}
			response = ProcessLineFrequencyDataGridUtilityRZ.getGridDataDailyResponse(lineList, responseEntity);
		}
		responseData.add(response);
	}
	
}
