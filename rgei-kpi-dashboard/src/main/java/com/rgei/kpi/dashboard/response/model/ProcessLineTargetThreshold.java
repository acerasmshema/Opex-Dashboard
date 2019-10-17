package com.rgei.kpi.dashboard.response.model;

public class ProcessLineTargetThreshold {

	private String processLineTargetThreshold;
	private BuTypeResponse buType;
	private MillDetail mill;
	private ProcessLine processLine;
	private Double threshold;
	private Double minimum;
	private Double maximum;
	private String startDate;
	private String endDate;
	private Boolean active;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;

	public String getProcessLineTargetThreshold() {
		return processLineTargetThreshold;
	}

	public void setProcessLineTargetThreshold(String processLineTargetThreshold) {
		this.processLineTargetThreshold = processLineTargetThreshold;
	}

	public MillDetail getMill() {
		return mill;
	}

	public void setMill(MillDetail mill) {
		this.mill = mill;
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