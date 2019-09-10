package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.repository.CountryRepository;
import com.rgei.kpi.dashboard.response.model.CountryResponse;

@Service
public class CountryServiceImpl implements CountryService {

	CentralizedLogger logger = RgeiLoggerFactory.getLogger(CountryServiceImpl.class);
	
	@Resource
	CountryRepository countryRepository;
	
	@Override
	public List<CountryResponse> getCountryList() {
		logger.info("Inside service call to get all countries");
		List<CountryResponse> responseList = new ArrayList<CountryResponse>();
		List<CountryEntity> entities = countryRepository.findAllByActiveOrderByCountryNameAsc(true);
		if(entities != null && !entities.isEmpty()) {
			CountryResponse resp = null;
			for(CountryEntity entity : entities) {
				resp = new CountryResponse();
				resp.setCountryId(entity.getCountryId());
				resp.setCountryName(entity.getCountryName());
				resp.setCountryCode(entity.getCountryCode());
				responseList.add(resp);
			}
			return responseList;
		}
		throw new RecordNotFoundException("Country list not available in database");
	}

}
