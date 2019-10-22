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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the kpi_annotation database table.
 * 
 */
@Entity
@Table(name="kpi_annotation")
public class KpiAnnotationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="kpi_annotation_id")
	private Integer kpiAnnotationId;

	private Boolean active;

	@Column(name="annotation_date")
	private Date annotationDate;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Date createdDate;

	private String description;

	@Column(name="process_lines")
	private String processLines;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Date updatedDate;

	//bi-directional many-to-one association to BusinessUnitType
	@ManyToOne
	@JoinColumn(name="business_unit_type_id")
	private BusinessUnitTypeEntity businessUnitType;

	//bi-directional many-to-one association to Kpi
	@ManyToOne
	@JoinColumn(name="kpi_id")
	private KpiEntity kpi;

	//bi-directional many-to-one association to Mill
	@ManyToOne
	@JoinColumn(name="mill_id")
	private MillEntity mill;

	public Integer getKpiAnnotationId() {
		return this.kpiAnnotationId;
	}

	public void setKpiAnnotationId(Integer kpiAnnotationId) {
		this.kpiAnnotationId = kpiAnnotationId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getAnnotationDate() {
		return this.annotationDate;
	}

	public void setAnnotationDate(Date annotationDate) {
		this.annotationDate = annotationDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcessLines() {
		return this.processLines;
	}

	public void setProcessLines(String processLines) {
		this.processLines = processLines;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public BusinessUnitTypeEntity getBusinessUnitType() {
		return this.businessUnitType;
	}

	public void setBusinessUnitType(BusinessUnitTypeEntity businessUnitType) {
		this.businessUnitType = businessUnitType;
	}

	public KpiEntity getKpi() {
		return this.kpi;
	}

	public void setKpi(KpiEntity kpi) {
		this.kpi = kpi;
	}

	public MillEntity getMill() {
		return this.mill;
	}

	public void setMill(MillEntity mill) {
		this.mill = mill;
	}

}
