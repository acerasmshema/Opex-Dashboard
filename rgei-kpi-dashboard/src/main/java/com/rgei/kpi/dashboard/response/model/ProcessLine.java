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

import java.math.BigDecimal;
import java.util.Map;

public class ProcessLine {
	
private Integer processLineId;
	
	private String processLineCode;

	private String processLineName;
	
	private Integer millId;
	
	private BigDecimal maxTarget;

	private BigDecimal minTarget;
	
	private String rangeValue;
	
	private String colorRange;
	
	private BigDecimal dailyLineTarget;
	
	private Map<String, BigDecimal> thresholdMap;

	public Integer getProcessLineId() {
		return processLineId;
	}

	public void setProcessLineId(Integer processLineId) {
		this.processLineId = processLineId;
	}

	public String getProcessLineCode() {
		return processLineCode;
	}

	public void setProcessLineCode(String processLineCode) {
		this.processLineCode = processLineCode;
	}

	public String getProcessLineName() {
		return processLineName;
	}

	public void setProcessLineName(String processLineName) {
		this.processLineName = processLineName;
	}

	public Integer getMillId() {
		return millId;
	}

	public void setMillId(Integer millId) {
		this.millId = millId;
	}

	public BigDecimal getMaxTarget() {
		return maxTarget;
	}

	public void setMaxTarget(BigDecimal maxTarget) {
		this.maxTarget = maxTarget;
	}

	public BigDecimal getMinTarget() {
		return minTarget;
	}

	public void setMinTarget(BigDecimal minTarget) {
		this.minTarget = minTarget;
	}

	public String getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(String rangeValue) {
		this.rangeValue = rangeValue;
	}

	public String getColorRange() {
		return colorRange;
	}

	public void setColorRange(String colorRange) {
		this.colorRange = colorRange;
	}
	
	public BigDecimal getDailyLineTarget() {
		return dailyLineTarget;
	}

	public void setDailyLineTarget(BigDecimal dailyLineTarget) {
		this.dailyLineTarget = dailyLineTarget;
	}

	public Map<String, BigDecimal> getThresholdMap() {
		return thresholdMap;
	}

	public void setThresholdMap(Map<String, BigDecimal> thresholdMap) {
		this.thresholdMap = thresholdMap;
	}
}
