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

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.response.model.RgeUserResponse;

public class UserConverter {
	
	private UserConverter() {
	}
	
	public static List<String> splitName(String name) {
		StringTokenizer stok = new StringTokenizer(name);
		List<String> names = new ArrayList<>();
	    while (stok.hasMoreTokens()) {
	    	names.add(stok.nextToken());
	    }
		return names;
	}
	
	public static RgeUserResponse convertToResponse(RgeUserEntity rgeUserEntity) {
		RgeUserResponse rgeUserResponse = null;
		if(Objects.nonNull(rgeUserEntity)) {
			rgeUserResponse = new RgeUserResponse();
			rgeUserResponse.setLoginId(rgeUserEntity.getLoginId());
			rgeUserResponse.setUserName(rgeUserEntity.getFirstName() + " " +rgeUserEntity.getLastName());
			if(!rgeUserEntity.getUserRoles().isEmpty()) {
				rgeUserResponse.setUserRole(rgeUserEntity.getUserRoles().get(0).getRoleName());
			}
		}
		return rgeUserResponse;
	}
	
	public static Date getCurrentDate() {
		LocalDate currentDate= LocalDate.now();
		return Date.valueOf(currentDate.toString());
	}
	
}
