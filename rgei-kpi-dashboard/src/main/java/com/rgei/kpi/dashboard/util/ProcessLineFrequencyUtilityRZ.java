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

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.constant.Quarter;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

public class ProcessLineFrequencyUtilityRZ {
	
	private ProcessLineFrequencyUtilityRZ() {
	}

	public static List<SeriesObject> getDailySeriesResponse(String processLine, List<Object[]> values) {
		ArrayList<SeriesObject> series = new ArrayList<>();
		for(Object[] obj: values) {
			SeriesObject val = new SeriesObject();
			switch (processLine) {
			case DashboardConstant.PROCESS_LINE_PD1:
					val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.FORMAT));
					val.setValue(new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD2:
				val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.FORMAT));
				val.setValue(new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD3:
				val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.FORMAT));
				val.setValue(new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PL11:
				val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.FORMAT));
				val.setValue(new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PL12:
				val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.FORMAT));
				val.setValue(new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
				break;
			default:
			}
			series.add(val);
		}
	
	return series;
}
	
	public static List<SeriesObject> getQuarterlySeriesResponse(String processLine, List<Object[]> values) {
		ArrayList<SeriesObject> series = new ArrayList<>();
		for(Object[] obj: values) {
			SeriesObject val = new SeriesObject();
			switch (processLine) {
			case DashboardConstant.PROCESS_LINE_PD1:
				processPD1(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PD2:
				processPD2(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PD3:
				processPD3(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PL11:
				processPL11(obj, val);
				break;
			case DashboardConstant.PROCESS_LINE_PL12:
				processPL12(obj, val);
				break;
			default:
			}
			series.add(val);
		}
		return series;
	}

	private static void processPL12(Object[] obj, SeriesObject val) {
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPL11(Object[] obj, SeriesObject val) {
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPD3(Object[] obj, SeriesObject val) {
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPD2(Object[] obj, SeriesObject val) {
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		}
	}

	private static void processPD1(Object[] obj, SeriesObject val) {
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			val.setValue(new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
		}
	}
		
		public static List<SeriesObject> getMonthlySeriesResponse(String processLine, List<Object[]> values) {
			ArrayList<SeriesObject> series = new ArrayList<>();
			for(Object[] obj: values) {
				SeriesObject val = new SeriesObject();
				switch (processLine) {
				case DashboardConstant.PROCESS_LINE_PD1:
					val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PD2:
					val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PD3:
					val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PL11:
					val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PL12:
					val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				default:
				}
				series.add(val);
			}
		
		return series;
	}
		
		public static List<SeriesObject> getYearlySeriesResponse(String processLine, List<Object[]> values) {
			ArrayList<SeriesObject> series = new ArrayList<>();
			for(Object[] obj: values) {
				SeriesObject val = new SeriesObject();
				switch (processLine) {
				case DashboardConstant.PROCESS_LINE_PD1:
						val.setName(String.valueOf(obj[0]).split("\\.")[0]);
						val.setValue(new BigDecimal(obj[1].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PD2:
					val.setName(String.valueOf(obj[0]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[2].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PD3:
					val.setName(String.valueOf(obj[0]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[3].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PL11:
					val.setName(String.valueOf(obj[0]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[4].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				case DashboardConstant.PROCESS_LINE_PL12:
					val.setName(String.valueOf(obj[0]).split("\\.")[0]);
					val.setValue(new BigDecimal(obj[5].toString()).setScale(0, RoundingMode.CEILING).doubleValue());
					break;
				default:
				}
				series.add(val);
			}
		
		return series;
	}

}
