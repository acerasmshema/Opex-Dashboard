package com.rgei.kpi.dashboard.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "annual_configuration")
public class AnnualConfigurationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "annual_configuration_id")
	private Integer annualConfigurationId;

	@Column(name = "bu_type_id")
	private Integer buTypeId;

	@Column(name = "mill_id")
	private Integer millId;

	@Column(name = "kpi_id")
	private Integer kpiId;

	@Column(name = "threshold")
	private Double threshold;

	@Column(name = "year")
	private Integer year;

	@Column(name = "working_days")
	private Integer workingDays;

	@Column(name = "annual_target")
	private String annualTarget;

	@Column(name = "is_default")
	private Boolean isDefault;

	private Boolean active;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bu_type_id", referencedColumnName = "business_unit_type_id", insertable = false, updatable = false)
	private BusinessUnitTypeEntity buType;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mill_id", referencedColumnName = "mill_id", insertable = false, updatable = false)
	private MillEntity mill;

	@JsonIgnore
	@OneToMany(mappedBy = "kpiId")
	private List<KpiEntity> kpi;

	public Integer getAnnualConfigurationId() {
		return annualConfigurationId;
	}

	public void setAnnualConfigurationId(Integer annualConfigurationId) {
		this.annualConfigurationId = annualConfigurationId;
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

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public BusinessUnitTypeEntity getBuType() {
		return buType;
	}

	public void setBuType(BusinessUnitTypeEntity buType) {
		this.buType = buType;
	}

	public MillEntity getMill() {
		return mill;
	}

	public void setMill(MillEntity mill) {
		this.mill = mill;
	}

	public List<KpiEntity> getKpi() {
		return kpi;
	}

	public void setKpi(List<KpiEntity> kpi) {
		this.kpi = kpi;
	}

}
