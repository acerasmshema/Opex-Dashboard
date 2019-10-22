package com.rgei.kpi.dashboard.response.model;

public class ProcessLineDetailsResponse {
	
	private Integer processLineId;
	private String processLineCode;
	private String processLineName;
	private String legendColor;
	
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
	public String getLegendColor() {
		return legendColor;
	}
	public void setLegendColor(String legendColor) {
		this.legendColor = legendColor;
	}
}
