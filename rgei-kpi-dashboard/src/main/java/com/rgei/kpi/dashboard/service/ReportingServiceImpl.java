package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.LoginDetailEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.repository.LoginDetailEntityRepository;
import com.rgei.kpi.dashboard.response.model.LoginDetailResponse;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.UserConverter;
import com.rgei.kpi.dashboard.util.Utility;

@Service
public class ReportingServiceImpl implements ReportingService {
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(ReportingServiceImpl.class);

	@Resource
	LoginDetailEntityRepository loginDetailEntityRepository;

	@Override
	public List<LoginDetailResponse> getAllUserLoginDetails(String startDate, String endDate, String millId) {
		logger.info("Inside get all login details by MillId :{}", millId);
		if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
			List<LoginDetailEntity> loginDetailEntityList = loginDetailEntityRepository.findAllLoginDetailsByLoginTime(
					Utility.stringToDateConvertor(startDate, DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(endDate, DashboardConstant.FORMAT));
			return UserConverter.convertLoginUserInfoToLoginDetailResponse(loginDetailEntityList, CommonFunction.covertToInteger(millId));
		}
		return new ArrayList<LoginDetailResponse>();
	}

	@Override
	@Transactional
	public List<LoginDetailResponse> getDistinctUserLoginDetails(String startDate, String endDate, String millId) {
		logger.info("Inside get distinct login details by MillId :{}", millId);
		if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
			List<RgeUserEntity> loginDetailEntityList = loginDetailEntityRepository.findDistinctLoginDetailsByLoginTime(
					Utility.stringToDateConvertor(startDate, DashboardConstant.FORMAT),
					Utility.stringToDateConvertor(endDate, DashboardConstant.FORMAT));
			return UserConverter.convertToLoginDetailResponseFromRgeUserEntity(loginDetailEntityList, CommonFunction.covertToInteger(millId));
		}
		return new ArrayList<LoginDetailResponse>();
	}
	
	

}
