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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the business_unit database table.
 * 
 */
@Entity
@Table(name="business_unit")
public class BusinessUnitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="business_unit_id")
	private Integer businessUnitId;

	private Boolean active;

	@Column(name="business_unit_code")
	private String businessUnitCode;

	@Column(name="business_unit_name")
	private String businessUnitName;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to BusinessUnitType
	@JsonIgnore
	@OneToMany(mappedBy="businessUnit")
	private List<BusinessUnitTypeEntity> businessUnitTypes;

	//bi-directional many-to-one association to DailyKpiPulp
	@OneToMany(mappedBy="businessUnit")
	private List<DailyKpiPulpEntity> dailyKpiPulps;

	//bi-directional many-to-one association to MillBuKpiCategory
	@OneToMany(mappedBy="businessUnit")
	private List<MillBuKpiCategoryEntity> millBuKpiCategories;

	public Integer getBusinessUnitId() {
		return this.businessUnitId;
	}

	public void setBusinessUnitId(Integer businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getBusinessUnitCode() {
		return this.businessUnitCode;
	}

	public void setBusinessUnitCode(String businessUnitCode) {
		this.businessUnitCode = businessUnitCode;
	}

	public String getBusinessUnitName() {
		return this.businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
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

	public List<BusinessUnitTypeEntity> getBusinessUnitTypes() {
		return this.businessUnitTypes;
	}

	public void setBusinessUnitTypes(List<BusinessUnitTypeEntity> businessUnitTypes) {
		this.businessUnitTypes = businessUnitTypes;
	}

	public BusinessUnitTypeEntity addBusinessUnitType(BusinessUnitTypeEntity businessUnitType) {
		getBusinessUnitTypes().add(businessUnitType);
		businessUnitType.setBusinessUnit(this);

		return businessUnitType;
	}

	public BusinessUnitTypeEntity removeBusinessUnitType(BusinessUnitTypeEntity businessUnitType) {
		getBusinessUnitTypes().remove(businessUnitType);
		businessUnitType.setBusinessUnit(null);

		return businessUnitType;
	}

	public List<DailyKpiPulpEntity> getDailyKpiPulps() {
		return this.dailyKpiPulps;
	}

	public void setDailyKpiPulps(List<DailyKpiPulpEntity> dailyKpiPulps) {
		this.dailyKpiPulps = dailyKpiPulps;
	}

	public DailyKpiPulpEntity addDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().add(dailyKpiPulp);
		dailyKpiPulp.setBusinessUnit(this);

		return dailyKpiPulp;
	}

	public DailyKpiPulpEntity removeDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().remove(dailyKpiPulp);
		dailyKpiPulp.setBusinessUnit(null);

		return dailyKpiPulp;
	}

	public List<MillBuKpiCategoryEntity> getMillBuKpiCategories() {
		return this.millBuKpiCategories;
	}

	public void setMillBuKpiCategories(List<MillBuKpiCategoryEntity> millBuKpiCategories) {
		this.millBuKpiCategories = millBuKpiCategories;
	}

	public MillBuKpiCategoryEntity addMillBuKpiCategory(MillBuKpiCategoryEntity millBuKpiCategory) {
		getMillBuKpiCategories().add(millBuKpiCategory);
		millBuKpiCategory.setBusinessUnit(this);

		return millBuKpiCategory;
	}

	public MillBuKpiCategoryEntity removeMillBuKpiCategory(MillBuKpiCategoryEntity millBuKpiCategory) {
		getMillBuKpiCategories().remove(millBuKpiCategory);
		millBuKpiCategory.setBusinessUnit(null);

		return millBuKpiCategory;
	}

}
