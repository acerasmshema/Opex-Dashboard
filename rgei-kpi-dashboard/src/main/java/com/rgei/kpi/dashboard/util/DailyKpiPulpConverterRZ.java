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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.DailyKpiPulpEntity;
import com.rgei.kpi.dashboard.entities.MillBuKpiCategoryEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.response.model.DailyKpiPulp;
import com.rgei.kpi.dashboard.response.model.DailyKpiPulpResponse;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLine;
import com.rgei.kpi.dashboard.response.model.ProcessLineAnnualResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineSeries;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

public class DailyKpiPulpConverterRZ {

	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(DailyKpiPulpConverterRZ.class);

	// no-arg constructor
	private DailyKpiPulpConverterRZ() {

	}

	public static Date getYesterdayDate() {
		LocalDate yesterdayDate = LocalDate.now().minusDays(1);
		return Date.valueOf(yesterdayDate.toString());
	}

	public static Integer covertToInteger(String integerValue) {
		return Integer.parseInt(integerValue);
	}

	public static List<String> getRequestedProcessLines() {
		List<String> processLines = new ArrayList<>();
		processLines.add(DashboardConstant.PROCESS_LINE_PL11);
		processLines.add(DashboardConstant.PROCESS_LINE_PL12);
		return processLines;
	}

	public static ProcessLineResponse prePareResponse(List<ProcessLineEntity> processLineEntites,
			List<DailyKpiPulpEntity> dailyKpiPulpEntities, MillBuKpiCategoryEntity millBuKpiCategoryEntity) {
		ProcessLineResponse response = new ProcessLineResponse();
		Double allProcessSum = 0.0D;
		if (!dailyKpiPulpEntities.isEmpty()) {
			for (DailyKpiPulpEntity entity : dailyKpiPulpEntities) {
				if (Double.isNaN(entity.getProcessLine4()) && Double.isNaN(entity.getProcessLine5())) {
					response.setTotalAverageValue(Double.NaN);
				} else {
					handleNaNCondition(entity);
					Double total = 0.0;
					Double sumDouble = Double.sum(entity.getProcessLine4() + entity.getProcessLine5(), total);
					allProcessSum += sumDouble;
					response.setTotalAverageValue(
							BigDecimal.valueOf(allProcessSum).setScale(0, RoundingMode.CEILING).doubleValue());
				}
			}
		} else {
			if (dailyKpiPulpEntities.isEmpty()) {
				response.setTotalAverageValue(Double.NaN);
			}
		}
		return populateTarget(processLineEntites, response, millBuKpiCategoryEntity);
	}

	private static void handleNaNCondition(DailyKpiPulpEntity entity) {
		if (Double.isNaN(entity.getProcessLine1())) {
			entity.setProcessLine1(0.00D);
		} else if (Double.isNaN(entity.getProcessLine2())) {
			entity.setProcessLine2(0.00D);
		} else if (Double.isNaN(entity.getProcessLine3())) {
			entity.setProcessLine3(0.00D);
		}
	}

	private static ProcessLineResponse populateTarget(List<ProcessLineEntity> processLineEntites,
			ProcessLineResponse response, MillBuKpiCategoryEntity millBuKpiCategoryEntity) {
		response.setMinValue(BigDecimal.valueOf(millBuKpiCategoryEntity.getMinTarget()));
		response.setMaxValue(BigDecimal.valueOf(millBuKpiCategoryEntity.getMaxTarget()));

		response.setRange(millBuKpiCategoryEntity.getRangeValue());
		response.setColorRange(millBuKpiCategoryEntity.getColorRange());
		return response;
	}

	@SuppressWarnings("static-access")
	public static Date getCurrentYearDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(cal.MONTH, cal.JANUARY);
		cal.set(cal.DATE, 1);
		return new java.sql.Date(cal.getTime().getTime());
	}

	public static ProcessLineAnnualResponse prePareResponse(MillBuKpiCategoryEntity millBuKpiCategoryEntity,
			List<DailyKpiPulpEntity> dailyKpiPulpEntities) {
		ProcessLineAnnualResponse response = new ProcessLineAnnualResponse();
		Double allProcessSum = 0.0D;
		if (!dailyKpiPulpEntities.isEmpty()) {
			for (DailyKpiPulpEntity entity : dailyKpiPulpEntities) {
				if (Double.isNaN(entity.getProcessLine4()) && Double.isNaN(entity.getProcessLine5())) {
					response.setTotalYTDProduction(Double.NaN);
				} else {
					handleNaNCondition(entity);
					Double total = 0.0;
					Double sumDouble = Double.sum(entity.getProcessLine4() + entity.getProcessLine5(), total);
					allProcessSum += sumDouble;
					response.setTotalYTDProduction(allProcessSum);
				}
			}
		}
		return populateAnnulTaget(millBuKpiCategoryEntity, response);
	}

	public static List<ProcessLineSeries> prePareDailyTargetResponse(List<DailyKpiPulpEntity> dailyKpiPulpEntities) {
		List<ProcessLineSeries> seriesList = new ArrayList<>();
		ProcessLineSeries series = null;
		Double processSum = 0.0D;
		for (DailyKpiPulpEntity entity : dailyKpiPulpEntities) {
			handleNaNCondition(entity);
			series = new ProcessLineSeries();
			Date value = new Date(entity.getDatetime().getTime());
			series.setName(Utility.dateToStringConvertor(value, DashboardConstant.EXTENDED_DATE_FORMAT));
			Double total = 0.0;
			Double sumOfProcessLine = Double.sum(entity.getProcessLine4() + entity.getProcessLine5(), total);
			processSum += sumOfProcessLine;
			series.setValue(processSum.longValue());
			seriesList.add(series);
		}
		return seriesList;
	}

	public static Long calculateActualTarget(List<DailyKpiPulpEntity> dailyKpiPulpEntities) {
		Double processSum = 0.0D;
		for (DailyKpiPulpEntity entity : dailyKpiPulpEntities) {
			handleNaNCondition(entity);
			Double total = 0.0;
			Double sumOfProcessLine = Double
					.sum(entity.getProcessLine1() + entity.getProcessLine2() + entity.getProcessLine3(), total);
			processSum += sumOfProcessLine;
		}
		return processSum.longValue();
	}

	private static ProcessLineAnnualResponse populateAnnulTaget(MillBuKpiCategoryEntity millBuKpiCategoryEntity,
			ProcessLineAnnualResponse response) {
		response.setAnnualTarget(millBuKpiCategoryEntity.getAnnualTarget());
		return response;
	}

	// Shubham and Dixit code
	public static List<DailyKpiPulp> convertToDailyKpiPulpDTO(List<DailyKpiPulpEntity> entities) {
		List<DailyKpiPulp> dailyKpiPulp = new ArrayList<>();
		for (DailyKpiPulpEntity entity : entities) {
			DailyKpiPulp dailyKpiPulpObject = new DailyKpiPulp();
			dailyKpiPulpObject.setDailyKpiPulpId(entity.getDailyKpiPulpId());
			dailyKpiPulpObject.setProcessLine1(entity.getProcessLine1());
			dailyKpiPulpObject.setProcessLine2(entity.getProcessLine2());
			dailyKpiPulpObject.setProcessLine3(entity.getProcessLine3());
			dailyKpiPulpObject.setProcessLine4(entity.getProcessLine4());
			dailyKpiPulpObject.setProcessLine5(entity.getProcessLine5());
			dailyKpiPulpObject.setProcessLine6(entity.getProcessLine6());
			dailyKpiPulpObject.setProcessLine7(entity.getProcessLine7());
			dailyKpiPulpObject.setProcessLine8(entity.getProcessLine8());
			dailyKpiPulpObject.setProcessLine9(entity.getProcessLine9());
			dailyKpiPulpObject.setProcessLine10(entity.getProcessLine10());
			dailyKpiPulpObject.setProcessLine11(entity.getProcessLine11());
			dailyKpiPulpObject.setProcessLine12(entity.getProcessLine12());
			dailyKpiPulpObject.setProcessLine13(entity.getProcessLine13());
			dailyKpiPulpObject.setProcessLine14(entity.getProcessLine14());
			dailyKpiPulpObject.setProcessLine15(entity.getProcessLine15());
			dailyKpiPulp.add(dailyKpiPulpObject);
		}
		return dailyKpiPulp;
	}

	// Shubham and Dixit code
	public static List<DailyKpiPulpResponse> createResponseObject(List<ProcessLine> processLine,
			List<DailyKpiPulp> dailyKpiPulp) {
		List<DailyKpiPulpResponse> response = new ArrayList<>();
		for (ProcessLine processLineObj : processLine) {
			DailyKpiPulpResponse dailyKpiPulpResponse = new DailyKpiPulpResponse();
			dailyKpiPulpResponse.setName(processLineObj.getProcessLineCode());
			dailyKpiPulpResponse.setMin(processLineObj.getMinTarget().longValue());
			dailyKpiPulpResponse.setMax(processLineObj.getMaxTarget().longValue());
			dailyKpiPulpResponse.setRange(processLineObj.getRangeValue());
			dailyKpiPulpResponse.setColorRange(processLineObj.getColorRange());
			response.add(dailyKpiPulpResponse);
		}
		populateProcessLinesValues(dailyKpiPulp, response);
		return response;
	}

	// Shubham and Dixit code
	private static void populateProcessLinesValues(List<DailyKpiPulp> dailyKpiPulp,
			List<DailyKpiPulpResponse> response) {
		if (!dailyKpiPulp.isEmpty()) {

			try {
				response.get(0).setValue(BigDecimal.valueOf(dailyKpiPulp.get(0).getProcessLine4())
						.setScale(0, RoundingMode.CEILING).doubleValue());
				response.get(1).setValue(BigDecimal.valueOf(dailyKpiPulp.get(0).getProcessLine5())
						.setScale(0, RoundingMode.CEILING).doubleValue());
				response.get(2).setValue(BigDecimal.valueOf(dailyKpiPulp.get(0).getProcessLine1())
						.setScale(0, RoundingMode.CEILING).doubleValue());
				response.get(3).setValue(BigDecimal.valueOf(dailyKpiPulp.get(0).getProcessLine2())
						.setScale(0, RoundingMode.CEILING).doubleValue());
				response.get(4).setValue(BigDecimal.valueOf(dailyKpiPulp.get(0).getProcessLine3())
						.setScale(0, RoundingMode.CEILING).doubleValue());
			} catch (IndexOutOfBoundsException ex) {
				logger.error("Index out of bound excep while populating process lines value", ex, response);
			}
		} else {
			response.get(0).setValue(Double.valueOf(DashboardConstant.NAN));
			response.get(1).setValue(Double.valueOf(DashboardConstant.NAN));
			response.get(2).setValue(Double.valueOf(DashboardConstant.NAN));
			response.get(3).setValue(Double.valueOf(DashboardConstant.NAN));
			response.get(4).setValue(Double.valueOf(DashboardConstant.NAN));
		}
	}

	public static List<DateRangeResponse> createDailyKpiPulpResponseForBarChart(
			List<DailyKpiPulpEntity> dailyKpiPulpEntities) {
		List<DateRangeResponse> resultList = new ArrayList<>();
		List<String> lineList = getRequestedProcessLines();
		lineList.forEach(item -> resultList.add(new DateRangeResponse(item, new ArrayList<>())));

		List<SeriesObject> seriesObjects = resultList.get(0).getSeries();
		List<SeriesObject> seriesObjects2 = resultList.get(1).getSeries();

		dailyKpiPulpEntities.forEach(item -> {
			Map<Object, Object> tmp = new HashMap<>();
			tmp.put(DashboardConstant.NAME,
					Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT));
			tmp.put(DashboardConstant.VALUE, Utility.parseProcessLineValue(item.getProcessLine4()));

			seriesObjects.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(BigDecimal.valueOf(item.getProcessLine4())
									.setScale(0, RoundingMode.CEILING).doubleValue())));
			seriesObjects2.add(
					new SeriesObject(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT),
							Utility.parseProcessLineValue(BigDecimal.valueOf(item.getProcessLine5())
									.setScale(0, RoundingMode.CEILING).doubleValue())));
		});
		return resultList;
	}

	public static List<DateRangeResponse> createDailyKpiPulpResponseForAreaChart(
			List<DailyKpiPulpEntity> dailyKpiPulpEntities) {
		List<DateRangeResponse> resultList = new ArrayList<>();
		List<String> lineList = getRequestedProcessLines();

		dailyKpiPulpEntities.forEach(item -> {
			DateRangeResponse dateRangeResponse = new DateRangeResponse();

			ArrayList<SeriesObject> seriesList = new ArrayList<>();
			Collections.addAll(seriesList,
					new SeriesObject(lineList.get(0),
							Utility.parseProcessLineValue(BigDecimal.valueOf(item.getProcessLine4())
									.setScale(0, RoundingMode.CEILING).doubleValue())),
					new SeriesObject(lineList.get(1), Utility.parseProcessLineValue(BigDecimal
							.valueOf(item.getProcessLine5()).setScale(0, RoundingMode.CEILING).doubleValue())));

			dateRangeResponse.setName(Utility.dateToStringConvertor(item.getDatetime(), DashboardConstant.DATE_FORMAT));
			dateRangeResponse.setSeries(seriesList);

			resultList.add(dateRangeResponse);
		});
		return resultList;
	}
}
