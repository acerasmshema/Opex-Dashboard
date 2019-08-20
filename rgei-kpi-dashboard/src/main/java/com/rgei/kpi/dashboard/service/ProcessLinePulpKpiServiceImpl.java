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
import com.rgei.kpi.dashboard.util.ProcessLineExtendedUtil;
import com.rgei.kpi.dashboard.util.ProcessLineFrequencyDataGridUtility;
import com.rgei.kpi.dashboard.util.ProcessLineFrequencyUtility;
import com.rgei.kpi.dashboard.util.ProcessLineTargetUtil;
import com.rgei.kpi.dashboard.util.ProcessLineUtility;
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
		ResponseObject response;
		List<ProcessLine> processLine = null;
		List<DailyKpiPulp> dailyKpiPulp = null;
		MillEntity mill = new MillEntity();
		mill.setMillId(productionRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLine = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}
		Optional<List<DailyKpiPulpEntity>> dailyKpiEntity = Optional.ofNullable(
				dailyKpiPulpEntityRepository.fetchData(yesterdayDate, productionRequest.getMillId(), productionRequest.getKpiCategoryId(),
						productionRequest.getBuId(), productionRequest.getBuTypeId(), productionRequest.getKpiId()));
		if (dailyKpiEntity.isPresent()) {
			dailyKpiPulp = DailyKpiPulpConverter.convertToDailyKpiPulpDTO(dailyKpiEntity.get());
		}
		List<DailyKpiPulpResponse> responeObject = DailyKpiPulpConverter.createResponseObject(processLine, dailyKpiPulp);
		response = new ResponseObject();
		response.setDailyKpiPulp(responeObject);
		return response;
	}

	@Override
	public List<DateRangeResponse> getDailyKpiPulpDataForBarChart(ProcessLineRequest productionRequest) {
		logger.info("Getting daily kpi pulp data for bar charts", productionRequest);
		List<DailyKpiPulpEntity> dailyKpiPulpEntities = dailyKpiPulpEntityRepository.findByDate(Utility.stringToDateConvertor(productionRequest.getStartDate(), DashboardConstant.FORMAT), 
				Utility.stringToDateConvertor(productionRequest.getEndDate(),DashboardConstant.FORMAT),
				productionRequest.getMillId(),
				productionRequest.getBuId(),
				productionRequest.getBuTypeId(),
				productionRequest.getKpiCategoryId(),
				productionRequest.getKpiId());
		
		return DailyKpiPulpConverter.createDailyKpiPulpResponseForBarChart(dailyKpiPulpEntities);
	}

	@Override
	public List<DateRangeResponse> getDailyKpiPulpDataForAreaChart(ProcessLineRequest productionRequest) {
		logger.info("Getting daily kpi pulp data for area chart", productionRequest);
		List<DailyKpiPulpEntity> dailyKpiPulpEntities = dailyKpiPulpEntityRepository.findByDate(Utility.stringToDateConvertor(productionRequest.getStartDate(), DashboardConstant.FORMAT), 
				Utility.stringToDateConvertor(productionRequest.getEndDate(),DashboardConstant.FORMAT),
				productionRequest.getMillId(),
				productionRequest.getBuId(),
				productionRequest.getBuTypeId(),
				productionRequest.getKpiCategoryId(),
				productionRequest.getKpiId());
		
		return DailyKpiPulpConverter.createDailyKpiPulpResponseForAreaChart(dailyKpiPulpEntities);
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
		List<ProcessLine> processLine = null;
		MillEntity mill = new MillEntity();
		mill.setMillId(processLineRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLine = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}
		List<DailyKpiPulpEntity> dailyKpiPulpEntities = dailyKpiPulpEntityRepository.findByDateForLineCharts(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(),DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(),DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getKpiId());
			
		return ProcessLineUtility.createDailyTargetLineResponse(processLine, dailyKpiPulpEntities);
		 
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
		MillBuKpiCategoryEntity millBuKpiCategoryEntity = millBuKpiCategoryEntityRepository.find(CommonFunction.covertToInteger(millId),
				CommonFunction.covertToInteger(kpiCategoryId), CommonFunction.covertToInteger(buId));
		
		List<DailyKpiPulpEntity> dailyKpiEntities = dailyKpiPulpEntityRepository.readForDateRange(DailyKpiPulpConverter.getCurrentYearDate(), DailyKpiPulpConverter.getYesterdayDate(),
				DailyKpiPulpConverter.covertToInteger(millId), DailyKpiPulpConverter.covertToInteger(buId), 
				DailyKpiPulpConverter.covertToInteger(kpiCategoryId), DailyKpiPulpConverter.covertToInteger(kpiId));
		
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
			String kpiId) {
		logger.info("Getting projected process line details");
		Long tarDiff = calculateTargetDifference(millId, buId, kpiCategoryId, kpiId);
		Integer targetDays = getTargetDays(millId, buId, kpiCategoryId);
		Integer dailyTargetValue = getDailyTargetValues(millId, buId, kpiCategoryId);
		List<MaintenanceDaysResponse>  maintainanceDays = maintenanceDaysService.getMaintainanceDayDetails(millId, buId);
		List<Date> dates = ProcessLineTargetUtil.processDateRange(targetDays);
		Integer finalTargetDays = ProcessLineTargetUtil.processTargetDaysAsPerMaintainanceDays(dates, maintainanceDays, targetDays);
		Long projectedTargetValue = ProcessLineTargetUtil.setProjectedTaeget(tarDiff,targetDays, dailyTargetValue);
		String endDate = new ProcessLineTargetUtil().getProjectedDate(finalTargetDays);
		return ProcessLineTargetUtil.populateResponse(projectedTargetValue, targetDays, endDate);
	}
	
	@Override
	public List<DateRangeResponse> getProcessLinesForFrequency(ProcessLineRequest processLineRequest) {
		logger.info("Getting process line data for specific frequencies", processLineRequest);
		List<DateRangeResponse> resultList = new ArrayList<>();
		List<ProcessLine> processLines = null;
		List<String> lineList = Arrays.asList(processLineRequest.getProcessLines());
		MillEntity mill = new MillEntity();
		mill.setMillId(processLineRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional
				.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLines = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}
		if (lineList.isEmpty()) {
			lineList = ProcessLineUtility.getAllProcessLines(processLines);
		}
		 
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
		List<String> lineList = Arrays.asList(processLineRequest.getProcessLines());
		MillEntity mill = new MillEntity();
		mill.setMillId(processLineRequest.getMillId());
		Optional<List<ProcessLineEntity>> processLineEntity = Optional
				.ofNullable(processLineRepository.findAllByMillOrderByProcessLineIdAsc(mill));
		if (processLineEntity.isPresent()) {
			processLines = ProcessLineUtility.convertToProcessLineDTO(processLineEntity.get());
		}
		if (lineList.isEmpty()) {
			lineList = ProcessLineUtility.getAllProcessLines(processLines);
		}
		if (!Objects.nonNull(processLineRequest.getFrequency())) {
			processLineRequest.setFrequency(0);
		}
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
		List<Object[]> responseEntity = processLineFrequencyRepository.getProcessLinesTotalYearly(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());

		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(ProcessLineFrequencyUtility.getYearlySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}

	private void getQuarterlyFrequencyResponse(ProcessLineRequest processLineRequest,
			List<DateRangeResponse> resultList, List<String> lineList) {
		List<Object[]> responseEntity = processLineFrequencyRepository.getProcessLinesTotalQuarterly(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());

		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(ProcessLineFrequencyUtility.getQuarterlySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}

	private void getMonthlyFrequencyResponse(ProcessLineRequest processLineRequest, List<DateRangeResponse> resultList,
			List<String> lineList) {
		List<Object[]> responseEntity = processLineFrequencyRepository.getProcessLinesTotalMonthly(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(ProcessLineFrequencyUtility.getMonthlySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}

	private void getDailyFrequencyResponse(ProcessLineRequest processLineRequest, List<DateRangeResponse> resultList,
			List<String> lineList) {
		List<Object[]> responseEntity = processLineFrequencyRepository.getProcessLinesDailyTotal(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		for (String processLine : lineList) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(ProcessLineFrequencyUtility.getDailySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}
	
	private void getYearlyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		List<Map<String, Object>> response;
		List<Object[]> responseEntity = processLineFrequencyRepository.getProcessLinesTotalYearly(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		response = ProcessLineFrequencyDataGridUtility.getGridDataYearly(lineList, responseEntity);
		responseData.add(response);
	}

	private void getQuarterlyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		List<Map<String, Object>> response;
		List<Object[]> responseEntity = processLineFrequencyRepository.getProcessLinesTotalQuarterly(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		response = ProcessLineFrequencyDataGridUtility.getGridDataQuarterly(lineList, responseEntity);
		responseData.add(response);
	}

	private void getMonthlyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		List<Map<String, Object>> response;
		List<Object[]> responseEntity = processLineFrequencyRepository.getProcessLinesTotalMonthly(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		response = ProcessLineFrequencyDataGridUtility.getGridDataMonthly(lineList, responseEntity);
		responseData.add(response);
	}

	private void getDailyFrequencyGridData(ProcessLineRequest processLineRequest, List<String> lineList,
			List<List<Map<String, Object>>> responseData) {
		List<Map<String, Object>> response;
		List<Map<String, Object>> responseEntity = processLineFrequencyRepository.findByDateForDataGrid(
				Utility.stringToDateConvertor(processLineRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(processLineRequest.getEndDate(), DashboardConstant.FORMAT),
				processLineRequest.getMillId(), processLineRequest.getBuTypeId(),
				processLineRequest.getKpiCategoryId(), processLineRequest.getBuId(), processLineRequest.getKpiId());
		response = ProcessLineFrequencyDataGridUtility.getGridDataDailyResponse(lineList, responseEntity);
		responseData.add(response);
	}
	
}
