package com.rgei.kpi.dashboard.response.model;

import java.util.List;

public class KpiTypeExtendedResponse extends KpiTypeResponse{
	
	private List<KpiTypeDetails> kpiList;

	public List<KpiTypeDetails> getKpiList() {
		return kpiList;
	}
	public void setKpiList(List<KpiTypeDetails> kpiList) {
		this.kpiList = kpiList;
	}
}
