package com.rgei.kpi.dashboard.response.model;

import java.util.List;

public class BenchmarkingRequest {
	
	private List<Integer> millId;
	
	private List<Integer> buTypeId;
	
	private Integer kpiId;
	
	private String startDate;
	
	private String endDate;
	
	private Integer frequency;

	public List<Integer> getMillId() {
		return millId;
	}

	public void setMillId(List<Integer> millId) {
		this.millId = millId;
	}

	public List<Integer> getBuTypeId() {
		return buTypeId;
	}

	public void setBuTypeId(List<Integer> buTypeId) {
		this.buTypeId = buTypeId;
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

	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	
}
