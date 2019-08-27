/*******************************************************************************
 * Copyright (c) 2019 Ace Resource Advisory Services Sdn. Bhd., Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Ace Resource Advisory Services Sdn. Bhd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Ace Resource Advisory Services Sdn. Bhd.
 * 
 * Ace Resource Advisory Services Sdn. Bhd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Ace Resource Advisory Services Sdn. Bhd. SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 ******************************************************************************/
package com.rgei.kpi.dashboard.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the kpi database table.
 * 
 */
@Entity
@Table(name="kpi")
public class KpiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="kpi_id")
	private Integer kpiId;

	private Boolean active;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="kpi_name")
	private String kpiName;

	@Column(name="kpi_unit")
	private String kpiUnit;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	


	//bi-directional many-to-one association to DailyKpiPulp
	@OneToMany(mappedBy="kpi")
	private List<DailyKpiPulpEntity> dailyKpiPulps;

	//bi-directional many-to-one association to KpiType
	@ManyToOne
	@JoinColumn(name="kpi_type_id")
	private KpiTypeEntity kpiType;

	//bi-directional many-to-one association to KpiAnnotation
	@OneToMany(mappedBy="kpi")
	private List<KpiAnnotationEntity> kpiAnnotations;

	//bi-directional many-to-one association to KpiProcessLine
	@OneToMany(mappedBy="kpi", fetch = FetchType.EAGER)
	private List<KpiProcessLineEntity> kpiProcessLines;

	public Integer getKpiId() {
		return this.kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getKpiName() {
		return this.kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiUnit() {
		return this.kpiUnit;
	}

	public void setKpiUnit(String kpiUnit) {
		this.kpiUnit = kpiUnit;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	



	public List<DailyKpiPulpEntity> getDailyKpiPulps() {
		return this.dailyKpiPulps;
	}

	public void setDailyKpiPulps(List<DailyKpiPulpEntity> dailyKpiPulps) {
		this.dailyKpiPulps = dailyKpiPulps;
	}

	public DailyKpiPulpEntity addDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().add(dailyKpiPulp);
		dailyKpiPulp.setKpi(this);

		return dailyKpiPulp;
	}

	public DailyKpiPulpEntity removeDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().remove(dailyKpiPulp);
		dailyKpiPulp.setKpi(null);

		return dailyKpiPulp;
	}

	public KpiTypeEntity getKpiType() {
		return this.kpiType;
	}

	public void setKpiType(KpiTypeEntity kpiType) {
		this.kpiType = kpiType;
	}

	public List<KpiAnnotationEntity> getKpiAnnotations() {
		return this.kpiAnnotations;
	}

	public void setKpiAnnotations(List<KpiAnnotationEntity> kpiAnnotations) {
		this.kpiAnnotations = kpiAnnotations;
	}

	public KpiAnnotationEntity addKpiAnnotation(KpiAnnotationEntity kpiAnnotation) {
		getKpiAnnotations().add(kpiAnnotation);
		kpiAnnotation.setKpi(this);

		return kpiAnnotation;
	}

	public KpiAnnotationEntity removeKpiAnnotation(KpiAnnotationEntity kpiAnnotation) {
		getKpiAnnotations().remove(kpiAnnotation);
		kpiAnnotation.setKpi(null);

		return kpiAnnotation;
	}

	public List<KpiProcessLineEntity> getKpiProcessLines() {
		return this.kpiProcessLines;
	}

	public void setKpiProcessLines(List<KpiProcessLineEntity> kpiProcessLines) {
		this.kpiProcessLines = kpiProcessLines;
	}

	public KpiProcessLineEntity addKpiProcessLine(KpiProcessLineEntity kpiProcessLine) {
		getKpiProcessLines().add(kpiProcessLine);
		kpiProcessLine.setKpi(this);

		return kpiProcessLine;
	}

	public KpiProcessLineEntity removeKpiProcessLine(KpiProcessLineEntity kpiProcessLine) {
		getKpiProcessLines().remove(kpiProcessLine);
		kpiProcessLine.setKpi(null);

		return kpiProcessLine;
	}

}
