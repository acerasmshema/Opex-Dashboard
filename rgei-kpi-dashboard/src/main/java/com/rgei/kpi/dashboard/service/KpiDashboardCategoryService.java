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
import java.util.Map;

import com.rgei.kpi.dashboard.response.model.DateRangeResponse;
import com.rgei.kpi.dashboard.response.model.KpiCategoryResponse;
import com.rgei.kpi.dashboard.response.model.KpiDashboardCategoryRequest;

/**
 * @author dixit.sharma
 *
 */

public interface KpiDashboardCategoryService {

	public List<DateRangeResponse> getKpiCategoryData(KpiDashboardCategoryRequest kpiDashboardCategoryRequest);
	
	public List<DateRangeResponse> getKpiCategoryLineChartData(KpiDashboardCategoryRequest kpiDashboardCategoryRequest);
		
	public List<List<Map<String,Object>>> getKpiCategoryDownloadGridData(KpiDashboardCategoryRequest kpiDashboardCategoryRequest);
	
	public List<KpiCategoryResponse> getYesterdayValuesForKpiCategory(Integer kpiCategoryId, Integer millId);
}
