package com.rgei.kpi.dashboard.response.model;

import java.util.List;

public class BenchmarkingReponse {
	
	private String kpiId;
	
	private String kpiName;
	
	private String kpiUnit;

	private List<DateRangeResponse> kpiData;

	public String getKpiId() {
		return kpiId;
	}

	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiUnit() {
		return kpiUnit;
	}

	public void setKpiUnit(String kpiUnit) {
		this.kpiUnit = kpiUnit;
	}

	public List<DateRangeResponse> getKpiData() {
		return kpiData;
	}

	public void setKpiData(List<DateRangeResponse> kpiData) {
		this.kpiData = kpiData;
	}
	
}
