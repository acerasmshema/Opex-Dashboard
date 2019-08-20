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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the process_line database table.
 * 
 */
@Entity
@Table(name="process_line")
public class ProcessLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="process_line_id")
	private Integer processLineId;

	private Boolean active;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="max_target")
	private BigDecimal maxTarget;

	@Column(name="min_target")
	private BigDecimal minTarget;

	@Column(name="process_line_code")
	private String processLineCode;

	@Column(name="process_line_name")
	private String processLineName;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@Column(name="range_value")
	private String rangeValue;
	
	@Column(name="color_range")
	private String colorRange;
	
	@Column(name="daily_line_target")
	private BigDecimal dailyLineTarget;

	//bi-directional many-to-one association to KpiProcessLine
	@OneToMany(mappedBy="processLine")
	private List<KpiProcessLineEntity> kpiProcessLines;

	//bi-directional many-to-one association to Mill
	@ManyToOne
	@JoinColumn(name="mill_id")
	private MillEntity mill;
	
	@Column(name="legend_color")
	private String legendColor;

	public Integer getProcessLineId() {
		return this.processLineId;
	}

	public void setProcessLineId(Integer processLineId) {
		this.processLineId = processLineId;
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

	public BigDecimal getMaxTarget() {
		return this.maxTarget;
	}

	public void setMaxTarget(BigDecimal maxTarget) {
		this.maxTarget = maxTarget;
	}

	public BigDecimal getMinTarget() {
		return this.minTarget;
	}

	public void setMinTarget(BigDecimal minTarget) {
		this.minTarget = minTarget;
	}

	public String getProcessLineCode() {
		return this.processLineCode;
	}

	public void setProcessLineCode(String processLineCode) {
		this.processLineCode = processLineCode;
	}

	public String getProcessLineName() {
		return this.processLineName;
	}

	public void setProcessLineName(String processLineName) {
		this.processLineName = processLineName;
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

	public List<KpiProcessLineEntity> getKpiProcessLines() {
		return this.kpiProcessLines;
	}

	public void setKpiProcessLines(List<KpiProcessLineEntity> kpiProcessLines) {
		this.kpiProcessLines = kpiProcessLines;
	}

	public KpiProcessLineEntity addKpiProcessLine(KpiProcessLineEntity kpiProcessLine) {
		getKpiProcessLines().add(kpiProcessLine);
		kpiProcessLine.setProcessLine(this);

		return kpiProcessLine;
	}

	public KpiProcessLineEntity removeKpiProcessLine(KpiProcessLineEntity kpiProcessLine) {
		getKpiProcessLines().remove(kpiProcessLine);
		kpiProcessLine.setProcessLine(null);

		return kpiProcessLine;
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

	public BigDecimal getDailyLineTarget() {
		return dailyLineTarget;
	}

	public void setDailyLineTarget(BigDecimal dailyLineTarget) {
		this.dailyLineTarget = dailyLineTarget;
	}

	public String getLegendColor() {
		return legendColor;
	}

	public void setLegendColor(String legendColor) {
		this.legendColor = legendColor;
	}
}
