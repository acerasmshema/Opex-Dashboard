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


/**
 * The persistent class for the mill_bu_kpi_category database table.
 * 
 */
@Entity
@Table(name="mill_bu_kpi_category")
public class MillBuKpiCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="mill_bu_kpi_category_id")
	private Integer millBuKpiCategoryId;

	private Boolean active;

	@Column(name="annual_target")
	private String annualTarget;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="daily_target")
	private Integer dailyTarget;
	
	@Column(name="target_days")
	private Integer targetDays;

	@Column(name="max_target")
	private Integer maxTarget;

	@Column(name="min_target")
	private Integer minTarget;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@Column(name="range_value")
	private String rangeValue;
	
	@Column(name="color_range")
	private String colorRange;

	//bi-directional many-to-one association to BusinessUnit
	@ManyToOne
	@JoinColumn(name="business_unit_id")
	private BusinessUnitEntity businessUnit;

	//bi-directional many-to-one association to KpiCategory
	@ManyToOne
	@JoinColumn(name="kpi_category_id")
	private KpiCategoryEntity kpiCategory;

	//bi-directional many-to-one association to Mill
	@ManyToOne
	@JoinColumn(name="mill_id")
	private MillEntity mill;

	public Integer getMillBuKpiCategoryId() {
		return this.millBuKpiCategoryId;
	}

	public void setMillBuKpiCategoryId(Integer millBuKpiCategoryId) {
		this.millBuKpiCategoryId = millBuKpiCategoryId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAnnualTarget() {
		return this.annualTarget;
	}

	public void setAnnualTarget(String annualTarget) {
		this.annualTarget = annualTarget;
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

	public Integer getDailyTarget() {
		return this.dailyTarget;
	}

	public void setDailyTarget(Integer dailyTarget) {
		this.dailyTarget = dailyTarget;
	}

	public Integer getMaxTarget() {
		return this.maxTarget;
	}

	public void setMaxTarget(Integer maxTarget) {
		this.maxTarget = maxTarget;
	}

	public Integer getMinTarget() {
		return this.minTarget;
	}

	public void setMinTarget(Integer minTarget) {
		this.minTarget = minTarget;
	}

	public Integer getTargetDays() {
		return targetDays;
	}

	public void setTargetDays(Integer targetDays) {
		this.targetDays = targetDays;
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

	public KpiCategoryEntity getKpiCategory() {
		return this.kpiCategory;
	}

	public void setKpiCategory(KpiCategoryEntity kpiCategory) {
		this.kpiCategory = kpiCategory;
	}

	public MillEntity getMill() {
		return this.mill;
	}

	public void setMill(MillEntity mill) {
		this.mill = mill;
	}

	public String getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(String rangeValue) {
		this.rangeValue = rangeValue;
	}

	public String getColorRange() {
		return colorRange;
	}

	public void setColorRange(String colorRange) {
		this.colorRange = colorRange;
	}
}
