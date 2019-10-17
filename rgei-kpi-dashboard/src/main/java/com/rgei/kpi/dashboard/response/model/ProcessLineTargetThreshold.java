package com.rgei.kpi.dashboard.response.model;

public class ProcessLineTargetThreshold {

	private String processLineTargetThresholdId;
	private BuTypeResponse buType;
	private Integer millId;
	private ProcessLine processLine;
	private Integer kpiId;
	private Double threshold;
	private Double minimum;
	private Double maximum;
	private String startDate;
	private String endDate;
	private Boolean active;
	private Boolean isDefaultConfig;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;

	public String getProcessLineTargetThresholdId() {
		return processLineTargetThresholdId;
	}

	public void setProcessLineTargetThresholdId(String processLineTargetThreshold) {
		this.processLineTargetThresholdId = processLineTargetThreshold;
	}

	public Integer getMillId() {
		return millId;
	}

	public void setMillId(Integer millId) {
		this.millId = millId;
	}

	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public ProcessLine getProcessLine() {
		return processLine;
	}

	public void setProcessLine(ProcessLine processLine) {
		this.processLine = processLine;
	}

	public BuTypeResponse getBuType() {
		return buType;
	}

	public void setBuType(BuTypeResponse buType) {
		this.buType = buType;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public void setMaximum(Double maximum) {
		this.maximum = maximum;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Boolean getIsDefaultConfig() {
		return isDefaultConfig;
	}

	public void setIsDefaultConfig(Boolean isDefaultConfig) {
		this.isDefaultConfig = isDefaultConfig;
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

}
