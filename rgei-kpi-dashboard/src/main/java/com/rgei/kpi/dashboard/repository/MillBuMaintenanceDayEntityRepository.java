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

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.MillBuMaintenanceDayEntity;

@Repository
public interface MillBuMaintenanceDayEntityRepository extends CrudRepository<MillBuMaintenanceDayEntity, Long>{
	
	
	@Query("Select d from MillBuMaintenanceDayEntity d where d.mill.millId = :millId and  d.buId = :buId  and d.active=true order by d.maintenanceDays")
	public List<MillBuMaintenanceDayEntity> findByMillAndbuId(@Param("millId")Integer  millId, @Param("buId")Integer buId);
	
	public List<MillBuMaintenanceDayEntity> findBymillBuMdIdIn(List<Long> ids);
	
	@Query("Select d from MillBuMaintenanceDayEntity d where d.active=true AND date(d.maintenanceDays) IN :maintenanceDays")
	public List<MillBuMaintenanceDayEntity> findByMaintenanceDays(@Param("maintenanceDays") List<Date> maintenanceDays);

}
