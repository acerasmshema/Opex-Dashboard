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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.DailyKpiPulpEntity;
import com.rgei.kpi.dashboard.entities.MillBuKpiCategoryEntity;
import com.rgei.kpi.dashboard.response.model.ProcessLineAnnualResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineSeries;
import com.rgei.kpi.dashboard.response.model.TargetProceessLine;

public class ProcessLineExtendedUtil {
	
	private ProcessLineExtendedUtil() {
	}

	public static TargetProceessLine generateExtendedResponse(ProcessLineAnnualResponse processLineAnnualResponse) {
		TargetProceessLine target = null;
		if(processLineAnnualResponse != null) {
			target = new TargetProceessLine();
			List<ProcessLineSeries>  series = new LinkedList<>();
			target.setName("Actual");
			series.add(0,setFixedParameter(setFirstDateOfTheYear()));
			series.add(1,setDynamicParameter(processLineAnnualResponse));
			target.setSeries(series);
		}
		return target;
	}
	
	public static TargetProceessLine generateExtendedResponse(List<ProcessLineSeries> series) {
		TargetProceessLine target = null;
		if(series != null) {
			target = new TargetProceessLine();
			target.setName("Actual");
			target.setSeries(series);
		}
		return target;
	}

	private static ProcessLineSeries setFixedParameter(String dateOfYear) {
		ProcessLineSeries series = new ProcessLineSeries();
		series.setName(dateOfYear);
		series.setValue(0L);
		return series;
	}

	private static ProcessLineSeries setDynamicParameter(ProcessLineAnnualResponse processLineAnnualResponse) {
		ProcessLineSeries series = new ProcessLineSeries();
		series.setName(setYesterday());
		series.setValue(processLineAnnualResponse.getTotalYTDProduction().longValue());
		return series;
	}
	
	@SuppressWarnings("unused")
	private static ProcessLineSeries setDynamicParameter(MillBuKpiCategoryEntity millBuKpiCategoryEntity) {
		ProcessLineSeries series = new ProcessLineSeries();
		series.setName(setLastDateOfTheYear());
		series.setValue(Long.valueOf(millBuKpiCategoryEntity.getAnnualTarget()));
		return series;
	}

	private static String setFirstDateOfTheYear() {
		Integer year = DailyKpiPulpConverter.getYesterdayDate().toLocalDate().getYear();
		DailyKpiPulpConverter.getYesterdayDate();
		return "01-Jan-" + year;
	}
	
	private static String setLastDateOfTheYear() {
		Integer year = DailyKpiPulpConverter.getYesterdayDate().toLocalDate().getYear();
		DailyKpiPulpConverter.getYesterdayDate();
		return "31-Dec-" + year;
	}
	
	
	private static Long calculateNoOfDays() {
		Integer year = DailyKpiPulpConverter.getYesterdayDate().toLocalDate().getYear();
		LocalDate startDate = LocalDate.of(year, Month.JANUARY, 01);
	    return java.time.temporal.ChronoUnit.DAYS.between(startDate, LocalDate.now().minusDays(1));
	}	
	
	private static Double calculatedDailyTarget(MillBuKpiCategoryEntity millBuKpiCategoryEntity) {
		return Double.valueOf(millBuKpiCategoryEntity.getDailyTarget() * calculateNoOfDays());
	}
	
	private static ProcessLineSeries setCalculatedTarget(MillBuKpiCategoryEntity millBuKpiCategoryEntity) {
		ProcessLineSeries series = new ProcessLineSeries();
		series.setName(setYesterday());
		series.setValue(calculatedDailyTarget(millBuKpiCategoryEntity).longValue());
		return series;
	}
	
	private static String setYesterday() {
		  String str1 = "dd-MMM-yyyy";
		  Calendar cal = Calendar.getInstance();
		  cal.add(Calendar.DATE, -1);
	      Date d = cal.getTime();
	      SimpleDateFormat sdf = new SimpleDateFormat(str1, Locale.ENGLISH);
	      return sdf.format(d);
	}
	
	private static String getFormattedLocalDate(LocalDate date) {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DashboardConstant.EXTENDED_DATE_FORMAT);		
		  return dtf.format(date);
	}
	public static TargetProceessLine generateTargetResponse(MillBuKpiCategoryEntity millBuKpiCategoryEntity) {
		TargetProceessLine target = null;
		if(millBuKpiCategoryEntity != null) {
			target = new TargetProceessLine();
			List<ProcessLineSeries>  series = new LinkedList<>();
			target.setName("Target");
			series.add(0,setFixedParameter(setFirstDateOfTheYear()));
			series.add(1,setCalculatedTarget(millBuKpiCategoryEntity));
			target.setSeries(series);
		}
		return target;
		
	}
	
	public static TargetProceessLine generateTargetResponseV2(MillBuKpiCategoryEntity millBuKpiCategoryEntity, List<DailyKpiPulpEntity> dailyKpiEntities) {
		TargetProceessLine target = null;
		if(millBuKpiCategoryEntity != null) {
			target = new TargetProceessLine();
			List<ProcessLineSeries>  series = new LinkedList<>();
			target.setName("Target");
			int index = 1;
			for(DailyKpiPulpEntity item : dailyKpiEntities) {
				series.add(new ProcessLineSeries(
							getFormattedLocalDate(item.getDatetime().toLocalDateTime().toLocalDate()), 
							millBuKpiCategoryEntity.getDailyTarget().longValue() * index++
						));
			}
			target.setSeries(series);
		}
		return target;
		
	}
	
	public static Long calculateTargetValue(MillBuKpiCategoryEntity millBuKpiCategoryEntity) {
		Long target = null;
		if(millBuKpiCategoryEntity != null) {
			target = calculatedDailyTarget(millBuKpiCategoryEntity).longValue();
		}
		return target;
		
	}
	


}
