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
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.DailyKpiPulpEntity;

@Repository
public interface DailyKpiPulpEntityRepository extends JpaRepository<DailyKpiPulpEntity, Long>{
	
	
	@Query("Select d from DailyKpiPulpEntity d where date(d.datetime) = :date and d.mill.millId = :millId and d.businessUnit.businessUnitId = :buId  and d.kpiCategory.kpiCategoryId = :kpiCategoryId and d.kpi.kpiId = :kpiId" )
	public List<DailyKpiPulpEntity> readByRequestedParameters(@Param("date")Date date ,@Param("millId")Integer millId,
			@Param("buId") Integer buId, @Param("kpiCategoryId") Integer kpiCategoryId, @Param("kpiId") Integer kpiId);
	
	
	@Query("Select d from DailyKpiPulpEntity d where date(d.datetime) BETWEEN :startDate and :endDate and d.mill.millId = :millId and d.businessUnit.businessUnitId = :buId  and d.kpiCategory.kpiCategoryId = :kpiCategoryId and d.kpi.kpiId = :kpiId order by date(d.datetime)" )
	public List<DailyKpiPulpEntity> readForDateRange(@Param("startDate")Date startDate ,@Param("endDate")Date endDate ,@Param("millId")Integer millId,
			@Param("buId") Integer buId, @Param("kpiCategoryId") Integer kpiCategoryId, @Param("kpiId") Integer kpiId);

	//Shubham Code 
	@Query(value = "SELECT data FROM DailyKpiPulpEntity data WHERE "
			+"data.mill.millId = :millId "
			+"AND date(data.datetime) = :yesterdayDate "
			+"AND data.kpiCategory.kpiCategoryId = :kpiCategoryId "
			+"AND data.businessUnit.businessUnitId = :buId "
			+"AND data.businessUnitType.businessUnitTypeId = :buTypeId "
			+"AND data.kpi.kpiId = :kpiId")
	public List<DailyKpiPulpEntity> fetchData(@Param("yesterdayDate") Date yesterdayDate,
			@Param("millId") Integer millId, @Param("kpiCategoryId") Integer kpiCategoryId,
			@Param("buId") Integer buId, @Param("buTypeId") Integer buTypeId,
			@Param("kpiId") Integer kpiId);
	
	@Query(value = "SELECT u FROM DailyKpiPulpEntity u WHERE "
			+ "u.businessUnitType.businessUnitTypeId = :buId "
			+ "and u.mill.millId = :millId "
			+ "and u.businessUnitType.businessUnitTypeId = :buTypeId "
			+ "and u.kpi.kpiId = :kpiId "
			+ "and u.kpiCategory.kpiCategoryId = :kpiCategoryId "
			+ "and date(u.datetime) between :startDate and :endDate "
			+ "order by date(u.datetime) asc")
	public List<DailyKpiPulpEntity> findByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate,
			@Param("millId")Integer millId,
			@Param("buId")Integer buId,
			@Param("buTypeId")Integer buTypeId,
			@Param("kpiCategoryId")Integer kpiCategoryId,
			@Param("kpiId")Integer kpiId);
	
	@Query(value = "SELECT cast(u.datetime as date) as date, u.processLine1 as fl1,"
			+ "u.processLine2 as fl2, u.processLine3 as fl3, "
			+ "u.processLine4 as pcd, u.processLine5 as pd1, "
			+ "u.processLine6 as pd2, u.processLine7 as pd3, "
			+ "u.processLine8 as pd4 FROM DailyKpiPulpEntity u WHERE "
			+ "u.businessUnitType.businessUnitTypeId = :buId "
			+ "and u.mill.millId = :millId "
			+ "and u.businessUnitType.businessUnitTypeId = :buTypeId "
			+ "and u.kpi.kpiId = :kpiId "
			+ "and u.kpiCategory.kpiCategoryId = :kpiCategoryId "
			+ "and date(u.datetime) between :startDate and :endDate "
			+ "order by date(u.datetime) asc")
	public List<Map<String, Object>> findByDateForDataGrid(@Param("startDate")Date startDate, @Param("endDate")Date endDate,
			@Param("millId")int millId,
			@Param("buId")int buId,
			@Param("buTypeId")int buTypeId,
			@Param("kpiCategoryId")int kpiCategoryId,
			@Param("kpiId")int kpiId);
	
	@Query(value = "SELECT u FROM DailyKpiPulpEntity u WHERE "
			+ "u.businessUnitType.businessUnitTypeId = :buId "
			+ "and u.mill.millId = :millId "
			+ "and u.businessUnitType.businessUnitTypeId = :buTypeId "
			+ "and u.kpi.kpiId = :kpiId "
			+ "and u.kpiCategory.kpiCategoryId = :kpiCategoryId "
			+ "and date(u.datetime) between :startDate and :endDate order by date(u.datetime) asc")
	public List<DailyKpiPulpEntity> findByDateForLineCharts(@Param("startDate")Date startDate, @Param("endDate")Date endDate,
			@Param("millId")Integer millId,
			@Param("buId")Integer buId,
			@Param("buTypeId")Integer buTypeId,
			@Param("kpiCategoryId")Integer kpiCategoryId,
			@Param("kpiId")Integer kpiId);
	
}
