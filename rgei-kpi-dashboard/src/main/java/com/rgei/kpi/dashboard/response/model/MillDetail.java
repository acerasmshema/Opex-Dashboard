package com.rgei.kpi.dashboard.response.model;

public class MillDetail {
	private String millId;
	private String millName;
	private String millCode;
	private String countryId;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private Boolean active;

	public String getMillId() {
		return millId;
	}

	public void setMillId(String millId) {
		this.millId = millId;
	}

	public String getMillName() {
		return millName;
	}

	public void setMillName(String millName) {
		this.millName = millName;
	}

	public String getMillCode() {
		return millCode;
	}

	public void setMillCode(String millCode) {
		this.millCode = millCode;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
