package com.rgei.kpi.dashboard.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="business_unit_type_id")
	private BusinessUnitTypeEntity buType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="mill_id")
	private MillEntity millId;

	@Column(name = "kpi_id")
	private KpiEntity kpiId;

	@Column(name = "minimum")
	private Integer minimum;

	@Column(name = "maximum")
	private Integer maximum;

	@Column(name = "threshold")
	private Integer threshold;

	private Boolean active;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
	@JsonIgnore
	@OneToMany(mappedBy="kpiId")
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

	public MillEntity getMillId() {
		return millId;
	}

	public void setMillId(MillEntity millId) {
		this.millId = millId;
	}

	public KpiEntity getKpiId() {
		return kpiId;
	}

	public void setKpiId(KpiEntity kpiId) {
		this.kpiId = kpiId;
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

}
