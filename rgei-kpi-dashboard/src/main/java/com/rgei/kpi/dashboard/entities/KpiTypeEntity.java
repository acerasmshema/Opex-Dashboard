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
 * The persistent class for the kpi_type database table.
 * 
 */
@Entity
@Table(name="kpi_type")
public class KpiTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="kpi_type_id")
	private Integer kpiTypeId;

	private Boolean active;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="kpi_type_code")
	private String kpiTypeCode;

	@Column(name="kpi_type_name")
	private String kpiTypeName;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@Column(name="kpi_order")
	private Integer kpiOrder;
	
	//bi-directional many-to-one association to Kpi
	@OneToMany(mappedBy="kpiType", fetch = FetchType.EAGER)
	private List<KpiEntity> kpis;

	//bi-directional many-to-one association to KpiCategory
	@ManyToOne
	@JoinColumn(name="kpi_category_id")
	private KpiCategoryEntity kpiCategory;

	public Integer getKpiTypeId() {
		return this.kpiTypeId;
	}

	public void setKpiTypeId(Integer kpiTypeId) {
		this.kpiTypeId = kpiTypeId;
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

	public String getKpiTypeCode() {
		return this.kpiTypeCode;
	}

	public void setKpiTypeCode(String kpiTypeCode) {
		this.kpiTypeCode = kpiTypeCode;
	}

	public String getKpiTypeName() {
		return this.kpiTypeName;
	}

	public void setKpiTypeName(String kpiTypeName) {
		this.kpiTypeName = kpiTypeName;
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
	
	public Integer getKpiOrder() {
		return kpiOrder;
	}

	public void setKpiOrder(Integer kpiOrder) {
		this.kpiOrder = kpiOrder;
	}

	public List<KpiEntity> getKpis() {
		return this.kpis;
	}

	public void setKpis(List<KpiEntity> kpis) {
		this.kpis = kpis;
	}

	public KpiEntity addKpi(KpiEntity kpi) {
		getKpis().add(kpi);
		kpi.setKpiType(this);

		return kpi;
	}

	public KpiEntity removeKpi(KpiEntity kpi) {
		getKpis().remove(kpi);
		kpi.setKpiType(null);

		return kpi;
	}

	public KpiCategoryEntity getKpiCategory() {
		return this.kpiCategory;
	}

	public void setKpiCategory(KpiCategoryEntity kpiCategory) {
		this.kpiCategory = kpiCategory;
	}

}
