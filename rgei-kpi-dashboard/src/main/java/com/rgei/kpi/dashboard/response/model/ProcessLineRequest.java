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
package com.rgei.kpi.dashboard.response.model;

public class ProcessLineRequest {

	private Integer millId;
	private Integer buId; 
	private Integer buTypeId;
	private Integer kpiCategoryId; 
	private Integer kpiId;
	private String[] processLines;
	private String startDate;
	private String endDate;
	private Integer frequency;
	
	public Integer getMillId() {
		return millId;
	}
	public void setMillId(Integer millId) {
		this.millId = millId;
	}
	public Integer getBuId() {
		return buId;
	}
	public void setBuId(Integer buId) {
		this.buId = buId;
	}
	public Integer getBuTypeId() {
		return buTypeId;
	}
	public void setBuTypeId(Integer buTypeId) {
		this.buTypeId = buTypeId;
	}
	public Integer getKpiCategoryId() {
		return kpiCategoryId;
	}
	public void setKpiCategoryId(Integer kpiCategoryId) {
		this.kpiCategoryId = kpiCategoryId;
	}
	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	
	public String[] getProcessLines() {
		return processLines;
	}
	public void setProcessLines(String[] processLines) {
		this.processLines = processLines;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public String toString() {
		return "ProductionRequest [millId=" + millId + ", buId=" + buId + ", buTypeId="
				+ buTypeId + ", kpiCategoryId=" + kpiCategoryId + ", kpiId=" + kpiId + ", productionLines="
				+ processLines + ", startDate=" + startDate + ", endDate=" + endDate + ", frequency=" + frequency
				+ "]";
	}
	
}

