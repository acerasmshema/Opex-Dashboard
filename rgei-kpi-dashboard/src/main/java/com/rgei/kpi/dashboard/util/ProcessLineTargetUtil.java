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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineProjectedResponse;

public class ProcessLineTargetUtil {
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ProcessLineTargetUtil.class);

	public static Long calculateTargetDifference(Long projectedTarget, Long actualTarget) {
		if(projectedTarget != null && actualTarget != null) {
			return (projectedTarget - actualTarget);
		}
		return null;
	}

	public static Long setProjectedTaeget(Long tarDiff, Integer targetDays, Integer dailyTargetValue) {
		Long newTarget = null;
		if(tarDiff>0) {
			Long val = targetDays.longValue();
			newTarget = (tarDiff / val);
			return (newTarget + dailyTargetValue);
		}
		return newTarget;
	}
	
	public String getProjectedDate(Integer targetDays) {
		SimpleDateFormat sdf = new SimpleDateFormat(DashboardConstant.FORMAT);
		Calendar c = Calendar.getInstance();
		try{
		   c.setTime(sdf.parse(new Date().toString()));
		}catch(ParseException e){
			logger.error("Parse Exception for date format", e, targetDays);
		 }
		c.add(Calendar.DAY_OF_MONTH, targetDays - 1);  
		return sdf.format(c.getTime());  
	}
	
	public static List<Date> processDateRange(Integer tergetDays) {
		
		Date projDate = Utility.stringToDateConvertor(new ProcessLineTargetUtil().getProjectedDate(tergetDays), DashboardConstant.FORMAT);
		Date startDate = new Date();
		List<Date> datesInRange = new ArrayList<>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(startDate);
	     
	    Calendar endCalendar = new GregorianCalendar();
	    endCalendar.setTime(projDate);
	 
	    while (calendar.before(endCalendar)) {
	        Date result = calendar.getTime();
	        datesInRange.add(result);
	        calendar.add(Calendar.DATE, 1);
	    }
		return datesInRange;
	}
	
	public static Integer processTargetDaysAsPerMaintainanceDays(List<Date> datesIn, List<MaintenanceDaysResponse>  maintainanceDays, Integer targetDays) {
		if(maintainanceDays != null && !maintainanceDays.isEmpty()) {
				for(Date tempDate:datesIn) {
					for(MaintenanceDaysResponse response:maintainanceDays) {
						LocalDate dateOne = LocalDate.parse(response.getMaintainceDates());
						LocalDate dateTwo = tempDate.toInstant()
							      .atZone(ZoneId.systemDefault())
							      .toLocalDate();
					if(dateOne.equals(dateTwo)){
						targetDays = targetDays + 1;
					}
				}
			}
		}

		return targetDays;
	}

	public static ProcessLineProjectedResponse populateResponse(Long projectedTargetValue, Integer finalTarget,
			String endDate, String annualTargetValue) {
		ProcessLineProjectedResponse response = new ProcessLineProjectedResponse();
		response.setStartDate(Utility.dateToStringConvertor(new Date(), DashboardConstant.FORMAT));
		response.setEndDate(endDate);
		response.setTargetDays(finalTarget.longValue());
		response.setProjectedTarget(projectedTargetValue);
		response.setAnnualTarget(annualTargetValue);
		return response;
	}
	

}
