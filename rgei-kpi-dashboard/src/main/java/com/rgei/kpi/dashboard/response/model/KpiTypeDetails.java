package com.rgei.kpi.dashboard.response.model;

public class KpiTypeDetails {
	
	private Integer kpiId;                                        
    private String kpiName;
    private String unit;
    private Integer kpiOrder;
    
	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Integer getKpiOrder() {
		return kpiOrder;
	}

	public void setKpiOrder(Integer kpiOrder) {
		this.kpiOrder = kpiOrder;
	}

}
