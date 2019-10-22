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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.response.model.ApiSuccessResponse;

public class Utility {
	
	private Utility() {
	}
	
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(Utility.class);
	
	public static Date stringToDateConvertor(String date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("Date Parsing Exception:", e, e); 
		}
		return null;
	}
	
	public static String dateToStringConvertor(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static java.util.Date getYesterdayDate() {
		LocalDate yesterdayDate= LocalDate.now().minusDays(1);
		Date yestDate = java.sql.Date.valueOf(yesterdayDate.toString());
		return new Date(yestDate.getTime());
	}
	
	public static Date getYesterDay(){
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(DashboardConstant.FORMAT);
		cal.add(Calendar.DATE, -1);
		try {
			return dateFormat.parse(dateFormat.format(cal.getTime()));
		} catch (ParseException e) {
			logger.error("Date Parsing Exception:", e, e); 
		}
		return null;
	}

	public static Date getDate() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	
	public static Integer covertToInteger(String integerValue) {
		return Integer.parseInt(integerValue);
	}
	
	public static double parseProcessLineValue(Double value) {
		return !value.isNaN()?value:-300;
	}
	
	public static ApiSuccessResponse getApiResponse(String description, String status) {
		return new ApiSuccessResponse(new Date(System.currentTimeMillis()).toString(), description, status);
	}
}
