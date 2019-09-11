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
 * The persistent class for the country database table.
 * 
 */
@Entity
@Table(name="country")
public class CountryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="country_id")
	private Integer countryId;

	@Column(name="active")
	private Boolean active;

	@Column(name="country_code")
	private String countryCode;

	@Column(name="country_name")
	private String countryName;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	//bi-directional many-to-one association to DailyKpiPulp
	@OneToMany(mappedBy="country")
	private List<DailyKpiPulpEntity> dailyKpiPulps;

	//bi-directional many-to-one association to Mill
	@OneToMany(mappedBy="country")
	private List<MillEntity> mills;

	public Integer getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	public List<DailyKpiPulpEntity> getDailyKpiPulps() {
		return this.dailyKpiPulps;
	}

	public void setDailyKpiPulps(List<DailyKpiPulpEntity> dailyKpiPulps) {
		this.dailyKpiPulps = dailyKpiPulps;
	}

	public DailyKpiPulpEntity addDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().add(dailyKpiPulp);
		dailyKpiPulp.setCountry(this);

		return dailyKpiPulp;
	}

	public DailyKpiPulpEntity removeDailyKpiPulp(DailyKpiPulpEntity dailyKpiPulp) {
		getDailyKpiPulps().remove(dailyKpiPulp);
		dailyKpiPulp.setCountry(null);

		return dailyKpiPulp;
	}

	public List<MillEntity> getMills() {
		return this.mills;
	}

	public void setMills(List<MillEntity> mills) {
		this.mills = mills;
	}

	public MillEntity addMill(MillEntity mill) {
		getMills().add(mill);
		mill.setCountry(this);

		return mill;
	}

	public MillEntity removeMill(MillEntity mill) {
		getMills().remove(mill);
		mill.setCountry(null);

		return mill;
	}

}
