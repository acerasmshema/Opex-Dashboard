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

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.constant.Quarter;

public class ProcessLineFrequencyDataGridUtility {
	
	private ProcessLineFrequencyDataGridUtility() {
	}

	/*
	 * Api is to get the result for download data grid
	 */
	public static List<Map<String,Object>> getGridDataDailyResponse(List<String> lineList, List<Map<String, Object>> downloadGridResponse) {
		List<Map<String, Object>> transferList = new ArrayList<>();
			downloadGridResponse.forEach(item -> {
				Map<String, Object> transferMap = new HashMap<>();
				for(Map.Entry<String, Object> entry : item.entrySet()) {
					if(entry.getKey().equals(DashboardConstant.DATE) || ((Double) entry.getValue()).isNaN()) {
						transferMap.put(entry.getKey(), entry.getValue());
						continue;
					}
					for(String processLine: lineList) {
					if(entry.getKey().equalsIgnoreCase(processLine)) {
					transferMap.put(entry.getKey(), (new BigDecimal(entry.getValue().toString()).setScale(0, RoundingMode.CEILING)));
					}
					}
				}
				transferList.add(transferMap);
			});
		
		return transferList;
	}
	
	public static List<Map<String,Object>> getGridDataMonthly(List<String> lineList, List<Object[]> downloadGridResponse) {
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
	
	private static void createResponse(Object[] obj, Map<String, Object> transferMap, String processLine) {
		switch(processLine) {
		case DashboardConstant.PROCESS_LINE_FL1:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_FL2:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_FL3:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PCD:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD1:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[6].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[7].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD4:
			transferMap.put(processLine.toLowerCase(), new BigDecimal(obj[8].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
			break;
		default:
		}
	}
}
