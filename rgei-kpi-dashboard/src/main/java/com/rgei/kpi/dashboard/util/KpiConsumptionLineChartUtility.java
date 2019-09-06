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
import java.sql.Date;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.constant.Quarter;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

public class KpiConsumptionLineChartUtility {
	
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiConsumptionLineChartUtility.class);
	
	private KpiConsumptionLineChartUtility() {
	}

	public static List<SeriesObject> getDailySeriesResponse(String processLine, List<Object[]> values) {
		logger.info("Get daily series response for process line ", processLine);
		double value;
		ArrayList<SeriesObject> series = new ArrayList<>();
		for(Object[] obj: values) {
			SeriesObject val = new SeriesObject();
			val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.FORMAT));
			switch (processLine) {
			case DashboardConstant.PROCESS_LINE_FL1:
				value = parseProcessLineValue(obj[1].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL2:
				value = parseProcessLineValue(obj[2].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL3:
				value = parseProcessLineValue(obj[3].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PCD:
				value = parseProcessLineValue(obj[4].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD1:
				value = parseProcessLineValue(obj[5].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD2:
				value = parseProcessLineValue(obj[6].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD3:
				value = parseProcessLineValue(obj[7].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD4:
				value = parseProcessLineValue(obj[8].toString());
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			default:
			}
			series.add(val);
		}
	
	return series;
}
	
	public static List<SeriesObject> getQuarterlySeriesResponse(String processLine, List<Object[]> values) {
		logger.info("Get quarterly series response for process line ", processLine);
		ArrayList<SeriesObject> series = new ArrayList<>();
		for(Object[] obj: values) {
			SeriesObject val = new SeriesObject();
			switch (processLine) {
			case DashboardConstant.PROCESS_LINE_FL1:
				processFL1(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_FL2:
				processFL2(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_FL3:
				processFL3(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PCD:
				processPCD(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PD1:
				processPD1(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PD2:
				processPD2(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PD3:
				processPD3(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PD4:
				processPD4(obj, val);
				break;
			default:
			}
			series.add(val);
		}
		return series;
	}

	private static void processPD4(Object[] obj, SeriesObject val) {
		logger.info("Process PD4");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[8]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[8]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[8]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[8]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPD3(Object[] obj, SeriesObject val) {
		logger.info("Process PD3");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[7]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[7]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[7]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[7]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPD2(Object[] obj, SeriesObject val) {
		logger.info("Process PD2");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[6]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[6]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[6]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[6]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPD1(Object[] obj, SeriesObject val) {
		logger.info("Process PD1");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[5]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[5]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[5]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[5]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPCD(Object[] obj, SeriesObject val) {
		logger.info("Process PCD");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[4]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[4]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[4]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[4]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processFL3(Object[] obj, SeriesObject val) {
		logger.info("Process FL3");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[3]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[3]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[3]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[3]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processFL2(Object[] obj, SeriesObject val) {
		logger.info("Process FL2");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[2]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[2]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[2]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[2]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processFL1(Object[] obj, SeriesObject val) {
		logger.info("Process FL1");
		double value;
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[1]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[1]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[1]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			value = parseProcessLineNullValue(obj[1]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		}
	}
		
		public static List<SeriesObject> getMonthlySeriesResponse(String processLine, List<Object[]> values) {
			logger.info("Get monthly series response for process line ", processLine);
			double value;
			ArrayList<SeriesObject> series = new ArrayList<>();
			for(Object[] obj: values) {
				SeriesObject val = new SeriesObject();
				val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
			switch (processLine) {
			case DashboardConstant.PROCESS_LINE_FL1:
				value = parseProcessLineNullValue(obj[1]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL2:
				value = parseProcessLineNullValue(obj[2]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL3:
				value = parseProcessLineNullValue(obj[3]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PCD:
				value = parseProcessLineNullValue(obj[4]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD1:
				value = parseProcessLineNullValue(obj[5]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD2:
				value = parseProcessLineNullValue(obj[6]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD3:
				value = parseProcessLineNullValue(obj[7]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD4:
				value = parseProcessLineNullValue(obj[8]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			default:
			}
				series.add(val);
			}
		
		return series;
	}
		
		public static List<SeriesObject> getYearlySeriesResponse(String processLine, List<Object[]> values) {
			logger.info("Get yearly series response for process line ", processLine);
			double value;
			ArrayList<SeriesObject> series = new ArrayList<>();
			for(Object[] obj: values) {
			SeriesObject val = new SeriesObject();
			val.setName(String.valueOf(obj[0]).split("\\.")[0]);
			switch (processLine) {
			case DashboardConstant.PROCESS_LINE_FL1:
				value = parseProcessLineNullValue(obj[1]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL2:
				value = parseProcessLineNullValue(obj[2]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL3:
				value = parseProcessLineNullValue(obj[3]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PCD:
				value = parseProcessLineNullValue(obj[4]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD1:
				value = parseProcessLineNullValue(obj[5]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD2:
				value = parseProcessLineNullValue(obj[6]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD3:
				value = parseProcessLineNullValue(obj[7]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD4:
				value = parseProcessLineNullValue(obj[8]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			default:
			}
				series.add(val);
			}
		
		return series;
	}

		public static double parseProcessLineValue(String value) {
			return !value.equalsIgnoreCase(DashboardConstant.NAN)?Double.valueOf(value):-1;
		}
		
		public static double parseProcessLineNullValue(Object value) {
			return Objects.nonNull(value)?Double.valueOf(value.toString()):-1;
		}
}
