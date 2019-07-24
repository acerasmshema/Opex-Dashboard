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
 * The persistent class for the mill database table.
 * 
 */
@Entity
@Table(name="mill")
public class MillEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="mill_id")
	private Integer millId;

	private Boolean active;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="mill_code")
	private String millCode;

	@Column(name="mill_name")
	private String millName;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to DailyKpiPulp
	@OneToMany(mappedBy="mill")
	private List<DailyKpiPulpEntity> dailyKpiPulps;

	//bi-directional many-to-one association to KpiAnnotation
	@OneToMany(mappedBy="mill")
	private List<KpiAnnotationEntity> kpiAnnotations;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country_id")
	private CountryEntity country;

	//bi-directional many-to-one association to MillBuKpiCategory
	@OneToMany(mappedBy="mill")
	private List<MillBuKpiCategoryEntity> millBuKpiCategories;

	//bi-directional many-to-one association to ProcessLine
	@OneToMany(mappedBy="mill")
	private List<ProcessLineEntity> processLines;

	public Integer getMillId() {
		return this.millId;
	}

	public void setMillId(Integer millId) {
		this.millId = millId;
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

	public String getMillCode() {
		return this.millCode;
	}

	public void setMillCode(String millCode) {
		this.millCode = millCode;
	}

	public String getMillName() {
		return this.millName;
	}

	public void setMillName(String millName) {
		this.millName = millName;
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
		dailyKpiPulp.setMill(this);

		return dailyKpiPulp;
	}

	public DailyKpiPulpEntity removeDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().remove(dailyKpiPulp);
		dailyKpiPulp.setMill(null);

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
		kpiAnnotation.setMill(this);

		return kpiAnnotation;
	}

	public KpiAnnotationEntity removeKpiAnnotation(KpiAnnotationEntity kpiAnnotation) {
		getKpiAnnotations().remove(kpiAnnotation);
		kpiAnnotation.setMill(null);

		return kpiAnnotation;
	}

	public CountryEntity getCountry() {
		return this.country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public List<MillBuKpiCategoryEntity> getMillBuKpiCategories() {
		return this.millBuKpiCategories;
	}

	public void setMillBuKpiCategories(List<MillBuKpiCategoryEntity> millBuKpiCategories) {
		this.millBuKpiCategories = millBuKpiCategories;
	}

	public MillBuKpiCategoryEntity addMillBuKpiCategory(MillBuKpiCategoryEntity millBuKpiCategory) {
		getMillBuKpiCategories().add(millBuKpiCategory);
		millBuKpiCategory.setMill(this);

		return millBuKpiCategory;
	}

	public MillBuKpiCategoryEntity removeMillBuKpiCategory(MillBuKpiCategoryEntity millBuKpiCategory) {
		getMillBuKpiCategories().remove(millBuKpiCategory);
		millBuKpiCategory.setMill(null);

		return millBuKpiCategory;
	}

	public List<ProcessLineEntity> getProcessLines() {
		return this.processLines;
	}

	public void setProcessLines(List<ProcessLineEntity> processLines) {
		this.processLines = processLines;
	}

	public ProcessLineEntity addProcessLine(ProcessLineEntity processLine) {
		getProcessLines().add(processLine);
		processLine.setMill(this);

		return processLine;
	}

	public ProcessLineEntity removeProcessLine(ProcessLineEntity processLine) {
		getProcessLines().remove(processLine);
		processLine.setMill(null);

		return processLine;
	}

}
