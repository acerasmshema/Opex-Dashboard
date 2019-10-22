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
import com.rgei.kpi.dashboard.response.model.ProcessLineProjectedResponse;
import com.rgei.kpi.dashboard.response.model.ProcessLineRequest;
import com.rgei.kpi.dashboard.response.model.ResponseObject;
import com.rgei.kpi.dashboard.response.model.TargetProceessLine;

public interface ProcessLinePulpKpiService {

	public ResponseObject allProcessLines(ProcessLineRequest productionRequest);

	public List<DateRangeResponse> getDailyKpiPulpDataForBarChart(ProcessLineRequest productionRequestDTO);

	public List<DateRangeResponse> getDailyKpiPulpDataForAreaChart(ProcessLineRequest productionRequestDTO);

	public List<Map<String, Object>> getDownloadDataGrid(ProcessLineRequest productionRequestDTO);

	public List<DateRangeResponse> getLineChartData(ProcessLineRequest productionRequestDTO);

	public List<DateRangeResponse> getDailyTargetLineData(ProcessLineRequest productionRequestDTO);

	public TargetProceessLine getAnnualExtendedProcessLine(String millId, String buId, String kpiCategoryId,
			String kpiId);

	public TargetProceessLine getAnnualTargetProcessLine(String millId, String buId, String kpiCategoryId,
			String kpiId);

	public TargetProceessLine getAnnualTargetProcessLineV2(String millId, String buId, String kpiCategoryId,
			String kpiId);

	public Long getAnnualTargetValue(String millId, String buId, String kpiCategoryId, String kpiId);

	public ProcessLineProjectedResponse getProjectedProcessLineDetails(String millId, String buId, String kpiCategoryId,
			String kpiId, Boolean annualTargetRequired);

	public List<DateRangeResponse> getProcessLinesForFrequency(ProcessLineRequest productionRequestDTO);

	public List<List<Map<String, Object>>> getDataGridProcessLinesForFrequecy(ProcessLineRequest productionRequestDTO);
}
