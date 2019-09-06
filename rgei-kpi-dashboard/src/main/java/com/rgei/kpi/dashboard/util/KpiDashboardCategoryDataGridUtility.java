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
import java.math.RoundingMode;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.constant.Quarter;

public class KpiDashboardCategoryDataGridUtility {
	
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiDashboardCategoryDataGridUtility.class);

	//no-arg constructor
	private KpiDashboardCategoryDataGridUtility() {
	}

	/*
	 * Api is to get the result for download data grid
	 */
	public static List<Map<String,Object>> getGridDataDailyResponse(List<String> lineList, List<Object[]> downloadGridResponse) {
		logger.info("Get grid data daily response");
		  List<Map<String, Object>> transferList = new ArrayList<>();
		  for(Object[] obj: downloadGridResponse) {
				Map<String, Object> transferMap = new HashMap<>();
				transferMap.put(DashboardConstant.DATE, obj[0]);
				for(String processLine: lineList) {
				createResponseForDailyFrequency(obj, transferMap, processLine);
				}
				transferList.add(transferMap);
			}
		  
		  return transferList;
		 
	}
	
	public static List<Map<String,Object>> getGridDataMonthly(List<String> lineList, List<Object[]> downloadGridResponse) {
		logger.info("Get grid data monthly");
		List<Map<String, Object>> transferList = new ArrayList<>();
			for(Object[] obj: downloadGridResponse) {
				Map<String, Object> transferMap = new HashMap<>();
				for(String processLine: lineList) {
				
				transferMap.put(DashboardConstant.DATE, Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
				createResponse(obj, transferMap, processLine);
				
				
				}
				transferList.add(transferMap);
			}
		
		
		return transferList;
	}
	
	
	public static List<Map<String,Object>> getGridDataQuarterly(List<String> lineList, List<Object[]> downloadGridResponse) {
		logger.info("Get grid data quarterly");
		List<Map<String, Object>> transferList = new ArrayList<>();
		for (Object[] obj : downloadGridResponse) {
			Map<String, Object> transferMap = new HashMap<>();
			for (String processLine : lineList) {
				if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
					transferMap.put(DashboardConstant.DATE, Quarter.Q1.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
					createResponse(obj, transferMap, processLine);
				} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
					transferMap.put(DashboardConstant.DATE, Quarter.Q2.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
					createResponse(obj, transferMap, processLine);
				} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
					transferMap.put(DashboardConstant.DATE, Quarter.Q3.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
					createResponse(obj, transferMap, processLine);
				} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
					transferMap.put(DashboardConstant.DATE, Quarter.Q4.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
					createResponse(obj, transferMap, processLine);
				}
			}
			transferList.add(transferMap);
		}
			
		return transferList;
	}

	public static List<Map<String,Object>> getGridDataYearly(List<String> lineList, List<Object[]> downloadGridResponse) {
		logger.info("Get grid data yearly");
		List<Map<String, Object>> transferList = new ArrayList<>();
		for (Object[] obj : downloadGridResponse) {
			Map<String, Object> transferMap = new HashMap<>();
			for (String processLine : lineList) {
					transferMap.put(DashboardConstant.DATE, String.valueOf(obj[0]).split("\\.")[0]);
					createResponse(obj, transferMap, processLine);
				}
			transferList.add(transferMap);
			}
		return transferList;
	}
	
	private static void createResponseForDailyFrequency(Object[] obj, Map<String, Object> transferMap, String processLine) {
		logger.info("Create response for daily frequency for process line ", processLine);
		String value = null;
		switch(processLine) {
		case DashboardConstant.PROCESS_LINE_FL1:
			value = parseNaNValue(Double.valueOf(obj[1].toString()));
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_FL2:
			value = parseNaNValue(Double.valueOf(obj[2].toString()));
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_FL3:
			value = parseNaNValue(Double.valueOf(obj[3].toString()));
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PCD:
			value = parseNaNValue(Double.valueOf(obj[4].toString()));
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD1:
			value = parseNaNValue(Double.valueOf(obj[5].toString()));
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			value = parseNaNValue(Double.valueOf(obj[6].toString()));
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			value = parseNaNValue(Double.valueOf(obj[7].toString()));
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD4:
			value = parseNaNValue(Double.valueOf(obj[8].toString()));
			transferMap.put(processLine, value);
			break;
		default:
		}
	}
	
	private static void createResponse(Object[] obj, Map<String, Object> transferMap, String processLine) {
		logger.info("Create response for process line ", processLine);
		String value = null;
		switch(processLine) {
		case DashboardConstant.PROCESS_LINE_FL1:
			value = parseProcessLineNullValue(obj[1]);
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_FL2:
			value = parseProcessLineNullValue(obj[2]);
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_FL3:
			value = parseProcessLineNullValue(obj[3]);
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PCD:
			value = parseProcessLineNullValue(obj[4]);
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD1:
			value = parseProcessLineNullValue(obj[5]);
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			value = parseProcessLineNullValue(obj[6]);
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			value = parseProcessLineNullValue(obj[7]);
			transferMap.put(processLine, value);
			break;
		case DashboardConstant.PROCESS_LINE_PD4:
			value = parseProcessLineNullValue(obj[8]);
			transferMap.put(processLine, value);
			break;
		default:
		}
	}
	
	public static String parseNaNValue(Double value) {
		return value.isNaN()?DashboardConstant.NA: BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).toString();
	}
	
	public static double parseProcessLineValue(String value) {
		return !value.equalsIgnoreCase("NaN")?Double.valueOf(value):-1;
	}
	
	public static String parseProcessLineNullValue(Object value) {
		return !Objects.nonNull(value)?DashboardConstant.NA:BigDecimal.valueOf(Double.valueOf(value.toString())).setScale(2, RoundingMode.CEILING).toString();
		
	}
}
