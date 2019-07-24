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
import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;
import com.rgei.kpi.dashboard.entities.KpiAnnotationEntity;
import com.rgei.kpi.dashboard.entities.KpiEntity;
import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationRequest;

public class KpiAnnotationUtil {
	
	//no-arg constructor
	private KpiAnnotationUtil() {
	}

	public static List<KpiAnnotationEntity> convertToEntity(KpiAnnotationRequest kpiAnnotationRequest) {

		KpiAnnotationEntity kpiAnnotationEntity = null;
		List<KpiAnnotationEntity> annotations = new ArrayList<>();
		if(kpiAnnotationRequest != null ) {
				kpiAnnotationEntity = new KpiAnnotationEntity();
				kpiAnnotationEntity.setActive(Boolean.TRUE);
				kpiAnnotationEntity.setAnnotationDate(Utility.stringToDateConvertor(kpiAnnotationRequest.getAnnotationDate(), DashboardConstant.FORMAT));
				kpiAnnotationEntity.setCreatedDate(new Date());
				kpiAnnotationEntity.setDescription(kpiAnnotationRequest.getDescription());
				kpiAnnotationEntity.setUpdatedDate(new Date());

				BusinessUnitTypeEntity businessUnitType = new BusinessUnitTypeEntity();
				businessUnitType.setBusinessUnitTypeId(CommonFunction.covertToInteger(kpiAnnotationRequest.getBuTypeId()));
				kpiAnnotationEntity.setBusinessUnitType(businessUnitType);

				KpiEntity kpiEntity = new KpiEntity();
				kpiEntity.setKpiId(CommonFunction.covertToInteger(kpiAnnotationRequest.getKpiId()));
				kpiAnnotationEntity.setKpi(kpiEntity);

				MillEntity millEntity = new MillEntity();
				millEntity.setMillId(CommonFunction.covertToInteger(kpiAnnotationRequest.getMillId()));
				kpiAnnotationEntity.setMill(millEntity);

				kpiAnnotationEntity.setCreatedBy(kpiAnnotationRequest.getUserLoginId());
				kpiAnnotationEntity.setUpdatedBy(kpiAnnotationRequest.getUserLoginId());
				kpiAnnotationEntity.setProcessLines(kpiAnnotationRequest.getProcessLines());
				annotations.add(kpiAnnotationEntity);
		}
		return annotations;
	}

}
