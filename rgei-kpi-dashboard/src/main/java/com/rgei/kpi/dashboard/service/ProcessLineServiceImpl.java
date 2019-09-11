package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.BusinessUnitTypeRepository;
import com.rgei.kpi.dashboard.repository.MillEntityRepository;
import com.rgei.kpi.dashboard.repository.ProcessLineRepository;
import com.rgei.kpi.dashboard.response.model.BuTypeResponse;
import com.rgei.kpi.dashboard.response.model.MillsResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineDetailsResponse;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.ProcessLineUtility;

@Service
public class ProcessLineServiceImpl implements ProcessLineService {

	@Resource
	ProcessLineRepository processLineRepository;

	@Resource
	MillEntityRepository millEntityRepository;

	@Resource
	BusinessUnitTypeRepository businessUnitTypeRepository;

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(KpiDashboardCategoryServiceImpl.class);

	@Override
	public List<ProcessLineDetailsResponse> getProcessLines(String millId) {
		if (Objects.nonNull(millId)) {
			List<ProcessLineEntity> processLine;
			try {
				processLine = processLineRepository.getPrcessLineByLocation(CommonFunction.covertToInteger(millId),
						Boolean.TRUE);
			} catch (Exception e) {
				throw new RecordNotFoundException("Error while fetching process lines for mill Id : " + millId);
			}
			if(processLine == null || processLine.isEmpty()) {
				throw new RecordNotFoundException("Error while fetching process lines for mill Id : " + millId);
			}
			return ProcessLineUtility.generateResponse(processLine);
		}
		return null;
	}

	@Override
	public List<MillsResponse> getMillDetails(List<String> countryIds) {
		if (countryIds != null && !countryIds.isEmpty()) {
			List<Integer> ids = new ArrayList<>();
			for (String s : countryIds) {
				ids.add(CommonFunction.covertToInteger(s));
			}
			return ProcessLineUtility.prePareMillResponse(millEntityRepository.findByCountry(ids, Boolean.TRUE));
		} else {
			return ProcessLineUtility.prePareMillResponse(millEntityRepository.findByActive(Boolean.TRUE));
		}
	}

	@Override
	public List<BuTypeResponse> getAllBuType() {
		List<BuTypeResponse> buTypeResponse = null;
		Optional<List<BusinessUnitTypeEntity>> businessUnitTypeEntity = Optional
				.ofNullable(businessUnitTypeRepository.findAll());
		if (businessUnitTypeEntity.isPresent()) {
			List<BusinessUnitTypeEntity> businessUnitTypeEntityList = businessUnitTypeEntity.get();
			try {
				buTypeResponse = ProcessLineUtility.preareBUTypeResponse(businessUnitTypeEntityList);
			} catch (Exception e) {
				logger.info("error in fetching BU Types", e);
				throw new RecordNotFoundException("Error while fetching record for Bu Types");
			}
		}
		return buTypeResponse;
	}

}
