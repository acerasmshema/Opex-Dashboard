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

import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.response.model.CountryResponse;

public class CommonFunction {
	
	private CommonFunction() {
		
	}
	
	public static Integer covertToInteger(String integerValue) {
		return Integer.parseInt(integerValue);
	}
	
	public static Long covertToLong(String longValue) {
		return Long.parseLong(longValue);
	}
	
	public static Date getYesterdayDate() {
		LocalDate yesterdayDate= LocalDate.now().minusDays(1);
		return Date.valueOf(yesterdayDate.toString());
	}

	public static String getString(Object obj) {
		if(obj != null) {
			return obj.toString();
		}
		return "";
	}
	
	public static CountryResponse convertCountryEntityToResponse(CountryEntity countryEntity) {
		CountryResponse countryResponse=new CountryResponse();
		if(countryEntity!=null) {
			countryResponse.setCountryId(countryEntity.getCountryId().toString());
			countryResponse.setCountryCode(countryEntity.getCountryCode());
			countryResponse.setCountryName(countryEntity.getCountryName());
			countryResponse.setActive(countryEntity.getActive());
			countryResponse.setCreatedBy(countryEntity.getCreatedBy());
			countryResponse.setCreatedDate(countryEntity.getCreatedDate().toString());
			countryResponse.setUpdatedBy(countryEntity.getUpdatedBy());
			countryResponse.setUpdatedDate(countryEntity.getUpdatedDate().toString());
		}
		return countryResponse;
		}
}
