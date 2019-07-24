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
package com.rgei.kpi.dashboard.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.MillBuMaintenanceDayEntity;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysRequest;
import com.rgei.kpi.dashboard.response.model.MaintenanceDaysResponse;

public class MaintenanceDaysUtil {
	
	//no-arg constructor
	private MaintenanceDaysUtil() {
	}
	
	public static MillBuMaintenanceDayEntity convertIntoPersistenceEntity(MaintenanceDaysRequest maintenanceDaysRequest,
			MillBuMaintenanceDayEntity millBuMaintenanceDayEntity) {
		List<String> dates = maintenanceDaysRequest.getMaintenanceDays();
		for(String date: dates) {  
			millBuMaintenanceDayEntity.getMill().setMillId(maintenanceDaysRequest.getMillId());
			millBuMaintenanceDayEntity.setBuId(maintenanceDaysRequest.getBuId());
			millBuMaintenanceDayEntity.getUpdatedBy().setUserId(maintenanceDaysRequest.getCreatedBy());
			millBuMaintenanceDayEntity.setUpdatedDate(new Date());
			millBuMaintenanceDayEntity.setMaintenanceDays(Utility.stringToDateConvertor(date, DashboardConstant.FORMAT));
			millBuMaintenanceDayEntity.setActive(Boolean.TRUE);
			millBuMaintenanceDayEntity.setRemarks(maintenanceDaysRequest.getRemarks());
		}
		return millBuMaintenanceDayEntity;
	}

	public static List<MaintenanceDaysResponse> convertIntoResponse(List<MillBuMaintenanceDayEntity> entities) {
		List<MaintenanceDaysResponse> responseList = new ArrayList<>();
		MaintenanceDaysResponse maintenanceDaysResponse  = null;
		for(MillBuMaintenanceDayEntity entity:entities) {
			maintenanceDaysResponse = new MaintenanceDaysResponse();
			maintenanceDaysResponse.setId(entity.getMillBuMdId());
			maintenanceDaysResponse.setMaintainceDates(Utility.dateToStringConvertor(entity.getMaintenanceDays(), DashboardConstant.FORMAT));
			maintenanceDaysResponse.setRemarks(entity.getRemarks());
			responseList.add(maintenanceDaysResponse);
		}
		return responseList;
	}

}
