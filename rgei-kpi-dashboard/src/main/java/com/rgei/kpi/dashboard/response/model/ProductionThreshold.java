package com.rgei.kpi.dashboard.response.model;

public class ProductionThreshold {

	private String productionThresholdId;
	private BuTypeResponse buType;
	private Double threshold;
	private Double maximum;
	private String startDate;
	private String endDate;
	private Boolean active;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;

	public String getProductionThresholdId() {
		return productionThresholdId;
	}

	public void setProductionThresholdId(String productionThresholdId) {
		this.productionThresholdId = productionThresholdId;
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
