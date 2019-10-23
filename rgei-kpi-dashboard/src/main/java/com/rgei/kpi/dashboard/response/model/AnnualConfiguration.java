package com.rgei.kpi.dashboard.response.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AnnualConfiguration {
	private Integer annualConfigurationId;
	private BuTypeResponse buType;
	private Integer kpiId;
	private Integer millId;
	private Integer year;
	@JsonIgnore
	private Double threshold;
	private Integer workingDays;
	private String annualTarget;
	private Boolean isDefault;
	private Boolean active;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	
	public Integer getAnnualConfigurationId() {
		return annualConfigurationId;
	}
	public void setAnnualConfigurationId(Integer annualConfigurationId) {
		this.annualConfigurationId = annualConfigurationId;
	}
	public BuTypeResponse getBuType() {
		return buType;
	}
	public void setBuType(BuTypeResponse buType) {
		this.buType = buType;
	}
	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	public Integer getMillId() {
		return millId;
	}
	public void setMillId(Integer millId) {
		this.millId = millId;
	}
	public Double getThreshold() {
		return threshold;
	}
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
	public Integer getWorkingDays() {
		return workingDays;
	}
	public void setWorkingDays(Integer workingDays) {
		this.workingDays = workingDays;
	}
	public String getAnnualTarget() {
		return annualTarget;
	}
	public void setAnnualTarget(String annualTarget) {
		this.annualTarget = annualTarget;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
}
