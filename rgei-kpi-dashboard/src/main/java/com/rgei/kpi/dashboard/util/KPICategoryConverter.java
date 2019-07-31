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
import java.util.List;

import com.rgei.kpi.dashboard.entities.KpiTypeEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;
import com.rgei.kpi.dashboard.response.model.KpiTypeResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLinesResponse;

public class KPICategoryConverter {
	
	//no-arg construtor
	private KPICategoryConverter() {
	}
	
	public static List<KpiTypeResponse> covertToResponse(List<KpiTypeEntity> kpiTypeEntities) {
		List<KpiTypeResponse> kpiTypeResponseList = null;
		KpiTypeResponse kpiTypeResponse = null;
		if(!kpiTypeEntities.isEmpty()) {
			kpiTypeResponseList = new ArrayList<>();
			for(KpiTypeEntity entity:kpiTypeEntities) {
				kpiTypeResponse = new KpiTypeResponse();
				kpiTypeResponse.setKpiTypeId(entity.getKpiTypeId());
				kpiTypeResponse.setKpiTypeName(entity.getKpiTypeName());
				kpiTypeResponseList.add(kpiTypeResponse);
			}
		}
		return kpiTypeResponseList;
	}
	
	public static List<ProcessLinesResponse> covertToProcessLineResponse(List<ProcessLineEntity> processLinesEntities) {
		List<ProcessLinesResponse> processLines = null;
		ProcessLinesResponse processLinesResponse = null;
		if(!processLinesEntities.isEmpty()) {
			processLines = new ArrayList<>();
			for(ProcessLineEntity entity:processLinesEntities) {
				processLinesResponse = new ProcessLinesResponse();
				processLinesResponse.setProcessLineId(entity.getProcessLineId());
				processLinesResponse.setProcessLineName(entity.getProcessLineCode());
				processLines.add(processLinesResponse);
			}
		}
		return processLines;
	}

}
