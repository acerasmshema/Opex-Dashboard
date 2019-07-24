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
 * The persistent class for the business_unit_type database table.
 * 
 */
@Entity
@Table(name="business_unit_type")
public class BusinessUnitTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="business_unit_type_id")
	private Integer businessUnitTypeId;

	private Boolean active;

	@Column(name="business_unit_type_code")
	private String businessUnitTypeCode;

	@Column(name="business_unit_type_name")
	private String businessUnitTypeName;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to BusinessUnit
	@ManyToOne
	@JoinColumn(name="business_unit_id")
	private BusinessUnitEntity businessUnit;

	//bi-directional many-to-one association to DailyKpiPulp
	@OneToMany(mappedBy="businessUnitType")
	private List<DailyKpiPulpEntity> dailyKpiPulps;

	//bi-directional many-to-one association to KpiAnnotation
	@OneToMany(mappedBy="businessUnitType")
	private List<KpiAnnotationEntity> kpiAnnotations;

	public Integer getBusinessUnitTypeId() {
		return this.businessUnitTypeId;
	}

	public void setBusinessUnitTypeId(Integer businessUnitTypeId) {
		this.businessUnitTypeId = businessUnitTypeId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getBusinessUnitTypeCode() {
		return this.businessUnitTypeCode;
	}

	public void setBusinessUnitTypeCode(String businessUnitTypeCode) {
		this.businessUnitTypeCode = businessUnitTypeCode;
	}

	public String getBusinessUnitTypeName() {
		return this.businessUnitTypeName;
	}

	public void setBusinessUnitTypeName(String businessUnitTypeName) {
		this.businessUnitTypeName = businessUnitTypeName;
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

	public BusinessUnitEntity getBusinessUnit() {
		return this.businessUnit;
	}

	public void setBusinessUnit(BusinessUnitEntity businessUnit) {
		this.businessUnit = businessUnit;
	}

	public List<DailyKpiPulpEntity> getDailyKpiPulps() {
		return this.dailyKpiPulps;
	}

	public void setDailyKpiPulps(List<DailyKpiPulpEntity> dailyKpiPulps) {
		this.dailyKpiPulps = dailyKpiPulps;
	}

	public DailyKpiPulpEntity addDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().add(dailyKpiPulp);
		dailyKpiPulp.setBusinessUnitType(this);

		return dailyKpiPulp;
	}

	public DailyKpiPulpEntity removeDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().remove(dailyKpiPulp);
		dailyKpiPulp.setBusinessUnitType(null);

		return dailyKpiPulp;
	}

	public List<KpiAnnotationEntity> getKpiAnnotations() {
		return this.kpiAnnotations;
	}

	public void setKpiAnnotations(List<KpiAnnotationEntity> kpiAnnotations) {
		this.kpiAnnotations = kpiAnnotations;
	}

	public KpiAnnotationEntity addKpiAnnotation(KpiAnnotationEntity kpiAnnotation) {
		getKpiAnnotations().add(kpiAnnotation);
		kpiAnnotation.setBusinessUnitType(this);

		return kpiAnnotation;
	}

	public KpiAnnotationEntity removeKpiAnnotation(KpiAnnotationEntity kpiAnnotation) {
		getKpiAnnotations().remove(kpiAnnotation);
		kpiAnnotation.setBusinessUnitType(null);

		return kpiAnnotation;
	}

}
