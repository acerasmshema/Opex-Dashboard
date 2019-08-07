package com.rgei.kpi.dashboard.service;

import java.sql.Date;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.KpiEntity;
import com.rgei.kpi.dashboard.entities.KpiTypeEntity;
import com.rgei.kpi.dashboard.repository.KPICategoryEntityRepository;
import com.rgei.kpi.dashboard.repository.KpiDashboardCategoryRepository;
import com.rgei.kpi.dashboard.repository.KpiRepository;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.Kpi;
import com.rgei.kpi.dashboard.response.model.KpiCategoryResponse;
import com.rgei.kpi.dashboard.response.model.KpiDashboardCategoryRequest;
import com.rgei.kpi.dashboard.response.model.KpiType;
import com.rgei.kpi.dashboard.response.model.SeriesObject;
import com.rgei.kpi.dashboard.util.KpiConsumptionLineChartUtility;
import com.rgei.kpi.dashboard.util.KpiDashboardCategoryDataGridUtility;
import com.rgei.kpi.dashboard.util.KpiDashboardCategoryUtility;
import com.rgei.kpi.dashboard.util.Utility;

/**
 * @author dixit.sharma
 *
 */

@Service
public class KpiDashboardCategoryServiceImpl implements KpiDashboardCategoryService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiDashboardCategoryServiceImpl.class);
	
	@Resource
	KpiDashboardCategoryRepository kpiCategoryDashboardRepository;
	
	@Resource
	KpiRepository kpiRepository;
	
	@Resource
	KPICategoryEntityRepository kpiCategoryRepository;

	@Override
	public List<DateRangeResponse> getKpiCategoryData(KpiDashboardCategoryRequest kpiDashboardCategoryRequest) {
		logger.info("Getting kpi category data for consumption dashboards", kpiDashboardCategoryRequest);
		List<DateRangeResponse> resultList = new ArrayList<>();
		Kpi kpi = null;
		Optional<KpiEntity> kpiEntity  = Optional.ofNullable(kpiRepository.findByKpiId(kpiDashboardCategoryRequest.getKpiId()));
		if (kpiEntity.isPresent()) {
		kpi = KpiDashboardCategoryUtility.convertToKpiDTO(kpiEntity.get());
		}
		List<String> finalKpiProcessLines = null;
		List<String> lineList = Arrays.asList(kpiDashboardCategoryRequest.getProcessLines());
		finalKpiProcessLines = KpiDashboardCategoryUtility.fetchProcessLines(kpi, lineList);
		if (!Objects.nonNull(kpiDashboardCategoryRequest.getFrequency())) {
			kpiDashboardCategoryRequest.setFrequency(0);
		}
		
		switch (kpiDashboardCategoryRequest.getFrequency()) {
		case 0:
			getDailyFrequencyResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		case 1:
			getMonthlyFrequencyResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		case 2:
			getQuarterlyFrequencyResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		case 3:
			getYearlyFrequencyResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		default:
			getDailyFrequencyResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
		}
		return resultList;
	}
	
	@Override
	public List<DateRangeResponse> getKpiCategoryLineChartData(KpiDashboardCategoryRequest kpiDashboardCategoryRequest) {
		logger.info("Getting kpi category data for line chart for consumption dashboards", kpiDashboardCategoryRequest);
		List<DateRangeResponse> resultList = new ArrayList<>();
		Kpi kpi = null;
		Optional<KpiEntity> kpiEntity  = Optional.ofNullable(kpiRepository.findByKpiId(kpiDashboardCategoryRequest.getKpiId()));
		if (kpiEntity.isPresent()) {
		kpi = KpiDashboardCategoryUtility.convertToKpiDTO(kpiEntity.get());
		}
		List<String> finalKpiProcessLines = null;
		List<String> lineList = Arrays.asList(kpiDashboardCategoryRequest.getProcessLines());
		finalKpiProcessLines = KpiDashboardCategoryUtility.fetchProcessLines(kpi, lineList);
		if (!Objects.nonNull(kpiDashboardCategoryRequest.getFrequency())) {
			kpiDashboardCategoryRequest.setFrequency(0);
		}
		
		switch (kpiDashboardCategoryRequest.getFrequency()) {
		case 0:
			getDailyFrequencyLineChartResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		case 1:
			getMonthlyFrequencyLineChartResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		case 2:
			getQuarterlyFrequencyLineChartResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		case 3:
			getYearlyFrequencyLineChartResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
			break;
		default:
			getDailyFrequencyLineChartResponse(kpiDashboardCategoryRequest, resultList, finalKpiProcessLines);
		}
		return resultList;
	}
	@Override
	public List<List<Map<String, Object>>> getKpiCategoryDownloadGridData(KpiDashboardCategoryRequest kpiDashboardCategoryRequest) {
		logger.info("Getting kpi category data for grid download for consumption dashboards", kpiDashboardCategoryRequest);
		List<List<Map<String, Object>>> responseData = new ArrayList<>();
		Kpi kpi = null;
		Optional<KpiEntity> kpiEntity  = Optional.ofNullable(kpiRepository.findByKpiId(kpiDashboardCategoryRequest.getKpiId()));
		if (kpiEntity.isPresent()) {
			kpi = KpiDashboardCategoryUtility.convertToKpiDTO(kpiEntity.get());
		}
		List<String> finalKpiProcessLines = null;
		List<String> lineList = Arrays.asList(kpiDashboardCategoryRequest.getProcessLines());
		finalKpiProcessLines = KpiDashboardCategoryUtility.fetchProcessLines(kpi, lineList);
		if (!Objects.nonNull(kpiDashboardCategoryRequest.getFrequency())) {
			kpiDashboardCategoryRequest.setFrequency(0);
		}
		
		switch (kpiDashboardCategoryRequest.getFrequency()) {
		case 0:
			getDailyFrequencyGridResponse(kpiDashboardCategoryRequest, responseData, finalKpiProcessLines);
			break;
		case 1:
			getMonthlyFrequencyGridResponse(kpiDashboardCategoryRequest, responseData, finalKpiProcessLines);
			break;
		case 2:
			getQuarterlyFrequencyGridResponse(kpiDashboardCategoryRequest, responseData, finalKpiProcessLines);
			break;
		case 3:
			getYearlyFrequencyGridResponse(kpiDashboardCategoryRequest, responseData, finalKpiProcessLines);
			break;
		default:
			getDailyFrequencyGridResponse(kpiDashboardCategoryRequest, responseData, finalKpiProcessLines);
		}
		return responseData;
	}
	
	@Override
	public List<KpiCategoryResponse> getYesterdayValuesForKpiCategory(Integer kpiCategoryId, Integer millId) {
		logger.info("Getting yesterday values for kpi category against id", kpiCategoryId);
		List<KpiCategoryResponse> resultList = new ArrayList<>();
		List<KpiType> kpiType = null;
		Integer startKpiId = 0;
		Integer endKpiId = 0;
		Optional<List<KpiTypeEntity>> kpiTypeEntity  = Optional.ofNullable(kpiCategoryRepository.findByKpiCategoryId(kpiCategoryId));
		if (kpiTypeEntity.isPresent()) {
			kpiType = KpiDashboardCategoryUtility.convertToKpiTypeDTO(kpiTypeEntity.get(), millId);
		}
		if(kpiType != null) {
			startKpiId = kpiType.get(0).getKpi().getKpiId();
			endKpiId = kpiType.get(kpiType.size()-1).getKpi().getKpiId();
		}
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getYesterdayAllProcessLinesData(
				  KpiDashboardCategoryUtility.getYesterdayDate(), startKpiId, endKpiId, kpiCategoryId, millId);
		KpiDashboardCategoryUtility.fetchConsumptionGridResponse(resultList, kpiType, responseEntity);
		return resultList;
	}

	
	

	private void getYearlyFrequencyGridResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<List<Map<String, Object>>> responseData, List<String> finalKpiProcessLines) {
		logger.info("Creating yearly frequency grid response for process lines", finalKpiProcessLines);
		List<Map<String, Object>> response;
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalYearly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		response = KpiDashboardCategoryDataGridUtility.getGridDataYearly(finalKpiProcessLines, responseEntity);
		responseData.add(response);
	}

	private void getQuarterlyFrequencyGridResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<List<Map<String, Object>>> responseData, List<String> finalKpiProcessLines) {
		logger.info("Creating quarterly frequency grid response for process lines", finalKpiProcessLines);
		List<Map<String, Object>> response;
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalQuarterly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		response = KpiDashboardCategoryDataGridUtility.getGridDataQuarterly(finalKpiProcessLines, responseEntity);
		responseData.add(response);
	}

	private void getMonthlyFrequencyGridResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<List<Map<String, Object>>> responseData, List<String> finalKpiProcessLines) {
		logger.info("Creating monthly frequency grid response for process lines", finalKpiProcessLines);
		List<Map<String, Object>> response;
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalMonthly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		response = KpiDashboardCategoryDataGridUtility.getGridDataMonthly(finalKpiProcessLines, responseEntity);
		responseData.add(response);
	}

	private void getDailyFrequencyGridResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<List<Map<String, Object>>> responseData, List<String> finalKpiProcessLines) {
		logger.info("Creating daily frequency grid response for process lines", finalKpiProcessLines);
		List<Map<String, Object>> response;
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesDailyTotal(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		response = KpiDashboardCategoryDataGridUtility.getGridDataDailyResponse(finalKpiProcessLines, responseEntity);
		responseData.add(response);
	}

	
	
	private void getYearlyFrequencyResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating yearly frequency response for process lines", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalYearly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());

		for (Object[] obj: responseEntity) {
			DateRangeResponse val = new DateRangeResponse();
			List<SeriesObject> series = new ArrayList<>();
			val.setName(String.valueOf(obj[0]).split("\\.")[0]);
			for(String kpiProcessLine: finalKpiProcessLines) {
			SeriesObject value = new SeriesObject();
			val.setSeries(KpiDashboardCategoryUtility.getYearlySeriesResponse(obj, kpiProcessLine, value, series));
			}
			resultList.add(val);
		}
	}

	private void getQuarterlyFrequencyResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating quarterly frequency response for process lines", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalQuarterly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());

		for (Object[] obj: responseEntity) {
			KpiDashboardCategoryUtility.populateQuarterlyData(resultList, finalKpiProcessLines, obj);
		}
	}

	
	private void getMonthlyFrequencyResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating monthly frequency response for process lines", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalMonthly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		for (Object[] obj: responseEntity) {
			DateRangeResponse val = new DateRangeResponse();
			List<SeriesObject> series = new ArrayList<>();
			val.setName(Month.of(Integer.valueOf(String.valueOf(obj[0]).split("\\.")[0])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+"-"+String.valueOf(obj[9]).split("\\.")[0]);
			for(String kpiProcessLine: finalKpiProcessLines) {
			SeriesObject value = new SeriesObject();
			val.setSeries(KpiDashboardCategoryUtility.getMonthlySeriesResponse(obj, kpiProcessLine, value, series));
			}
			resultList.add(val);
		}
	}

	private void getDailyFrequencyResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating daily frequency response for process lines", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesDailyTotal(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		for (Object[] obj: responseEntity) {
			DateRangeResponse val = new DateRangeResponse();
			List<SeriesObject> series = new ArrayList<>();
			val.setName(Utility.dateToStringConvertor(Date.valueOf(obj[0].toString()), DashboardConstant.DATE_FORMAT));
			for(String kpiProcessLine: finalKpiProcessLines) {
			SeriesObject value = new SeriesObject();
			val.setSeries(KpiDashboardCategoryUtility.getDailySeriesResponse(obj, kpiProcessLine, value, series));
			}
			resultList.add(val);
		}
	}
	
	private void getYearlyFrequencyLineChartResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating yearly frequency response for line charts", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalYearly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());

		for (String processLine : finalKpiProcessLines) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(KpiConsumptionLineChartUtility.getYearlySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}

	private void getQuarterlyFrequencyLineChartResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating quarterly frequency response for line charts", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalQuarterly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());

		for (String processLine : finalKpiProcessLines) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(KpiConsumptionLineChartUtility.getQuarterlySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}

	
	private void getMonthlyFrequencyLineChartResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating monthly frequency response for line charts", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesTotalMonthly(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		for (String processLine : finalKpiProcessLines) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(KpiConsumptionLineChartUtility.getMonthlySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}

	private void getDailyFrequencyLineChartResponse(KpiDashboardCategoryRequest kpiDashboardCategoryRequest,
			List<DateRangeResponse> resultList, List<String> finalKpiProcessLines) {
		logger.info("Creating daily frequency response for line charts", finalKpiProcessLines);
		List<Object[]> responseEntity = kpiCategoryDashboardRepository.getProcessLinesDailyTotal(
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getStartDate(), DashboardConstant.FORMAT),
				Utility.stringToDateConvertor(kpiDashboardCategoryRequest.getEndDate(), DashboardConstant.FORMAT),
				kpiDashboardCategoryRequest.getMillId(), kpiDashboardCategoryRequest.getKpiCategoryId(), 
				kpiDashboardCategoryRequest.getBuId(), kpiDashboardCategoryRequest.getCountryId(), kpiDashboardCategoryRequest.getKpiId());
		for (String processLine : finalKpiProcessLines) {
			DateRangeResponse val = new DateRangeResponse();
			val.setName(processLine);
			val.setSeries(KpiConsumptionLineChartUtility.getDailySeriesResponse(processLine, responseEntity));
			resultList.add(val);
		}
	}
}
