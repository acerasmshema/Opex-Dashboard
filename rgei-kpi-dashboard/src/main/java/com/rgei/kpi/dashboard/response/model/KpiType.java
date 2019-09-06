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

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class KpiType {
	
	private Integer kpiTypeId;
	private Boolean active;
	private String createdBy;
	private Timestamp createdDate;
	private String kpiTypeCode;
	private String kpiName;
	private String updatedBy;
	private Timestamp updatedDate;
	private List<String> processLines;
	private Map<String,String> target;
	private Kpi kpi;
	
	public Integer getKpiTypeId() {
		return kpiTypeId;
	}
	public void setKpiTypeId(Integer kpiTypeId) {
		this.kpiTypeId = kpiTypeId;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getKpiTypeCode() {
		return kpiTypeCode;
	}
	public void setKpiTypeCode(String kpiTypeCode) {
		this.kpiTypeCode = kpiTypeCode;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public List<String> getProcessLines() {
		return processLines;
	}
	public void setProcessLines(List<String> processLines) {
		this.processLines = processLines;
	}
	public Map<String, String> getTarget() {
		return target;
	}
	public void setTarget(Map<String, String> target) {
		this.target = target;
	}
	public Kpi getKpi() {
		return kpi;
	}
	public void setKpi(Kpi kpi) {
		this.kpi = kpi;
	}
}
