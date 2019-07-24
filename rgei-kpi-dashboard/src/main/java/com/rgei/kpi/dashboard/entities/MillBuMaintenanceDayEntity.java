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


@Entity
@Table(name="mill_bu_maintenance_days")
public class MillBuMaintenanceDayEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mill_bu_md_id")
	private Long millBuMdId;

	private Boolean active;

	@Column(name="bu_id")
	private Integer buId;

	@Column(name="created_date")
	private Date createdDate;

	@Column(name="maintenance_days")
	private Date maintenanceDays;

	private String remarks;

	@Column(name="updated_date")
	private Date updatedDate;

	//bi-directional many-to-one association to Mill
	@ManyToOne
	@JoinColumn(name="mill_id")
	private MillEntity mill;

	//bi-directional many-to-one association to RgeUser
	@ManyToOne
	@JoinColumn(name="created_by")
	private RgeUserEntity createdBy;

	//bi-directional many-to-one association to RgeUser
	@ManyToOne
	@JoinColumn(name="updated_by")
	private RgeUserEntity updatedBy;

	public Long getMillBuMdId() {
		return millBuMdId;
	}

	public void setMillBuMdId(Long millBuMdId) {
		this.millBuMdId = millBuMdId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getBuId() {
		return buId;
	}

	public void setBuId(Integer buId) {
		this.buId = buId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getMaintenanceDays() {
		return maintenanceDays;
	}

	public void setMaintenanceDays(Date maintenanceDays) {
		this.maintenanceDays = maintenanceDays;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public MillEntity getMill() {
		return mill;
	}

	public void setMill(MillEntity mill) {
		this.mill = mill;
	}

	public RgeUserEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(RgeUserEntity createdBy) {
		this.createdBy = createdBy;
	}

	public RgeUserEntity getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(RgeUserEntity updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
