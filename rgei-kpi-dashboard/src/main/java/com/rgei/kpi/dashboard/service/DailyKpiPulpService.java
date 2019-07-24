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

import com.rgei.kpi.dashboard.response.model.ProcessLineAnnualResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineSeries;

public interface DailyKpiPulpService {
	
	public ProcessLineAnnualResponse getAnnualProcessLine(String millId,
			String buId,String kpiCategoryId,String kpiId);
	
	public List<ProcessLineSeries> calculateDateWiseTotalProcessLine(String millId,
			String buId,String kpiCategoryId,String kpiId);
	
	public Long getActualTarget(String millId, String buId, String kpiCategoryId,
			String kpiId) ;

}
