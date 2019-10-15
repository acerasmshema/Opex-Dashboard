package com.rgei.kpi.dashboard.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "kpi_configuration")
public class KpiConfigurationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "kpi_configuration_id")
	private Integer kpiConfigurationId;

	@Column(name = "bu_type_id")
	private Integer buTypeId;

	@Column(name = "mill_id")
	private Integer millId;

	@Column(name = "kpi_id")
	private Integer kpiId;

	@Column(name = "minimum")
	private Integer minimum;

	@Column(name = "maximum")
	private Integer maximum;

	@Column(name = "threshold")
	private Integer threshold;

	@Column(name = "start_date")
	private Timestamp startDate;

	@Column(name = "end_date")
	private Timestamp endDate;

	@Column(name = "is_default")
	private Boolean isDefault;

	private Boolean active;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Timestamp updatedDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bu_type_id", referencedColumnName = "business_unit_type_id", insertable = false, updatable = false)
	private BusinessUnitTypeEntity buType;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mill_id", referencedColumnName = "mill_id", insertable = false, updatable = false)
	private MillEntity mill;

	@JsonIgnore
	@OneToMany(mappedBy = "kpiId")
	private List<KpiEntity> kpi;

	public Integer getKpiConfigurationId() {
		return kpiConfigurationId;
	}

	public void setKpiConfigurationId(Integer kpiConfigurationId) {
		this.kpiConfigurationId = kpiConfigurationId;
	}

	public List<KpiEntity> getKpi() {
		return kpi;
	}

	public void setKpi(List<KpiEntity> kpi) {
		this.kpi = kpi;
	}

	public BusinessUnitTypeEntity getBuType() {
		return buType;
	}

	public void setBuType(BusinessUnitTypeEntity buType) {
		this.buType = buType;
	}

	public Integer getBuTypeId() {
		return buTypeId;
	}

	public void setBuTypeId(Integer buTypeId) {
		this.buTypeId = buTypeId;
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

	public MillEntity getMill() {
		return mill;
	}

	public void setMill(MillEntity mill) {
		this.mill = mill;
	}

	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public Integer getMaximum() {
		return maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
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

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
