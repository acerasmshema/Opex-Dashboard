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
import java.util.Objects;

import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;
import com.rgei.kpi.dashboard.entities.CountryEntity;
import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.response.model.BuTypeResponse;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.MillDetail;
import com.rgei.kpi.dashboard.response.model.MillRole;
import com.rgei.kpi.dashboard.response.model.ProcessLine;

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
		CountryResponse countryResponse= null;
		if(countryEntity!=null) {
			countryResponse = new CountryResponse();
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
	
	public static BuTypeResponse convertBUEntityToResponse(BusinessUnitTypeEntity buType) {
		BuTypeResponse buTypeResponse = null;
		if(Objects.nonNull(buType)) {
			buTypeResponse = new BuTypeResponse();
			buTypeResponse.setBuTypeId(buType.getBusinessUnitTypeId());
			buTypeResponse.setBuId(buType.getBusinessUnit().getBusinessUnitId());
			buTypeResponse.setBuTypeName(buType.getBusinessUnitTypeName());
			buTypeResponse.setBuTypeCode(buType.getBusinessUnitTypeCode());
		}
		return buTypeResponse;
	}
	
	public static MillDetail convertMillEntityToResponse(MillEntity millEntity) {
		MillDetail millDetail = null;
		if(Objects.nonNull(millEntity)) {
			millDetail = new MillDetail();
			millDetail.setMillId(millEntity.getMillId().toString());
			millDetail.setMillCode(millEntity.getMillCode().toString());
			millDetail.setMillName(millEntity.getMillName().toString());
			millDetail.setActive(millEntity.getActive());
		}
		return millDetail;
	}
	
	public static ProcessLine convertProcessLineEntityToResponse(ProcessLineEntity processLineEntity) {
		ProcessLine processLine = null;
		if(Objects.nonNull(processLineEntity)) {
			processLine = new ProcessLine();
			processLine.setProcessLineId(processLineEntity.getProcessLineId());
			processLine.setProcessLineCode(processLineEntity.getProcessLineCode());
			processLine.setProcessLineName(processLineEntity.getProcessLineName());
		}
		return processLine;
	}
	
	
}
