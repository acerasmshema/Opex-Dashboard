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
 * The persistent class for the kpi_category database table.
 * 
 */
@Entity
@Table(name="kpi_category")
public class KpiCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="kpi_category_id")
	private Integer kpiCategoryId;

	private Boolean active;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="kpi_category_code")
	private String kpiCategoryCode;

	@Column(name="kpi_category_name")
	private String kpiCategoryName;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to DailyKpiPulp
	@OneToMany(mappedBy="kpiCategory")
	private List<DailyKpiPulpEntity> dailyKpiPulps;

	//bi-directional many-to-one association to KpiType
	@OneToMany(mappedBy="kpiCategory")
	private List<KpiTypeEntity> kpiTypes;

	//bi-directional many-to-one association to MillBuKpiCategory
	@OneToMany(mappedBy="kpiCategory")
	private List<MillBuKpiCategoryEntity> millBuKpiCategories;

	public Integer getKpiCategoryId() {
		return this.kpiCategoryId;
	}

	public void setKpiCategoryId(Integer kpiCategoryId) {
		this.kpiCategoryId = kpiCategoryId;
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

	public String getKpiCategoryCode() {
		return this.kpiCategoryCode;
	}

	public void setKpiCategoryCode(String kpiCategoryCode) {
		this.kpiCategoryCode = kpiCategoryCode;
	}

	public String getKpiCategoryName() {
		return this.kpiCategoryName;
	}

	public void setKpiCategoryName(String kpiCategoryName) {
		this.kpiCategoryName = kpiCategoryName;
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
		dailyKpiPulp.setKpiCategory(this);

		return dailyKpiPulp;
	}

	public DailyKpiPulpEntity removeDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().remove(dailyKpiPulp);
		dailyKpiPulp.setKpiCategory(null);

		return dailyKpiPulp;
	}

	public List<KpiTypeEntity> getKpiTypes() {
		return this.kpiTypes;
	}

	public void setKpiTypes(List<KpiTypeEntity> kpiTypes) {
		this.kpiTypes = kpiTypes;
	}

	public KpiTypeEntity addKpiType(KpiTypeEntity kpiType) {
		getKpiTypes().add(kpiType);
		kpiType.setKpiCategory(this);

		return kpiType;
	}

	public KpiTypeEntity removeKpiType(KpiTypeEntity kpiType) {
		getKpiTypes().remove(kpiType);
		kpiType.setKpiCategory(null);

		return kpiType;
	}

	public List<MillBuKpiCategoryEntity> getMillBuKpiCategories() {
		return this.millBuKpiCategories;
	}

	public void setMillBuKpiCategories(List<MillBuKpiCategoryEntity> millBuKpiCategories) {
		this.millBuKpiCategories = millBuKpiCategories;
	}

	public MillBuKpiCategoryEntity addMillBuKpiCategory(MillBuKpiCategoryEntity millBuKpiCategory) {
		getMillBuKpiCategories().add(millBuKpiCategory);
		millBuKpiCategory.setKpiCategory(this);

		return millBuKpiCategory;
	}

	public MillBuKpiCategoryEntity removeMillBuKpiCategory(MillBuKpiCategoryEntity millBuKpiCategory) {
		getMillBuKpiCategories().remove(millBuKpiCategory);
		millBuKpiCategory.setKpiCategory(null);

		return millBuKpiCategory;
	}

}
