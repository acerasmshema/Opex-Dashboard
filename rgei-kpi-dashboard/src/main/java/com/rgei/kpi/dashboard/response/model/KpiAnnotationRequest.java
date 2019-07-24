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

public class KpiAnnotationRequest {

	private String millId;
	private String kpiId;
	private String buTypeId;
	private String userLoginId;
	private String annotationDate;
	private String description;
	private String processLines;
	
	public String getMillId() {
		return millId;
	}
	public void setMillId(String millId) {
		this.millId = millId;
	}
	public String getKpiId() {
		return kpiId;
	}
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}
	public String getBuTypeId() {
		return buTypeId;
	}
	public void setBuTypeId(String buTypeId) {
		this.buTypeId = buTypeId;
	}
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getAnnotationDate() {
		return annotationDate;
	}
	public void setAnnotationDate(String annotationDate) {
		this.annotationDate = annotationDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProcessLines() {
		return processLines;
	}
	public void setProcessLines(String processLines) {
		this.processLines = processLines;
	}
}
