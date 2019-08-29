package com.rgei.kpi.dashboard.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.constant.Quarter;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

public class BenchmarkingUtilityRZ {
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(BenchmarkingUtilityRZ.class);
	
	private BenchmarkingUtilityRZ() {
		
	}
	
	public static List<SeriesObject> getRZDailyResponse(Object[] obj, String kpiProcessLine, SeriesObject val, List<SeriesObject> series) {
		logger.info("Get RZ daily response for process line ", kpiProcessLine);
		double value;
		switch (kpiProcessLine) {
		case DashboardConstant.PROCESS_LINE_PD1:
			val.setName(kpiProcessLine);
			value = parseProcessLineValue(obj[1].toString());
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			val.setName(kpiProcessLine);
			value = parseProcessLineValue(obj[2].toString());
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			val.setName(kpiProcessLine);
			value = parseProcessLineValue(obj[3].toString());
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PL11:
			val.setName(kpiProcessLine);
			value = parseProcessLineValue(obj[4].toString());
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PL12:
			val.setName(kpiProcessLine);
			value = parseProcessLineValue(obj[5].toString());
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.RZ_AVG:
			val.setName(kpiProcessLine);
			value = parseProcessLineValue(obj[9].toString());
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		default:
		}
		series.add(val);
		return series;
	}
	
	public static List<DateRangeResponse> fetchBenchmarkingMonthlyData(List<Object[]> responseEntity, Map<String, List<String>> finalBenchmarkingProcessLines) {
		logger.info("Fetch RZ benchmarking monthly data for process lines ", finalBenchmarkingProcessLines);
		List<DateRangeResponse> rzData = new ArrayList<>();
		for(Object[] obj:responseEntity) {
			DateRangeResponse val = new DateRangeResponse();
			List<SeriesObject> series = new ArrayList<>();
			val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[10]).split("\\.")[0]);
			for(String kpiProcessLine: finalBenchmarkingProcessLines.get(DashboardConstant.RZ)) {
				SeriesObject value = new SeriesObject();
				val.setSeries(getRZSeriesResponse(obj, kpiProcessLine, value, series));
			}
			rzData.add(val);
		}
		return rzData;
	}
	
	public static List<DateRangeResponse> fetchBenchmarkingQuarterlyData(List<Object[]> responseEntity, Map<String, List<String>> finalBenchmarkingProcessLines) {
		logger.info("Fetch RZ benchmarking quarterly data for process lines ", finalBenchmarkingProcessLines);
		List<DateRangeResponse> rzData = new ArrayList<>();
		for(Object[] obj:responseEntity) {
			DateRangeResponse val = new DateRangeResponse();
			List<SeriesObject> series = new ArrayList<>();
			if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
				getQuarter1Data(finalBenchmarkingProcessLines, rzData, obj, val, series);
			} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
				getQuarter2Data(finalBenchmarkingProcessLines, rzData, obj, val, series);
			} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
				getQuarter3Data(finalBenchmarkingProcessLines, rzData, obj, val, series);
			} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
				getQuarter4Data(finalBenchmarkingProcessLines, rzData, obj, val, series);
			}
		}
		return rzData;
	}

	private static void getQuarter4Data(Map<String, List<String>> finalBenchmarkingProcessLines,
			List<DateRangeResponse> rzData, Object[] obj, DateRangeResponse val, List<SeriesObject> series) {
		val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[10]).split("\\.")[0]);
		for(String kpiProcessLine: finalBenchmarkingProcessLines.get(DashboardConstant.RZ)) {
			SeriesObject value = new SeriesObject();
			val.setSeries(getRZSeriesResponse(obj, kpiProcessLine, value, series));
			}
		rzData.add(val);
	}

	private static void getQuarter3Data(Map<String, List<String>> finalBenchmarkingProcessLines,
			List<DateRangeResponse> rzData, Object[] obj, DateRangeResponse val, List<SeriesObject> series) {
		val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[10]).split("\\.")[0]);
		for(String kpiProcessLine: finalBenchmarkingProcessLines.get(DashboardConstant.RZ)) {
			SeriesObject value = new SeriesObject();
			val.setSeries(getRZSeriesResponse(obj, kpiProcessLine, value, series));
			}
		rzData.add(val);
	}

	private static void getQuarter2Data(Map<String, List<String>> finalBenchmarkingProcessLines,
			List<DateRangeResponse> rzData, Object[] obj, DateRangeResponse val, List<SeriesObject> series) {
		val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[10]).split("\\.")[0]);
		for(String kpiProcessLine: finalBenchmarkingProcessLines.get(DashboardConstant.RZ)) {
			SeriesObject value = new SeriesObject();
			val.setSeries(getRZSeriesResponse(obj, kpiProcessLine, value, series));
			}
		rzData.add(val);
	}

	private static void getQuarter1Data(Map<String, List<String>> finalBenchmarkingProcessLines,
			List<DateRangeResponse> rzData, Object[] obj, DateRangeResponse val, List<SeriesObject> series) {
		val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[10]).split("\\.")[0]);
		for(String kpiProcessLine: finalBenchmarkingProcessLines.get(DashboardConstant.RZ)) {
			SeriesObject value = new SeriesObject();
			val.setSeries(getRZSeriesResponse(obj, kpiProcessLine, value, series));
			}
		rzData.add(val);
	}
	
	public static List<DateRangeResponse> fetchBenchmarkingDailyData(List<Object[]> responseEntity, Map<String, List<String>> finalBenchmarkingProcessLines) {
		logger.info("Fetch RZ benchmarking daily data for process lines ", finalBenchmarkingProcessLines);
		List<DateRangeResponse> rzData = new ArrayList<>();
		for(Object[] obj:responseEntity) {
			DateRangeResponse val = new DateRangeResponse();
			List<SeriesObject> series = new ArrayList<>();
			val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.DATE_FORMAT));
			for(String kpiProcessLine: finalBenchmarkingProcessLines.get(DashboardConstant.RZ)) {
				SeriesObject value = new SeriesObject();
				val.setSeries(getRZDailyResponse(obj, kpiProcessLine, value, series));
			}
			rzData.add(val);
		}
		return rzData;
	}
	
	public static List<DateRangeResponse> fetchBenchmarkingYearlyData(List<Object[]> responseEntity, Map<String, List<String>> finalBenchmarkingProcessLines) {
		logger.info("Fetch RZ benchmarking yearly data for process lines ", finalBenchmarkingProcessLines);
		List<DateRangeResponse> rzData = new ArrayList<>();
		for(Object[] obj:responseEntity) {
			DateRangeResponse val = new DateRangeResponse();
			List<SeriesObject> series = new ArrayList<>();
			val.setName(String.valueOf(obj[0]).split("\\.")[0]);
			for(String kpiProcessLine: finalBenchmarkingProcessLines.get(DashboardConstant.RZ)) {
				SeriesObject value = new SeriesObject();
				val.setSeries(getRZSeriesResponse(obj, kpiProcessLine, value, series));
			}
			rzData.add(val);
		}
		return rzData;
	}
	
	public static List<SeriesObject> getRZSeriesResponse(Object[] obj, String kpiProcessLine, SeriesObject val, List<SeriesObject> series) {
		logger.info("Get RZ series response for process line ", kpiProcessLine);
		double value;
		switch (kpiProcessLine) {
		case DashboardConstant.PROCESS_LINE_PD1:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[1]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[2]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[3]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PL11:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[4]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PL12:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[5]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.RZ_AVG:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[9]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		default:
		}
		series.add(val);
		return series;
	}
	
	public static double parseProcessLineValue(String value) {
		return !value.equalsIgnoreCase(DashboardConstant.NAN)?Double.valueOf(value):0;
	}
	
	public static double parseProcessLineNullValue(Object value) {
		return Objects.nonNull(value)?Double.valueOf(value.toString()):0;
	}
}
