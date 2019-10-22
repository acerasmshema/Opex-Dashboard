package com.rgei.kpi.dashboard.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.constant.Quarter;
import com.rgei.kpi.dashboard.entities.KpiEntity;
import com.rgei.kpi.dashboard.entities.KpiProcessLineEntity;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.Kpi;
import com.rgei.kpi.dashboard.response.model.KpiCategoryResponse;
import com.rgei.kpi.dashboard.response.model.KpiCategorySeriesResponse;
import com.rgei.kpi.dashboard.response.model.KpiType;
import com.rgei.kpi.dashboard.response.model.ProcessLine;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

/**
 * @author dixit.sharma
 *
 */

public class KpiDashboardCategoryUtilityRZ {

	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiDashboardCategoryUtilityRZ.class);

	private KpiDashboardCategoryUtilityRZ() {
	}

	public static Kpi convertToKpiDTO(KpiEntity entity, Integer millId) {
		Kpi kpiObject = new Kpi();
		if (Boolean.TRUE.equals(entity.getActive())) {
			List<String> kpiProcessLines = new ArrayList<>();
			kpiObject.setKpiId(entity.getKpiId());
			kpiObject.setKpiName(entity.getKpiName());
			kpiObject.setKpiTypeId(entity.getKpiType().getKpiTypeId());
			kpiObject.setKpiUnit(entity.getKpiUnit());
			for (KpiProcessLineEntity value : entity.getKpiProcessLines()) {
				if (Boolean.TRUE.equals(value.getActive()) && millId.equals(value.getMill().getMillId())) {
					kpiProcessLines.add(value.getProcessLine().getProcessLineCode());
					Collections.sort(kpiProcessLines);
				}
			}
			kpiObject.setKpiProcessLines(kpiProcessLines);
		}
		return kpiObject;
	}

	public static List<String> fetchProcessLines(Kpi kpi, List<String> lineList) {
		logger.info("Fetch process lines of RZ for Kpi ", kpi.getKpiName());
		List<String> finalKpiProcessLines;
		if (lineList.isEmpty()) {
			finalKpiProcessLines = kpi.getKpiProcessLines();
		} else {
			List<String> kpiProcessLines = kpi.getKpiProcessLines();
			finalKpiProcessLines = new ArrayList<>();
			for (String processLine : lineList) {
				if (kpiProcessLines.contains(processLine)) {
					finalKpiProcessLines.add(processLine);
				}
			}
		}
		return finalKpiProcessLines;
	}

	public static void fetchConsumptionGridResponse(List<KpiCategoryResponse> resultList, List<KpiType> kpiType,
			List<Object[]> responseEntity) {
		logger.info("Fetch consumption grid response RZ");
		int i = 0;
		if (responseEntity.isEmpty()) {
			for (; i < kpiType.size();) {
				i = fetchDefaultTableResponse(resultList, kpiType, i);
			}

		} else {
			for (Object[] obj : responseEntity) {
				KpiCategoryResponse val = new KpiCategoryResponse();
				List<KpiCategorySeriesResponse> series = new ArrayList<>();
				val.setKpiId(kpiType.get(i).getKpi().getKpiId());
				val.setKpiName(kpiType.get(i).getKpi().getKpiName());
				val.setUnit(kpiType.get(i).getKpi().getKpiUnit());
				for (ProcessLine kpiProcessLine : kpiType.get(i).getProcessLines()) {
					KpiCategorySeriesResponse value = new KpiCategorySeriesResponse();
					val.setSeries(KpiDashboardCategoryUtilityRZ.getSeriesObject(obj, kpiProcessLine, value, series,
							kpiType.get(i)));
				}
				i++;
				resultList.add(val);
			}
		}
	}

	private static int fetchDefaultTableResponse(List<KpiCategoryResponse> resultList, List<KpiType> kpiType, int i) {
		logger.info("Fetch default table response RZ");
		KpiCategoryResponse val = new KpiCategoryResponse();
		List<KpiCategorySeriesResponse> series = new ArrayList<>();
		val.setKpiId(kpiType.get(i).getKpi().getKpiId());
		val.setKpiName(kpiType.get(i).getKpi().getKpiName());
		val.setUnit(kpiType.get(i).getKpi().getKpiUnit());
		for (ProcessLine kpiProcessLine : kpiType.get(i).getProcessLines()) {
			KpiCategorySeriesResponse value = new KpiCategorySeriesResponse();
			val.setSeries(KpiDashboardCategoryUtilityRZ.getDefaultSeriesObject(kpiProcessLine, value, series,
					kpiType.get(i)));
		}
		i++;
		resultList.add(val);
		return i;
	}

	public static void populateQuarterlyData(List<DateRangeResponse> resultList, List<String> finalKpiProcessLines,
			Object[] obj) {
		logger.info("Populate quarterly data RZ");
		DateRangeResponse val = new DateRangeResponse();
		List<SeriesObject> series = new ArrayList<>();
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
			for (String kpiProcessLine : finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(getSeriesResponse(obj, kpiProcessLine, value, series));
			}
			resultList.add(val);
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
			for (String kpiProcessLine : finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(getSeriesResponse(obj, kpiProcessLine, value, series));
			}
			resultList.add(val);
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
			for (String kpiProcessLine : finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(getSeriesResponse(obj, kpiProcessLine, value, series));
			}
			resultList.add(val);
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString() + "/" + String.valueOf(obj[9]).split("\\.")[0]);
			for (String kpiProcessLine : finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(getSeriesResponse(obj, kpiProcessLine, value, series));
			}
			resultList.add(val);
		}
	}

	public static List<SeriesObject> getDailySeriesResponse(Object[] obj, String kpiProcessLine, SeriesObject val,
			List<SeriesObject> series) {
		logger.info("Get daily series response RZ");
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
		default:
		}
		series.add(val);
		return series;
	}

	public static List<SeriesObject> getSeriesResponse(Object[] obj, String kpiProcessLine, SeriesObject val,
			List<SeriesObject> series) {
		logger.info("Get series response RZ");
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
		default:
		}
		series.add(val);
		return series;
	}

	public static double parseProcessLineValue(String value) {
		return !value.equalsIgnoreCase(DashboardConstant.NAN) ? Double.valueOf(value) : -1;
	}

	public static double parseProcessLineNullValue(Object value) {
		return Objects.nonNull(value) ? Double.valueOf(value.toString()) : -1;
	}

	private static void sortResponse(List<KpiType> kpiType) {
		Collections.sort(kpiType, (o1, o2) -> {
			int value1 = o1.getKpi().getKpiId().compareTo(o2.getKpi().getKpiId());
			if (value1 == 0) {
				return o1.getKpi().getKpiId().compareTo(o2.getKpi().getKpiId());
			}
			return value1;
		});
	}

	public static Date getYesterdayDate() {
		LocalDate yesterdayDate = LocalDate.now().minusDays(1);
		return Date.valueOf(yesterdayDate.toString());
	}

	public static List<KpiCategorySeriesResponse> getSeriesObject(Object[] obj, ProcessLine kpiProcessLine,
			KpiCategorySeriesResponse val, List<KpiCategorySeriesResponse> series, KpiType type) {
		logger.info("Get series object of RZ for process line ", kpiProcessLine);
		String value = null;
		val.setProcessLineId(kpiProcessLine.getProcessLineId());
		val.setName(kpiProcessLine.getProcessLineCode());
		val.setTarget(type.getTarget().get(kpiProcessLine.getProcessLineCode()));
		switch (kpiProcessLine.getProcessLineCode()) {
		case DashboardConstant.PROCESS_LINE_PD1:
			value = parseProcessLineValue(Double.valueOf(obj[0].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine.getProcessLineCode())));
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			value = parseProcessLineValue(Double.valueOf(obj[1].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine.getProcessLineCode())));
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			value = parseProcessLineValue(Double.valueOf(obj[2].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine.getProcessLineCode())));
			break;
		case DashboardConstant.PROCESS_LINE_PL11:
			value = parseProcessLineValue(Double.valueOf(obj[3].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine.getProcessLineCode())));
			break;
		case DashboardConstant.PROCESS_LINE_PL12:
			value = parseProcessLineValue(Double.valueOf(obj[4].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine.getProcessLineCode())));
			break;
		default:
		}
		series.add(val);
		return series;
	}

	private static String fetchColor(String value, String threshold) {
		String color = null;
		if (DashboardConstant.NA.equalsIgnoreCase(value) || DashboardConstant.NAN.equalsIgnoreCase(value)
				|| Objects.isNull(threshold)) {
			color = DashboardConstant.BLACK;
		} else {
			String[] target = threshold.split(",");
			String[] targetColor = target[0].split(":");
			String colorThreshold = targetColor[1];
			Double colorValue = Double.parseDouble(colorThreshold);
			Double val = Double.parseDouble(value);
			if (val > colorValue) {
				color = DashboardConstant.RED;
			} else {
				color = DashboardConstant.BLACK;
			}
		}

		return color;
	}

	public static List<KpiCategorySeriesResponse> getDefaultSeriesObject(ProcessLine kpiProcessLine,
			KpiCategorySeriesResponse val, List<KpiCategorySeriesResponse> series, KpiType type) {
		val.setProcessLineId(kpiProcessLine.getProcessLineId());
		val.setName(kpiProcessLine.getProcessLineCode());
		val.setValue(DashboardConstant.NA);
		val.setTarget(type.getTarget().get(kpiProcessLine.getProcessLineCode()));
		val.setColor("black");
		series.add(val);
		return series;
	}

	public static String parseProcessLineValue(Double value) {
		return value.isNaN() ? DashboardConstant.NA
				: BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).toString();
	}

	public static String DatetimeToQuarterConverter(Object[] obj) {
		String quarter = null;
		if (Quarter.Q1.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
			quarter = Quarter.Q1.toString() + "/" + String.valueOf(obj[1]).split("\\.")[0];
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
			quarter = Quarter.Q2.toString() + "/" + String.valueOf(obj[1]).split("\\.")[0];
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
			quarter = Quarter.Q3.toString() + "/" + String.valueOf(obj[1]).split("\\.")[0];
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
			quarter = Quarter.Q4.toString() + "/" + String.valueOf(obj[1]).split("\\.")[0];
		}
		return quarter;
	}
}
