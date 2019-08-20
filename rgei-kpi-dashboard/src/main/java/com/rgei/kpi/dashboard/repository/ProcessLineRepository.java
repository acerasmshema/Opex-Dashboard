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
package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.MillEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineEntity;

@Repository
public interface ProcessLineRepository extends JpaRepository<ProcessLineEntity, Integer>{
	
	public List<ProcessLineEntity> findByProcessLineNameIn(@Param("processLines") List<String> processLines);
	
	public List<ProcessLineEntity> findAllByMillOrderByProcessLineIdAsc(MillEntity millId);
	
	@Query("Select PL from  ProcessLineEntity PL inner join KpiProcessLineEntity KPL on PL.processLineId=KPL.processLine.processLineId "
			+ "where KPL.kpi.kpiId = :kpiId and PL.active=:status and PL.mill.millId = :millId order by PL.processLineId")
	List<ProcessLineEntity> findByKpiId(@Param("kpiId") Integer kpiId,@Param("status") Boolean status, @Param("millId") Integer millId);
	
	@Query("Select PL from  ProcessLineEntity PL where PL.mill.millId = :millId And PL.active = :status order by PL.processLineId")
	List<ProcessLineEntity> getPrcessLineByLocation(@Param("millId") Integer millId, @Param("status") Boolean status);
}
