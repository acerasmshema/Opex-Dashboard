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

import java.util.List;

import com.rgei.kpi.dashboard.response.model.KpiAnnotationDateRangeSerach;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDateSerachRes;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationDeleteRequest;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationRequest;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationResponse;
import com.rgei.kpi.dashboard.response.model.KpiAnnotationSearchRequest;

public interface KpiAnnotationService {
	
	public void saveKpiAnnotationRequest(KpiAnnotationRequest kpiAnnotationRequest);
	
	public List<KpiAnnotationResponse> getAnnotationDetails(KpiAnnotationSearchRequest kpiAnnotationSearchRequest); 
	
	public KpiAnnotationDateSerachRes kpiAnnotationDateRangeSerach(KpiAnnotationDateRangeSerach kpiAnnotationDateRangeSerach);
	
	public void deleteAnnotation(List<KpiAnnotationDeleteRequest> kpiAnnotationDeleteRequest);
}
