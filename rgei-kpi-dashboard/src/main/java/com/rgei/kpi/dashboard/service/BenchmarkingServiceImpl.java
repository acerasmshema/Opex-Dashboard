package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.KpiEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.BenchmarkingRepository;
import com.rgei.kpi.dashboard.repository.KpiRepository;
import com.rgei.kpi.dashboard.response.model.BenchmarkingReponse;
import com.rgei.kpi.dashboard.response.model.BenchmarkingRequest;
import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.Kpi;
import com.rgei.kpi.dashboard.util.BenchmarkingUtility;
import com.rgei.kpi.dashboard.util.BenchmarkingUtilityKRC;
import com.rgei.kpi.dashboard.util.BenchmarkingUtilityRZ;
import com.rgei.kpi.dashboard.util.Utility;

@Service
public class BenchmarkingServiceImpl implements BenchmarkingService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(BenchmarkingServiceImpl.class);

	@Resource
	KpiRepository kpiRepository;

	@Resource
	BenchmarkingRepository benchmarkingRepository;

	@Override
	@Transactional
	public BenchmarkingReponse getBenchmarkingData(BenchmarkingRequest benchmarkingRequest) {

		logger.info("Getting benchmarking data for benchmarking dashboards", benchmarkingRequest);
		List<DateRangeResponse> resultList = new ArrayList<>();
		Kpi kpi = null;
		Optional<KpiEntity> kpiEntity = Optional.ofNullable(kpiRepository.findByKpiId(benchmarkingRequest.getKpiId()));
		List<Kpi> kpiList = new ArrayList<>();
		Collections.sort(benchmarkingRequest.getMillId());
		if (kpiEntity.isPresent()) {
			for (Integer millId : benchmarkingRequest.getMillId()) {
				kpi = BenchmarkingUtility.convertToKpiDTO(kpiEntity.get(), millId);
				kpiList.add(kpi);
			}

		} else {
			logger.info("KPI Entity not present for kpi id", benchmarkingRequest.getKpiId());
			throw new RecordNotFoundException("Record not found to get benchmarking data for Kpi Id : "+benchmarkingRequest.getKpiId());
		}
		Map<String, List<String>> finalBenchmarkingProcessLines = BenchmarkingUtility.fetchProcessLines(kpiList);
		if (!Objects.nonNull(benchmarkingRequest.getFrequency())) {
			benchmarkingRequest.setFrequency(1);
		}

		switch (benchmarkingRequest.getFrequency()) {
		case 0:
			getDailyFrequencyResponse(benchmarkingRequest, resultList, finalBenchmarkingProcessLines);
			break;
		case 1:
			getMonthlyFrequencyResponse(benchmarkingRequest, resultList, finalBenchmarkingProcessLines);
			break;
		case 2:
			getQuarterlyFrequencyResponse(benchmarkingRequest, resultList, finalBenchmarkingProcessLines);
			break;
		case 3:
			getYearlyFrequencyResponse(benchmarkingRequest, resultList, finalBenchmarkingProcessLines);
			break;
		default:
			getDailyFrequencyResponse(benchmarkingRequest, resultList, finalBenchmarkingProcessLines);

		}

		return BenchmarkingUtility.fetchBenchmarkingResponse(kpi, resultList);
	}

	private void getDailyFrequencyResponse(BenchmarkingRequest benchmarkingRequest, List<DateRangeResponse> resultList,
			Map<String, List<String>> finalBenchmarkingProcessLines) {

		logger.info("Creating daily frequency benchmarking response for process lines", finalBenchmarkingProcessLines);
		getPreviousYearData(benchmarkingRequest, finalBenchmarkingProcessLines, resultList);
		List<DateRangeResponse> benchmarkingDataKRC = null;
		List<DateRangeResponse> benchmarkingDataRZ = null;
		for (Integer millId : benchmarkingRequest.getMillId()) {
			List<Object[]> responseEntity = benchmarkingRepository.getBenchmarkingDailyAvgData(
					Utility.stringToDateConvertor(benchmarkingRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(benchmarkingRequest.getEndDate(), DashboardConstant.FORMAT), millId,
					benchmarkingRequest.getKpiId());
			if (DashboardConstant.KRC.equals(millId.toString())) {
				benchmarkingDataKRC = BenchmarkingUtilityKRC.fetchBenchmarkingDailyData(responseEntity,
						finalBenchmarkingProcessLines);
			} else if (DashboardConstant.RZ.equals(millId.toString())) {
				benchmarkingDataRZ = BenchmarkingUtilityRZ.fetchBenchmarkingDailyData(responseEntity,
						finalBenchmarkingProcessLines);
			}
		}
		BenchmarkingUtility.getMergedResponse(benchmarkingDataKRC, benchmarkingDataRZ, resultList);

	}

	private void getYearlyFrequencyResponse(BenchmarkingRequest benchmarkingRequest, List<DateRangeResponse> resultList,
			Map<String, List<String>> finalBenchmarkingProcessLines) {

		logger.info("Creating yearly frequency benchmarking response for process lines", finalBenchmarkingProcessLines);
		getPreviousYearData(benchmarkingRequest, finalBenchmarkingProcessLines, resultList);
		List<DateRangeResponse> benchmarkingDataKRC = null;
		List<DateRangeResponse> benchmarkingDataRZ = null;
		for (Integer millId : benchmarkingRequest.getMillId()) {
			List<Object[]> responseEntity = benchmarkingRepository.getBenchmarkingYearlyAvgData(
					Utility.stringToDateConvertor(benchmarkingRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(benchmarkingRequest.getEndDate(), DashboardConstant.FORMAT), millId,
					benchmarkingRequest.getKpiId());
			if (DashboardConstant.KRC.equals(millId.toString())) {
				benchmarkingDataKRC = BenchmarkingUtilityKRC.fetchBenchmarkingYearlyData(responseEntity,
						finalBenchmarkingProcessLines);
			} else if (DashboardConstant.RZ.equals(millId.toString())) {
				benchmarkingDataRZ = BenchmarkingUtilityRZ.fetchBenchmarkingYearlyData(responseEntity,
						finalBenchmarkingProcessLines);
			}
		}
		BenchmarkingUtility.getMergedResponse(benchmarkingDataKRC, benchmarkingDataRZ, resultList);

	}

	private void getQuarterlyFrequencyResponse(BenchmarkingRequest benchmarkingRequest,
			List<DateRangeResponse> resultList, Map<String, List<String>> finalBenchmarkingProcessLines) {

		logger.info("Creating quarterly frequency benchmarking response for process lines",
				finalBenchmarkingProcessLines);
		getPreviousYearData(benchmarkingRequest, finalBenchmarkingProcessLines, resultList);
		List<DateRangeResponse> benchmarkingDataKRC = null;
		List<DateRangeResponse> benchmarkingDataRZ = null;
		for (Integer millId : benchmarkingRequest.getMillId()) {
			List<Object[]> responseEntity = benchmarkingRepository.getBenchmarkingQuarterlyAvgData(
					Utility.stringToDateConvertor(benchmarkingRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(benchmarkingRequest.getEndDate(), DashboardConstant.FORMAT), millId,
					benchmarkingRequest.getKpiId());
			if (DashboardConstant.KRC.equals(millId.toString())) {
				benchmarkingDataKRC = BenchmarkingUtilityKRC.fetchBenchmarkingQuarterlyData(responseEntity,
						finalBenchmarkingProcessLines);
			} else if (DashboardConstant.RZ.equals(millId.toString())) {
				benchmarkingDataRZ = BenchmarkingUtilityRZ.fetchBenchmarkingQuarterlyData(responseEntity,
						finalBenchmarkingProcessLines);
			}
		}
		BenchmarkingUtility.getMergedResponse(benchmarkingDataKRC, benchmarkingDataRZ, resultList);

	}

	private void getMonthlyFrequencyResponse(BenchmarkingRequest benchmarkingRequest,
			List<DateRangeResponse> resultList, Map<String, List<String>> finalBenchmarkingProcessLines) {

		logger.info("Creating monthly frequency benchmarking response for process lines",
				finalBenchmarkingProcessLines);
		getPreviousYearData(benchmarkingRequest, finalBenchmarkingProcessLines, resultList);
		List<DateRangeResponse> benchmarkingDataKRC = null;
		List<DateRangeResponse> benchmarkingDataRZ = null;
		for (Integer millId : benchmarkingRequest.getMillId()) {
			List<Object[]> responseEntity = benchmarkingRepository.getBenchmarkingMonthlyAvgData(
					Utility.stringToDateConvertor(benchmarkingRequest.getStartDate(), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(benchmarkingRequest.getEndDate(), DashboardConstant.FORMAT), millId,
					benchmarkingRequest.getKpiId());
			if (DashboardConstant.KRC.equals(millId.toString())) {
				benchmarkingDataKRC = BenchmarkingUtilityKRC.fetchBenchmarkingMonthlyData(responseEntity,
						finalBenchmarkingProcessLines);
			} else if (DashboardConstant.RZ.equals(millId.toString())) {
				benchmarkingDataRZ = BenchmarkingUtilityRZ.fetchBenchmarkingMonthlyData(responseEntity,
						finalBenchmarkingProcessLines);
			}
		}
		BenchmarkingUtility.getMergedResponse(benchmarkingDataKRC, benchmarkingDataRZ, resultList);
	}

	private void getPreviousYearData(BenchmarkingRequest benchmarkingRequest,
			Map<String, List<String>> finalBenchmarkingProcessLines, List<DateRangeResponse> resultList) {
		logger.info("Creating frequency data for previous year benchmarking response for process lines",
				finalBenchmarkingProcessLines);
		List<String> dates = BenchmarkingUtility.getPreviousYearDates(benchmarkingRequest.getStartDate(),
				benchmarkingRequest.getEndDate());
		List<DateRangeResponse> benchmarkingpreviousYearDataKRC = null;
		List<DateRangeResponse> benchmarkingpreviousYearDataRZ = null;
		for (Integer millId : benchmarkingRequest.getMillId()) {
			List<Object[]> responseEntity = benchmarkingRepository.getBenchmarkingYearlyAvgData(
					Utility.stringToDateConvertor(dates.get(0), DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(dates.get(1), DashboardConstant.FORMAT), millId,
					benchmarkingRequest.getKpiId());
			if (DashboardConstant.KRC.equals(millId.toString())) {
				benchmarkingpreviousYearDataKRC = BenchmarkingUtilityKRC.fetchBenchmarkingYearlyData(responseEntity,
						finalBenchmarkingProcessLines);
			} else if (DashboardConstant.RZ.equals(millId.toString())) {
				benchmarkingpreviousYearDataRZ = BenchmarkingUtilityRZ.fetchBenchmarkingYearlyData(responseEntity,
						finalBenchmarkingProcessLines);
			}
		}
		if (Objects.nonNull(benchmarkingpreviousYearDataKRC) && Objects.nonNull(benchmarkingpreviousYearDataRZ)
				&& !(benchmarkingpreviousYearDataKRC.isEmpty() && benchmarkingpreviousYearDataRZ.isEmpty())) {
			BenchmarkingUtility.getMergedResponse(benchmarkingpreviousYearDataKRC, benchmarkingpreviousYearDataRZ,
					resultList);
		}
	}

}
