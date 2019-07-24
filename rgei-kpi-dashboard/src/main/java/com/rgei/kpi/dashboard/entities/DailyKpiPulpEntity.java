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
 * The persistent class for the daily_kpi_pulp database table.
 * 
 */
@Entity
@Table(name="daily_kpi_pulp")
public class DailyKpiPulpEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="daily_kpi_pulp_id")
	private Long dailyKpiPulpId;

	private Timestamp datetime;

	@Column(name="process_line_1")
	private double processLine1;

	@Column(name="process_line_10")
	private double processLine10;

	@Column(name="process_line_11")
	private double processLine11;

	@Column(name="process_line_12")
	private double processLine12;

	@Column(name="process_line_13")
	private double processLine13;

	@Column(name="process_line_14")
	private double processLine14;

	@Column(name="process_line_15")
	private double processLine15;

	@Column(name="process_line_2")
	private double processLine2;

	@Column(name="process_line_3")
	private double processLine3;

	@Column(name="process_line_4")
	private double processLine4;

	@Column(name="process_line_5")
	private double processLine5;

	@Column(name="process_line_6")
	private double processLine6;

	@Column(name="process_line_7")
	private double processLine7;

	@Column(name="process_line_8")
	private double processLine8;

	@Column(name="process_line_9")
	private double processLine9;

	//bi-directional many-to-one association to BusinessUnit
	@ManyToOne
	@JoinColumn(name="bu_id")
	private BusinessUnitEntity businessUnit;

	//bi-directional many-to-one association to BusinessUnitType
	@ManyToOne
	@JoinColumn(name="bu_type_id")
	private BusinessUnitTypeEntity businessUnitType;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country_id")
	private CountryEntity country;

	//bi-directional many-to-one association to Kpi
	@ManyToOne
	@JoinColumn(name="kpi_id")
	private KpiEntity kpi;

	//bi-directional many-to-one association to KpiCategory
	@ManyToOne
	@JoinColumn(name="kpi_category_id")
	private KpiCategoryEntity kpiCategory;

	//bi-directional many-to-one association to Mill
	@ManyToOne
	@JoinColumn(name="mill_id")
	private MillEntity mill;

	public Long getDailyKpiPulpId() {
		return this.dailyKpiPulpId;
	}

	public void setDailyKpiPulpId(Long dailyKpiPulpId) {
		this.dailyKpiPulpId = dailyKpiPulpId;
	}

	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public double getProcessLine1() {
		return this.processLine1;
	}

	public void setProcessLine1(double processLine1) {
		this.processLine1 = processLine1;
	}

	public double getProcessLine10() {
		return this.processLine10;
	}

	public void setProcessLine10(double processLine10) {
		this.processLine10 = processLine10;
	}

	public double getProcessLine11() {
		return this.processLine11;
	}

	public void setProcessLine11(double processLine11) {
		this.processLine11 = processLine11;
	}

	public double getProcessLine12() {
		return this.processLine12;
	}

	public void setProcessLine12(double processLine12) {
		this.processLine12 = processLine12;
	}

	public double getProcessLine13() {
		return this.processLine13;
	}

	public void setProcessLine13(double processLine13) {
		this.processLine13 = processLine13;
	}

	public double getProcessLine14() {
		return this.processLine14;
	}

	public void setProcessLine14(double processLine14) {
		this.processLine14 = processLine14;
	}

	public double getProcessLine15() {
		return this.processLine15;
	}

	public void setProcessLine15(double processLine15) {
		this.processLine15 = processLine15;
	}

	public double getProcessLine2() {
		return this.processLine2;
	}

	public void setProcessLine2(double processLine2) {
		this.processLine2 = processLine2;
	}

	public double getProcessLine3() {
		return this.processLine3;
	}

	public void setProcessLine3(double processLine3) {
		this.processLine3 = processLine3;
	}

	public double getProcessLine4() {
		return this.processLine4;
	}

	public void setProcessLine4(double processLine4) {
		this.processLine4 = processLine4;
	}

	public double getProcessLine5() {
		return this.processLine5;
	}

	public void setProcessLine5(double processLine5) {
		this.processLine5 = processLine5;
	}

	public double getProcessLine6() {
		return this.processLine6;
	}

	public void setProcessLine6(double processLine6) {
		this.processLine6 = processLine6;
	}

	public double getProcessLine7() {
		return this.processLine7;
	}

	public void setProcessLine7(double processLine7) {
		this.processLine7 = processLine7;
	}

	public double getProcessLine8() {
		return this.processLine8;
	}

	public void setProcessLine8(double processLine8) {
		this.processLine8 = processLine8;
	}

	public double getProcessLine9() {
		return this.processLine9;
	}

	public void setProcessLine9(double processLine9) {
		this.processLine9 = processLine9;
	}

	public BusinessUnitEntity getBusinessUnit() {
		return this.businessUnit;
	}

	public void setBusinessUnit(BusinessUnitEntity businessUnit) {
		this.businessUnit = businessUnit;
	}

	public BusinessUnitTypeEntity getBusinessUnitType() {
		return this.businessUnitType;
	}

	public void setBusinessUnitType(BusinessUnitTypeEntity businessUnitType) {
		this.businessUnitType = businessUnitType;
	}

	public CountryEntity getCountry() {
		return this.country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public KpiEntity getKpi() {
		return this.kpi;
	}

	public void setKpi(KpiEntity kpi) {
		this.kpi = kpi;
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

}
