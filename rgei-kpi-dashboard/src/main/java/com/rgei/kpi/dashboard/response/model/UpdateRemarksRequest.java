package com.rgei.kpi.dashboard.response.model;

import java.util.List;

public class UpdateRemarksRequest {
	
	private List<String> ids;
	
	private String remarks;
	
	private Long updatedBy;
	
	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

}
