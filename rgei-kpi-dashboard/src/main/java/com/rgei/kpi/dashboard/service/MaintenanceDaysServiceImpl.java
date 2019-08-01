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
package com.rgei.kpi.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.rgei.crosscutting.logger.RgeiLoggerFactory;
import com.rgei.crosscutting.logger.service.CentralizedLogger;
import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.MillBuMaintenanceDayEntity;
import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.repository.MillBuMaintenanceDayEntityRepository;
import com.rgei.kpi.dashboard.response.model.DeleteRequest;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysRequest;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysResponse;
import com.rgei.kpi.dashboard.response.model.UpdateRemarksRequest;
import com.rgei.kpi.dashboard.util.CommonFunction;
import com.rgei.kpi.dashboard.util.MaintenanceDaysUtil;
import com.rgei.kpi.dashboard.util.Utility;


@Service
public class MaintenanceDaysServiceImpl implements MaintenanceDaysService{
	
	CentralizedLogger logger = RgeiLoggerFactory.getLogger(MaintenanceDaysServiceImpl.class);

	@Resource
	private MillBuMaintenanceDayEntityRepository millBuMaintenanceDayEntityRepository;

	@Override
	public void saveMaintenanceDays(MaintenanceDaysRequest maintenanceDaysRequest) {
		logger.info("Saving maintenance days", maintenanceDaysRequest);
		if(maintenanceDaysRequest != null) {
			try {
			processMaintenanceDaysSaveRequest(maintenanceDaysRequest);
			}catch(DataIntegrityViolationException e) {
				logger.error("Error while saving maintenance days", e, maintenanceDaysRequest);
			}
		}
		
	}

	private void processMaintenanceDaysSaveRequest(
			MaintenanceDaysRequest maintenanceDaysRequest) {
		logger.info("Processing maintenance days save request", maintenanceDaysRequest);
		List<String> dates = maintenanceDaysRequest.getMaintenanceDays();
		checkForExistingMaintainanceDates(dates);
		MillBuMaintenanceDayEntity millBuMaintenanceDayEntity = null;
		MillEntity millEntity = new MillEntity();
		RgeUserEntity user = new RgeUserEntity();
		if(!dates.isEmpty()) {
			for(String date: dates) {
				millBuMaintenanceDayEntity = new MillBuMaintenanceDayEntity();
				millEntity.setMillId(maintenanceDaysRequest.getMillId());
				millBuMaintenanceDayEntity.setMill(millEntity);
				millBuMaintenanceDayEntity.setBuId(maintenanceDaysRequest.getBuId());
				user.setUserId(maintenanceDaysRequest.getCreatedBy());
				millBuMaintenanceDayEntity.setCreatedBy(user);
				millBuMaintenanceDayEntity.setCreatedDate(new Date());
				millBuMaintenanceDayEntity.setMaintenanceDays(Utility.stringToDateConvertor(date, DashboardConstant.FORMAT));
				millBuMaintenanceDayEntity.setActive(Boolean.TRUE);
				millBuMaintenanceDayEntity.setRemarks(maintenanceDaysRequest.getRemarks());
				millBuMaintenanceDayEntityRepository.save(millBuMaintenanceDayEntity);
			}
		}else {
			logger.info("Maintenance dates are empty", dates);
		}
	}

	private void checkForExistingMaintainanceDates(List<String> dates) {
		logger.info("Checking for existing maintenance days for dates", dates);
		if(dates != null && !dates.isEmpty()) {
			List<Date> dateList = new ArrayList<>();
			for(String date :dates) {
				dateList.add(Utility.stringToDateConvertor(date, DashboardConstant.FORMAT));
			}
			List<MillBuMaintenanceDayEntity>  existingDate = millBuMaintenanceDayEntityRepository.findByMaintenanceDays(dateList);
			if(existingDate != null && !existingDate.isEmpty()) {
				logger.info("No maintenance days found", existingDate);
			}
		}
		
	}

	@Override
	public List<MaintenanceDaysResponse> getMaintainanceDayDetails(String millId, String buId) {
		logger.info("Getting maintenance days details");
		List<MaintenanceDaysResponse> response =null;
		List<MillBuMaintenanceDayEntity> entities = millBuMaintenanceDayEntityRepository.findByMillAndbuId(CommonFunction.covertToInteger(millId),CommonFunction.covertToInteger(buId));
			if(entities != null && !entities.isEmpty()) {
			return MaintenanceDaysUtil.convertIntoResponse(entities);
		}
		return response;
	}

	@Override
	public void deleteMaintainanceDayDetails(DeleteRequest request) {
		logger.info("Deleting maintenance days", request);
		if(request != null && request.getIds() != null && !request.getIds().isEmpty() ) {
			List<Long> ids = new ArrayList<>();
			for(String id:request.getIds()) {
				ids.add(CommonFunction.covertToLong(id));
			}
			List<MillBuMaintenanceDayEntity> entities = millBuMaintenanceDayEntityRepository.findBymillBuMdIdIn(ids);
			for(MillBuMaintenanceDayEntity entity:entities) {
				entity.setActive(Boolean.FALSE);
				try {
					millBuMaintenanceDayEntityRepository.save(entity);
				}catch(EmptyResultDataAccessException e) {
					logger.error("Error while deleting maintenane days", e, entity);
				}
			}
		}
		
	}

	@Override
	public void updateMaintainanceDayRemarks(UpdateRemarksRequest request) {
		logger.info("Updating maintenance days remarks", request);
		if(request != null && Objects.nonNull(request.getRemarks()) && Objects.nonNull(request.getIds()) && !request.getIds().isEmpty() ) {
			List<Long> ids = new ArrayList<>();
			for(String id:request.getIds()) {
				ids.add(CommonFunction.covertToLong(id));
			}
			List<MillBuMaintenanceDayEntity> entities = millBuMaintenanceDayEntityRepository.findBymillBuMdIdIn(ids);
			for(MillBuMaintenanceDayEntity entity:entities) {
				RgeUserEntity user = new RgeUserEntity();
				user.setUserId(request.getUpdatedBy());
				entity.setRemarks(request.getRemarks());
				entity.setUpdatedBy(user);
				entity.setUpdatedDate(new Date());
				try {	
					millBuMaintenanceDayEntityRepository.save(entity);
				}
				catch(EmptyResultDataAccessException e) {
					logger.error("Error while updating maintenane days remarks for existing ids", e, entity);
				}
				catch(JpaObjectRetrievalFailureException e) {
					logger.error("Invalid user id passed", e, entity);
				}
			}
		}else {
			logger.info("No ids or empty remarks found in the request", request);
		}
	}

}
