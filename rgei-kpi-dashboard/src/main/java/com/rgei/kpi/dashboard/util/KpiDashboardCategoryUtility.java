package com.rgei.kpi.dashboard.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.constant.Quarter;
import com.rgei.kpi.dashboard.entities.KpiEntity;
import com.rgei.kpi.dashboard.entities.KpiProcessLineEntity;
import com.rgei.kpi.dashboard.entities.KpiTypeEntity;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.Kpi;
import com.rgei.kpi.dashboard.response.model.KpiCategoryResponse;
import com.rgei.kpi.dashboard.response.model.KpiCategorySeriesResponse;
import com.rgei.kpi.dashboard.response.model.KpiType;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

/**
 * @author dixit.sharma
 *
 */

public class KpiDashboardCategoryUtility {
	
	private KpiDashboardCategoryUtility() {
	}

	public static Kpi convertToKpiDTO(KpiEntity entity) {
		Kpi kpiObject = new Kpi();
		if(Boolean.TRUE.equals(entity.getActive())) {
		List<String> kpiProcessLines = new ArrayList<>();
		kpiObject.setKpiId(entity.getKpiId());
		kpiObject.setKpiName(entity.getKpiName());
		kpiObject.setKpiTypeId(entity.getKpiType().getKpiTypeId());
		kpiObject.setKpiUnit(entity.getKpiUnit());
		for (KpiProcessLineEntity value : entity.getKpiProcessLines()) {
			if(Boolean.TRUE.equals(value.getActive())) {
			kpiProcessLines.add(value.getProcessLine().getProcessLineCode());
			}
		}
		kpiObject.setKpiProcessLines(kpiProcessLines);
		}
		return kpiObject;
	}
	
	public static List<String> fetchProcessLines(Kpi kpi, List<String> lineList) {
		List<String> finalKpiProcessLines;
		if (lineList.isEmpty()) {
			finalKpiProcessLines = kpi.getKpiProcessLines();
		}else {
			List<String> kpiProcessLines = kpi.getKpiProcessLines();
			finalKpiProcessLines = new ArrayList<>();
			for(String processLine: lineList) {
				if(kpiProcessLines.contains(processLine)) {
					finalKpiProcessLines.add(processLine);
				}
			}
		}
		return finalKpiProcessLines;
	}

	public static void fetchConsumptionGridResponse(List<KpiCategoryResponse> resultList, List<KpiType> kpiType,
			List<Object[]> responseEntity) {
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
					for (String kpiProcessLine : kpiType.get(i).getProcessLines()) {
						KpiCategorySeriesResponse value = new KpiCategorySeriesResponse();
						val.setSeries(KpiDashboardCategoryUtility.getSeriesObject(obj, kpiProcessLine, value, series,
								kpiType.get(i)));
					}
					i++;
				resultList.add(val);
			}
		}
	}

	private static int fetchDefaultTableResponse(List<KpiCategoryResponse> resultList, List<KpiType> kpiType, int i) {
		KpiCategoryResponse val = new KpiCategoryResponse();
		List<KpiCategorySeriesResponse> series = new ArrayList<>();
		val.setKpiId(kpiType.get(i).getKpi().getKpiId());
		val.setKpiName(kpiType.get(i).getKpi().getKpiName());
		val.setUnit(kpiType.get(i).getKpi().getKpiUnit());
		for (String kpiProcessLine : kpiType.get(i).getProcessLines()) {
			KpiCategorySeriesResponse value = new KpiCategorySeriesResponse();
			val.setSeries(KpiDashboardCategoryUtility.getDefaultSeriesObject(kpiProcessLine, value, series,
					kpiType.get(i)));
		}
		i++;
		resultList.add(val);
		return i;
	}
	
	public static void populateQuarterlyData(List<DateRangeResponse> resultList, List<String> finalKpiProcessLines,
			Object[] obj) {
		DateRangeResponse val = new DateRangeResponse();
		List<SeriesObject> series = new ArrayList<>();
		if (Quarter.Q1.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q1.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			for(String kpiProcessLine: finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(KpiDashboardCategoryUtility.getQuarterlySeriesResponse(obj, kpiProcessLine, value, series));
				}
			resultList.add(val);
		} else if (Quarter.Q2.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q2.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			for(String kpiProcessLine: finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(KpiDashboardCategoryUtility.getQuarterlySeriesResponse(obj, kpiProcessLine, value, series));
				}
			resultList.add(val);
		} else if (Quarter.Q3.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q3.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			for(String kpiProcessLine: finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(KpiDashboardCategoryUtility.getQuarterlySeriesResponse(obj, kpiProcessLine, value, series));
				}
			resultList.add(val);
		} else if (Quarter.Q4.getValue().equalsIgnoreCase(obj[0].toString())) {
			val.setName(Quarter.Q4.toString()+"/"+ String.valueOf(obj[9]).split("\\.")[0]);
			for(String kpiProcessLine: finalKpiProcessLines) {
				SeriesObject value = new SeriesObject();
				val.setSeries(KpiDashboardCategoryUtility.getQuarterlySeriesResponse(obj, kpiProcessLine, value, series));
				}
			resultList.add(val);
		}
	}
	
	public static List<SeriesObject> getDailySeriesResponse(Object[] obj, String kpiProcessLine, SeriesObject val, List<SeriesObject> series) {
	double value;
	switch (kpiProcessLine) {
	case DashboardConstant.PROCESS_LINE_FL1:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[1].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	case DashboardConstant.PROCESS_LINE_FL2:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[2].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	case DashboardConstant.PROCESS_LINE_FL3:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[3].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	case DashboardConstant.PROCESS_LINE_PCD:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[4].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	case DashboardConstant.PROCESS_LINE_PD1:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[5].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	case DashboardConstant.PROCESS_LINE_PD2:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[6].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	case DashboardConstant.PROCESS_LINE_PD3:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[7].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	case DashboardConstant.PROCESS_LINE_PD4:
		val.setName(kpiProcessLine);
		value = parseProcessLineValue(obj[8].toString());
		val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
		break;
	default:
	}
	series.add(val);
	return series;
}

	public static List<SeriesObject> getMonthlySeriesResponse(Object[] obj, String kpiProcessLine, SeriesObject val, List<SeriesObject> series) {
		double value;
		switch (kpiProcessLine) {
		case DashboardConstant.PROCESS_LINE_FL1:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[1]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_FL2:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[2]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_FL3:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[3]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PCD:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[4]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD1:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[5]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[6]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[7]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD4:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[8]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		default:
		}
		series.add(val);
		return series;
	}

	public static List<SeriesObject> getQuarterlySeriesResponse(Object[] obj, String kpiProcessLine,
			SeriesObject val, List<SeriesObject> series) {
		double value;
		switch (kpiProcessLine) {
		case DashboardConstant.PROCESS_LINE_FL1:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[1]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_FL2:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[2]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_FL3:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[3]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PCD:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[4]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD1:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[5]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[6]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[7]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		case DashboardConstant.PROCESS_LINE_PD4:
			val.setName(kpiProcessLine);
			value = parseProcessLineNullValue(obj[8]);
			val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
			break;
		default:
		}
		series.add(val);
		return series;
	}
	
	public static List<SeriesObject> getYearlySeriesResponse(Object[] obj, String kpiProcessLine, SeriesObject val, List<SeriesObject> series) {
			double value;	
			switch (kpiProcessLine) {
			case DashboardConstant.PROCESS_LINE_FL1:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[1]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL2:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[2]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_FL3:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[3]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PCD:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[4]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD1:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[5]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD2:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[6]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD3:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[7]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());
				break;
			case DashboardConstant.PROCESS_LINE_PD4:
				val.setName(kpiProcessLine);
				value = parseProcessLineNullValue(obj[8]);
				val.setValue(BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).doubleValue());					
				break;
			default:
			}
			series.add(val);
	return series;
}
	
	public static double parseProcessLineValue(String value) {
		return !value.equalsIgnoreCase(DashboardConstant.NAN)?Double.valueOf(value):-1;
	}
	
	public static double parseProcessLineNullValue(Object value) {
		return Objects.nonNull(value)?Double.valueOf(value.toString()):-1;
	}
	
	public static List<KpiType> convertToKpiTypeDTO(List<KpiTypeEntity> entities, Integer millId) {
		List<KpiType> kpiType = new ArrayList<>();
		for (KpiTypeEntity value : entities) {
			if (Boolean.TRUE.equals(value.getActive())) {
				for (KpiEntity kpi : value.getKpis()) {
					populateKPITypeDTO(kpiType, value, kpi, millId);
				}
				sortResponse(kpiType);
			}
		}
		return kpiType;
	}

	private static void populateKPITypeDTO(List<KpiType> kpiType, KpiTypeEntity value, KpiEntity kpi, Integer millId) {
		KpiType kpiTypeObject;
		if (Boolean.TRUE.equals(kpi.getActive())) {
			kpiTypeObject = new KpiType();
			List<String> processLines =new ArrayList<>();
			Map<String,String> target = new HashMap<>();
			if (Boolean.TRUE.equals(value.getActive())) {
				kpiTypeObject.setKpiTypeId(value.getKpiTypeId());
				kpiTypeObject.setKpiTypeCode(value.getKpiTypeCode());
				Kpi obj = new Kpi();
				obj.setKpiId(kpi.getKpiId());
				obj.setKpiName(kpi.getKpiName());
				obj.setKpiUnit(kpi.getKpiUnit());
				kpiTypeObject.setKpi(obj);
				for (KpiProcessLineEntity line : kpi.getKpiProcessLines()) {
					if(millId.equals(line.getMill().getMillId())) {
					target.put(line.getProcessLine().getProcessLineCode(), line.getTarget());
					processLines.add(line.getProcessLine().getProcessLineCode());
					Collections.sort(processLines);
					}
				}
			}
			kpiTypeObject.setProcessLines(processLines);
			kpiTypeObject.setTarget(target);
			kpiType.add(kpiTypeObject);
		}
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
		LocalDate yesterdayDate= LocalDate.now().minusDays(1);
		return Date.valueOf(yesterdayDate.toString());
	}
	
	public static List<KpiCategorySeriesResponse> getSeriesObject(Object[] obj, String kpiProcessLine, KpiCategorySeriesResponse val, List<KpiCategorySeriesResponse> series, KpiType type) {
		String value = null;
		val.setName(kpiProcessLine);
		val.setTarget(type.getTarget().get(kpiProcessLine));
		switch (kpiProcessLine) {
		case DashboardConstant.PROCESS_LINE_FL1:
			value = parseProcessLineValue(Double.valueOf(obj[0].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		case DashboardConstant.PROCESS_LINE_FL2:
			value = parseProcessLineValue(Double.valueOf(obj[1].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		case DashboardConstant.PROCESS_LINE_FL3:
			value = parseProcessLineValue(Double.valueOf(obj[2].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		case DashboardConstant.PROCESS_LINE_PCD:
			value = parseProcessLineValue(Double.valueOf(obj[3].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		case DashboardConstant.PROCESS_LINE_PD1:
			value = parseProcessLineValue(Double.valueOf(obj[4].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		case DashboardConstant.PROCESS_LINE_PD2:
			value = parseProcessLineValue(Double.valueOf(obj[5].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		case DashboardConstant.PROCESS_LINE_PD3:
			value = parseProcessLineValue(Double.valueOf(obj[6].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		case DashboardConstant.PROCESS_LINE_PD4:
			value = parseProcessLineValue(Double.valueOf(obj[7].toString()));
			val.setValue(value);
			val.setColor(fetchColor(value, type.getTarget().get(kpiProcessLine)));
			break;
		default:
		}
		series.add(val);
		return series;
	}
	
	private static String fetchColor(String value, String threshold) {
		String color = null;
		String[] target = threshold.split(","); 
		String[] targetColor = target[0].split(":");
		String colorThreshold = targetColor[1];
		Double colorValue = Double.parseDouble(colorThreshold);
		Double val = Double.parseDouble(value);
		if(val > colorValue) {
			color = "red";
		}
		else {
			color = "black";
		}
		return color;
	}
	
	public static List<KpiCategorySeriesResponse> getDefaultSeriesObject(String kpiProcessLine, KpiCategorySeriesResponse val, List<KpiCategorySeriesResponse> series, KpiType type) {
		val.setName(kpiProcessLine);
		val.setValue(DashboardConstant.NA);
		val.setTarget(type.getTarget().get(kpiProcessLine));
		val.setColor("black");
		series.add(val);
		return series;
	}
	
public static String parseProcessLineValue(Double value) {
		return value.isNaN()?DashboardConstant.NA:BigDecimal.valueOf(value).setScale(2, RoundingMode.CEILING).toString();
	}

public static String DatetimeToQuarterConverter(Object[] obj) {
	String quarter=null;
	if (Quarter.Q1.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
		quarter=Quarter.Q1.toString()+"/"+ String.valueOf(obj[1]).split("\\.")[0];
	} else if (Quarter.Q2.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
		quarter=Quarter.Q2.toString()+"/"+ String.valueOf(obj[1]).split("\\.")[0];
	} else if (Quarter.Q3.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
		quarter=Quarter.Q3.toString()+"/"+ String.valueOf(obj[1]).split("\\.")[0];
	} else if (Quarter.Q4.getValue().equalsIgnoreCase(new Double(obj[0].toString()).toString())) {
		quarter=Quarter.Q4.toString()+"/"+ String.valueOf(obj[1]).split("\\.")[0];
	}
	return quarter;
}
}
