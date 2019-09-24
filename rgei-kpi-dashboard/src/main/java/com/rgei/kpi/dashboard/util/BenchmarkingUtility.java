package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.KpiEntity;
import com.rgei.kpi.dashboard.entities.KpiProcessLineEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.response.model.BenchmarkingReponse;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.Kpi;
import com.rgei.kpi.dashboard.response.model.SeriesObject;

public class BenchmarkingUtility {
	
	private static CentralizedLogger logger = RgeiLoggerFactory.getLogger(BenchmarkingUtility.class);
	
	private BenchmarkingUtility() {
		
	}
	
	public static Kpi convertToKpiDTO(KpiEntity entity, Integer millId) {
		logger.info("Convert Kpi Entity to Kpi Object", entity, millId);
		Kpi kpiObject = new Kpi();
		if(Boolean.TRUE.equals(entity.getActive())) {
		List<String> kpiProcessLines = new ArrayList<>();
		kpiObject.setKpiId(entity.getKpiId());
		kpiObject.setKpiName(entity.getKpiName());
		kpiObject.setKpiTypeId(entity.getKpiType().getKpiTypeId());
		kpiObject.setKpiUnit(entity.getKpiUnit());
		for (KpiProcessLineEntity value : entity.getKpiProcessLines()) {
			if(Boolean.TRUE.equals(value.getProcessLine().getIsBenchmarking()) && Boolean.TRUE.equals(value.getActive()) && value.getMill().getMillId().equals(millId)){
					kpiProcessLines.add(value.getProcessLine().getProcessLineCode());
					Collections.sort(kpiProcessLines);
			}
		}
		kpiObject.setKpiProcessLines(kpiProcessLines);
		}
		return kpiObject;
	}

	public static BenchmarkingReponse fetchBenchmarkingResponse(Kpi kpi, List<DateRangeResponse> resultList) {
		BenchmarkingReponse benchmarkingResponse = new BenchmarkingReponse();
		benchmarkingResponse.setKpiId(kpi.getKpiId().toString());
		benchmarkingResponse.setKpiName(kpi.getKpiName());
		benchmarkingResponse.setKpiUnit(kpi.getKpiUnit());
		if (!resultList.isEmpty()) {
			benchmarkingResponse.setKpiData(resultList);
		}
		return benchmarkingResponse;

	}

	public static Map<String, List<String>> fetchProcessLines(List<Kpi> kpiList) {
		logger.info("Fetching process lines for Kpi List ", kpiList);
		Map<String, List<String>> linesList = new TreeMap<>();
		List<String> krcProcessLines = new ArrayList<>();
		List<String> rzProcessLines = new ArrayList<>();
		krcProcessLines.addAll(kpiList.get(0).getKpiProcessLines());
		krcProcessLines.add(DashboardConstant.KRC_AVG);
		linesList.put(DashboardConstant.KRC, krcProcessLines);
		rzProcessLines.addAll(kpiList.get(1).getKpiProcessLines());
		rzProcessLines.add(DashboardConstant.RZ_AVG);
		linesList.put(DashboardConstant.RZ, rzProcessLines);
		return linesList;
	}

	public static void getMergedResponse(List<DateRangeResponse> benchmarkingDataKRC,
			List<DateRangeResponse> benchmarkingDataRZ, List<DateRangeResponse> resultList) {
		logger.info("Fetching merged response");
		DateRangeResponse obj = null;
		for(int i=0;i<benchmarkingDataKRC.size();i++) {
			obj = benchmarkingDataKRC.get(i);
			List<SeriesObject> series= obj.getSeries();
			for(SeriesObject merge:benchmarkingDataRZ.get(i).getSeries()) {
			series.add(merge);
			}
			obj.setSeries(series);
			resultList.add(obj);
		}
	}

	public static List<String> getPreviousYearDates(String startDate, String endDate) {
		List<String> dates = new ArrayList<>();
		Date date = Utility.stringToDateConvertor(startDate, DashboardConstant.FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR)-1;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_YEAR, 1);    
		Date start = cal.getTime();
		String yearStartDate =Utility.dateToStringConvertor(start, DashboardConstant.FORMAT);
		dates.add(yearStartDate);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 11); 
		cal.set(Calendar.DAY_OF_MONTH, 31);
		Date end = cal.getTime();
		String yearEndDate =Utility.dateToStringConvertor(end, DashboardConstant.FORMAT);
		dates.add(yearEndDate);
		
		return dates;
		
	}
}