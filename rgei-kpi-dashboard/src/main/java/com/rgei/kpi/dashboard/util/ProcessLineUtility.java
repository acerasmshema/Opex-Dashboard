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
package com.rgei.kpi.dashboard.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;
import com.rgei.kpi.dashboard.entities.DailyKpiPulpEntity;
import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.response.model.BuTypeResponse;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.MillDetail;
import com.rgei.kpi.dashboard.response.model.ProcessLine;
import com.rgei.kpi.dashboard.response.model.ProcessLineDetailsResponse;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

public class ProcessLineUtility {
	
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(ProcessLineUtility.class);

	private ProcessLineUtility() {
	}

	private static NumberFormat formatter = NumberFormat.getInstance();

	public static List<String> getRequestedProcessLines() {
		List<String> processLines = new ArrayList<>();
		processLines.add(DashboardConstant.PROCESS_LINE_FL1);
		processLines.add(DashboardConstant.PROCESS_LINE_FL2);
		processLines.add(DashboardConstant.PROCESS_LINE_FL3);
		return processLines;
	}

	public static List<String> fetchProcessLines(List<ProcessLine> processLines, List<String> lineList) {
		logger.info("Fetch process lines");
		List<String> processLinesList = new ArrayList<>();
		List<String> finalKpiProcessLines = new ArrayList<>();
		for (ProcessLine line : processLines) {
			processLinesList.add(line.getProcessLineCode());
		}
		if (lineList.isEmpty()) {
			return processLinesList;
		} else {
			for (String processLine : processLinesList) {
				if (lineList.contains(processLine)) {
					finalKpiProcessLines.add(processLine);
				}
			}
		}
		return finalKpiProcessLines;
	}

	public static java.util.Date getYesterdayDate() {
		LocalDate yesterdayDate = LocalDate.now().minusDays(1);
		Date yestDate = java.sql.Date.valueOf(yesterdayDate.toString());
		return new Date(yestDate.getTime());
	}

	public static List<ProcessLine> convertToProcessLineDTO(List<ProcessLineEntity> entities, Map<Integer, List<BigDecimal>> processLineConfigurationMap) {
		List<ProcessLine> processLine = new ArrayList<>();
		Date date=new Date();
		sortProcessLines(entities);
		for (ProcessLineEntity entity : entities) {
			ProcessLine processLineObject = new ProcessLine();
			processLineObject.setProcessLineId(entity.getProcessLineId());
			processLineObject.setProcessLineCode(entity.getProcessLineCode());
			processLineObject.setProcessLineName(entity.getProcessLineName());
			processLineObject.setMillId(entity.getMill().getMillId());
			processLineObject.setMinTarget(processLineConfigurationMap.get(entity.getProcessLineId()).get(0));
			processLineObject.setMaxTarget(processLineConfigurationMap.get(entity.getProcessLineId()).get(1));
			processLineObject.setRangeValue(entity.getRangeValue());
			processLineObject.setColorRange(entity.getColorRange());
			processLineObject.setDailyLineTarget(processLineConfigurationMap.get(entity.getProcessLineId()).get(2));
			processLine.add(processLineObject);
		}
		return processLine;
	}

	private static void sortProcessLines(List<ProcessLineEntity> kpiType) {
		Collections.sort(kpiType, (o1, o2) -> {
			int value1 = o1.getProcessLineOrder().compareTo(o2.getProcessLineOrder());
			if (value1 == 0) {
				return o1.getProcessLineOrder().compareTo(o2.getProcessLineOrder());
			}
			return value1;
		});
	}

	/*
	 * Api is to get the result for download data grid
	 */
	public static List<Map<String, Object>> parseCurrencyFormat(List<Map<String, Object>> downloadGridResponse) {
		List<Map<String, Object>> transferList = new ArrayList<>();
		downloadGridResponse.forEach(item -> {
			Map<String, Object> transferMap = new HashMap<>();
			for (Map.Entry<String, Object> entry : item.entrySet()) {
				if (entry.getKey().equals("date") || ((Double) entry.getValue()).isNaN()) {
					transferMap.put(entry.getKey(), entry.getValue());
					continue;
				}
				transferMap.put(entry.getKey(), formatter.format(entry.getValue()));
			}
			transferList.add(transferMap);
		});
		return transferList;
	}

	public static List<DateRangeResponse> createDailyKpiPulpResponseForLineChart(
			List<DailyKpiPulpEntity> dailyKpiPulpEntities) {
		List<DateRangeResponse> resultList = new ArrayList<>();
		List<String> lineList = Arrays.asList(DashboardConstant.PROCESS_LINE_FL1, DashboardConstant.PROCESS_LINE_FL2,
				DashboardConstant.PROCESS_LINE_FL3, DashboardConstant.PROCESS_LINE_PCD,
				DashboardConstant.PROCESS_LINE_PD1, DashboardConstant.PROCESS_LINE_PD2,
				DashboardConstant.PROCESS_LINE_PD3, DashboardConstant.PROCESS_LINE_PD4);
		lineList.forEach(item -> {
			DateRangeResponse processObj = new DateRangeResponse(item, new ArrayList<>());
			resultList.add(processObj);
		});

		List<SeriesObject> processLine1 = resultList.get(0).getSeries();
		List<SeriesObject> processLine2 = resultList.get(1).getSeries();
		List<SeriesObject> processLine3 = resultList.get(2).getSeries();
		List<SeriesObject> processLine4 = resultList.get(3).getSeries();
		List<SeriesObject> processLine5 = resultList.get(4).getSeries();
		List<SeriesObject> processLine6 = resultList.get(5).getSeries();
		List<SeriesObject> processLine7 = resultList.get(6).getSeries();
		List<SeriesObject> processLine8 = resultList.get(7).getSeries();

		createResponse(dailyKpiPulpEntities, processLine1, processLine2, processLine3, processLine4, processLine5,
				processLine6, processLine7, processLine8);

		return resultList;
	}

	/*
	 * creating daily kpi pulp enetity to send as response
	 */
	public static void createResponse(List<DailyKpiPulpEntity> dailyKpiPulpEntities, List<SeriesObject> processLine1,
			List<SeriesObject> processLine2, List<SeriesObject> processLine3, List<SeriesObject> processLine4,
			List<SeriesObject> processLine5, List<SeriesObject> processLine6, List<SeriesObject> processLine7,
			List<SeriesObject> processLine8) {
		dailyKpiPulpEntities.forEach(item -> {

			processLine1.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine1())));
			processLine2.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine2())));
			processLine3.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine3())));
			processLine4.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine4())));
			processLine5.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine5())));
			processLine6.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine6())));
			processLine7.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine7())));
			processLine8.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(item.getProcessLine8())));
		});
	}

	public static List<DateRangeResponse> createDailyTargetLineResponse(List<ProcessLine> processLine,
			List<DailyKpiPulpEntity> dailyKpiPulpEntities) {
		List<DateRangeResponse> dailyTargetResponse = new ArrayList<>();
		List<String> lineList = new ArrayList<>();
		for (ProcessLine line : processLine) {
			lineList.add(DashboardConstant.TARGET_LINE);
		}
		lineList.forEach(item -> {
			DateRangeResponse processObj = new DateRangeResponse(item, new ArrayList<>());
			dailyTargetResponse.add(processObj);
		});
		List<SeriesObject> processLine1 = null;
		List<SeriesObject> processLine2 = null;
		List<SeriesObject> processLine3 = null;
		List<SeriesObject> processLine4 = null;
		List<SeriesObject> processLine5 = null;
		List<SeriesObject> processLine6 = null;
		List<SeriesObject> processLine7 = null;
		List<SeriesObject> processLine8 = null;
		try {
			processLine1 = dailyTargetResponse.get(0).getSeries();
			processLine2 = dailyTargetResponse.get(1).getSeries();
			processLine3 = dailyTargetResponse.get(2).getSeries();
			processLine4 = dailyTargetResponse.get(3).getSeries();
			processLine5 = dailyTargetResponse.get(4).getSeries();
			processLine6 = dailyTargetResponse.get(5).getSeries();
			processLine7 = dailyTargetResponse.get(6).getSeries();
			processLine8 = dailyTargetResponse.get(7).getSeries();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException();
		}
		populateDailyLineTargetReponse(processLine, dailyKpiPulpEntities, processLine1, processLine2, processLine3,
				processLine4, processLine5, processLine6, processLine7, processLine8);

		return dailyTargetResponse;

	}

	public static void populateDailyLineTargetReponse(List<ProcessLine> processLine,
			List<DailyKpiPulpEntity> dailyKpiPulpEntities, List<SeriesObject> processLine1,
			List<SeriesObject> processLine2, List<SeriesObject> processLine3, List<SeriesObject> processLine4,
			List<SeriesObject> processLine5, List<SeriesObject> processLine6, List<SeriesObject> processLine7,
			List<SeriesObject> processLine8) {
		dailyKpiPulpEntities.forEach(item -> {

			processLine1
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(0).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
			processLine2
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(1).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
			processLine3
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(2).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
			processLine4
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(3).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
			processLine5
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(4).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
			processLine6
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(5).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
			processLine7
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(6).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
			processLine8
					.add(new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT),
							(processLine.get(7).getThresholdMap().get(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.FORMAT)).doubleValue())));
		});
	}

	public static List<ProcessLineDetailsResponse> generateResponse(List<ProcessLineEntity> processLine) {
		ProcessLineDetailsResponse response = null;
		List<ProcessLineDetailsResponse> responses = new ArrayList<>();
		if (processLine != null && !processLine.isEmpty()) {
			for (ProcessLineEntity entity : processLine) {
				response = new ProcessLineDetailsResponse();
				response.setProcessLineId(entity.getProcessLineId());
				response.setProcessLineCode(entity.getProcessLineCode());
				response.setProcessLineName(entity.getProcessLineName());
				response.setLegendColor(entity.getLegendColor());
				responses.add(response);
			}
		}
		return responses;
	}

	public static List<MillDetail> prePareMillResponse(List<MillEntity> mills) {

		MillDetail millObject = null;
		List<MillDetail> response = null;
		if (mills != null && !mills.isEmpty()) {
			response = new ArrayList<>();
			for (MillEntity millEntity : mills) {
				millObject = new MillDetail();
				millObject.setMillId(millEntity.getMillId().toString());
				millObject.setMillCode(millEntity.getMillCode());
				millObject.setMillName(millEntity.getMillName());
				millObject.setCountry(CommonFunction.convertCountryEntityToResponse(millEntity.getCountry()));
				response.add(millObject);
			}
		}else {
			throw new RecordNotFoundException("No records for mills data");
		}
		sortMillResponseByMillName(response);
		return response;
	}

	public static List<BuTypeResponse> preareBUTypeResponse(List<BusinessUnitTypeEntity> businessUnitTypeEntityList) {
		List<BuTypeResponse> buTypeList = new ArrayList<>();
		BuTypeResponse buTypeResponse = null;
		if (buTypeList != null) {
			for (BusinessUnitTypeEntity businessUnitTypeEntity : businessUnitTypeEntityList) {
				if (Boolean.TRUE.equals(businessUnitTypeEntity.getActive())) {
					buTypeResponse = new BuTypeResponse();
					buTypeResponse.setBuTypeId(businessUnitTypeEntity.getBusinessUnitTypeId());
					buTypeResponse.setBuId(businessUnitTypeEntity.getBusinessUnit().getBusinessUnitId());
					buTypeResponse.setBuTypeCode(businessUnitTypeEntity.getBusinessUnitTypeCode());
					buTypeResponse.setBuTypeName(businessUnitTypeEntity.getBusinessUnitTypeName());
					buTypeList.add(buTypeResponse);
					sortBuTypeResponse(buTypeList);
				}
			}
		}
		return buTypeList;
	}

	private static void sortMillResponseByMillName(List<MillDetail> mills) {
		Collections.sort(mills, (o1, o2) -> {
			int value1 = o1.getMillName().compareTo(o2.getMillName());
			if (value1 == 0) {
				return o1.getMillName().compareTo(o2.getMillName());
			}
			return value1;
		});
	}
	
	private static void sortBuTypeResponse(List<BuTypeResponse> buTypeList) {
		Collections.sort(buTypeList, (o1, o2) -> {
			int value1 = o1.getBuTypeId().compareTo(o2.getBuTypeId());
			if (value1 == 0) {
				return o1.getBuTypeId().compareTo(o2.getBuTypeId());
			}
			return value1;
		});
	}

	public static Map<Integer,List<BigDecimal>> convertToApplicableConfguration(
			List<ProcessLineConfigurationEntity> processLineConfigurationEntityList) {
		List<Integer> ProcessLineList=new ArrayList<Integer>();
		Map<Integer,List<BigDecimal>> processLineConfigurationMap=new HashMap<Integer,List<BigDecimal>>();
		processLineConfigurationEntityList.forEach(item->ProcessLineList.add(item.getProcessLineId()));
		for(ProcessLineConfigurationEntity processLineConfigurationEntity:processLineConfigurationEntityList) {
			if(!processLineConfigurationEntity.getIsDefault() ) {
				List<BigDecimal> configurationValueList= new ArrayList<BigDecimal>();
				configurationValueList.add(new BigDecimal( processLineConfigurationEntity.getMinimum()));
				configurationValueList.add(new BigDecimal( processLineConfigurationEntity.getMaximum()));
				configurationValueList.add(new BigDecimal( processLineConfigurationEntity.getThreshold()));
				processLineConfigurationMap.put(processLineConfigurationEntity.getProcessLineId(), configurationValueList);
			}else if(processLineConfigurationEntity.getIsDefault() && !processLineConfigurationMap.containsKey(processLineConfigurationEntity.getProcessLineId())) {
				List<BigDecimal> configurationValueList= new ArrayList<BigDecimal>();
				configurationValueList.add(new BigDecimal( processLineConfigurationEntity.getMinimum()));
				configurationValueList.add(new BigDecimal( processLineConfigurationEntity.getMaximum()));
				configurationValueList.add(new BigDecimal( processLineConfigurationEntity.getThreshold()));
				processLineConfigurationMap.put(processLineConfigurationEntity.getProcessLineId(), configurationValueList);
			}
		}
		return processLineConfigurationMap;
	}
	
	public static Map<Integer,Map<String,BigDecimal>> convertToApplicableConfgurationForTargetLine(
			List<ProcessLineConfigurationEntity> processLineConfigurationEntityList, Date startDate, Date endDate) {
		List<Integer> ProcessLineList=new ArrayList<Integer>();
		Map<Integer,Map<String,BigDecimal>> processLineConfigurationMap=new HashMap<Integer,Map<String,BigDecimal>>();
		
		processLineConfigurationEntityList.forEach(item->ProcessLineList.add(item.getProcessLineId()));
		
		for(ProcessLineConfigurationEntity processLineConfigurationEntity:processLineConfigurationEntityList) {
			if(processLineConfigurationEntity.getIsDefault() ) {
				Map<String,BigDecimal> configurationValueMap= new HashMap<String,BigDecimal>();
				List<String> applicableDates=null;
				try {
					applicableDates = generatesAllDatesBetweenTwoDates(startDate,endDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				applicableDates.forEach(thresholdDate->
				configurationValueMap.put(thresholdDate, new BigDecimal(processLineConfigurationEntity.getThreshold()))
				);
				processLineConfigurationMap.put(processLineConfigurationEntity.getProcessLineId(), configurationValueMap);
			}
		}
		for(ProcessLineConfigurationEntity processLineConfigurationEntity:processLineConfigurationEntityList) {
			if(!processLineConfigurationEntity.getIsDefault() ) {
				Map<String,BigDecimal> configurationValueMap= processLineConfigurationMap.get(processLineConfigurationEntity.getProcessLineId());
				List<String> applicableDates=null;
				try {
					applicableDates = generatesAllDatesBetweenTwoDates(processLineConfigurationEntity.getStartDate(),processLineConfigurationEntity.getEndDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				applicableDates.forEach(thresholdDate->
				configurationValueMap.put(thresholdDate, new BigDecimal(processLineConfigurationEntity.getThreshold()))
				);
				processLineConfigurationMap.put(processLineConfigurationEntity.getProcessLineId(), configurationValueMap);
			}
		}
		return processLineConfigurationMap;
}
	
	public static List<String> generatesAllDatesBetweenTwoDates(Date startDate,Date endDate) throws ParseException {
		List<String> dates = new ArrayList<String>();
		long interval = 24*1000 * 60 * 60; 
		long endTime =endDate.getTime() ; 
		long curTime = startDate.getTime();
		while (curTime <= endTime) {
		    dates.add(new SimpleDateFormat("yyyy-MM-dd").format(new Date(curTime)));
		    curTime += interval;
		}
		return dates;
	}
	
	public static List<ProcessLine> convertToProcessLineDTOForTargetLine(List<ProcessLineEntity> entities, Map<Integer, Map<String, BigDecimal>> processLineConfigurationMap) {
		List<ProcessLine> processLine = new ArrayList<>();
		Date date=new Date();
		sortProcessLines(entities);
		for (ProcessLineEntity entity : entities) {
			ProcessLine processLineObject = new ProcessLine();
			processLineObject.setProcessLineId(entity.getProcessLineId());
			processLineObject.setProcessLineCode(entity.getProcessLineCode());
			processLineObject.setProcessLineName(entity.getProcessLineName());
			processLineObject.setMillId(entity.getMill().getMillId());
			processLineObject.setRangeValue(entity.getRangeValue());
			processLineObject.setColorRange(entity.getColorRange());
			processLineObject.setThresholdMap(processLineConfigurationMap.get(entity.getProcessLineId()));
			processLine.add(processLineObject);
		}
		return processLine;
	}
	
}