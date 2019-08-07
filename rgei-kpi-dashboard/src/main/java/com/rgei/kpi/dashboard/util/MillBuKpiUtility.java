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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

public class MillBuKpiUtility {
	
	static CentralizedLogger logger = RgeiLoggerFactory.getLogger(ProcessLineTargetUtil.class);

	public static DateRangeResponse getDailySeriesResponse(List<String> targetDates, String targetValue)  {
		DateRangeResponse dateRangeResponse=null;
		List<SeriesObject> lineChartTargetSeries=null;
		try{
			dateRangeResponse=new DateRangeResponse();
			lineChartTargetSeries=new ArrayList<SeriesObject>(); 
			dateRangeResponse.setName(DashboardConstant.TARGET_LINE);
			for(String targetDate : targetDates) {
				SeriesObject seriesObject=new SeriesObject();
				seriesObject.setName(targetDate);
				seriesObject.setValue(parseProcessLineNullValue(targetValue));
				lineChartTargetSeries.add(seriesObject);
			}
			dateRangeResponse.setSeries(lineChartTargetSeries);
		}catch(Exception e) {
			logger.info("exception in fetching target data for KPI", e);
		}
		return dateRangeResponse;
	}

	public static double parseProcessLineNullValue(Object value) {
		return Objects.nonNull(value)?Double.valueOf(value.toString()):-1;
	}
	
}
