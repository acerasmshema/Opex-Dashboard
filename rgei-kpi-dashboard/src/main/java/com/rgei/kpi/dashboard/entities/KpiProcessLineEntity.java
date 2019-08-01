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
 * The persistent class for the kpi_process_line database table.
 * 
 */
@Entity
@Table(name="kpi_process_line")
public class KpiProcessLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="kpi_process_line_id")
	private Integer kpiProcessLineId;

	private Boolean active;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_date")
	private Timestamp updatedDate;
	
	@Column(name="target")
	private String target;
	
	// bi-directional many-to-one association to Mill
	@ManyToOne
	@JoinColumn(name = "mill_id")
	private MillEntity mill;
	
	//bi-directional many-to-one association to Kpi
	@ManyToOne
	@JoinColumn(name="kpi_id")
	private KpiEntity kpi;

	//bi-directional many-to-one association to ProcessLine
	@ManyToOne
	@JoinColumn(name="process_line_id")
	private ProcessLineEntity processLine;

	public Integer getKpiProcessLineId() {
		return this.kpiProcessLineId;
	}

	public void setKpiProcessLineId(Integer kpiProcessLineId) {
		this.kpiProcessLineId = kpiProcessLineId;
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

	public KpiEntity getKpi() {
		return this.kpi;
	}

	public void setKpi(KpiEntity kpi) {
		this.kpi = kpi;
	}

	public ProcessLineEntity getProcessLine() {
		return this.processLine;
	}

	public void setProcessLine(ProcessLineEntity processLine) {
		this.processLine = processLine;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public MillEntity getMill() {
		return mill;
	}

	public void setMill(MillEntity mill) {
		this.mill = mill;
	}

}
