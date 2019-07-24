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
public interface ProcessLineFrequencyRepository extends JpaRepository<DailyKpiPulpEntity, Long> {

	
	@Query(value = "select date(datetime), process_line_1 as FL1, process_line_2 as FL2,process_line_3 as FL3, process_line_4 as PCD, \r\n" + 
			"process_line_5 as PD1,process_line_6 as PD2,process_line_7 as PD3, process_line_8 as PD4 from daily_kpi_pulp WHERE mill_id= :millId AND\r\n" + 
			"bu_id= :buId AND bu_type_id= :buTypeId AND kpi_category_id= :kpiCategoryId AND kpi_id= :kpiId AND date(datetime) between :startDate AND :endDate order by date(datetime) asc", nativeQuery = true)
	public List<Object[]> getProcessLinesDailyTotal(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("millId") Integer millId,
			@Param("kpiCategoryId") Integer kpiCategoryId, @Param("buId") Integer buId,
			@Param("buTypeId") Integer buTypeId, @Param("kpiId") Integer kpiId);	
	
	@Query(value = "select extract(quarter from date(datetime)) quarterly_total,avg(nullif(process_line_1,'Nan')) as FL1,avg(nullif(process_line_2,'Nan')) as FL2,\r\n"
			+ "avg(nullif(process_line_3,'Nan')) as FL3, avg(nullif(process_line_4,'Nan')) as PCD, avg(nullif(process_line_5,'Nan')) as PD1,avg(nullif(process_line_6,'Nan')) as PD2,\r\n"
			+ "avg(nullif(process_line_7,'Nan')) as PD3, avg(nullif(process_line_8,'Nan')) as PD4, extract(year from date(datetime)) year_value from daily_kpi_pulp where mill_id= :millId AND bu_id= :buId AND bu_type_id= :buTypeId AND \r\n"
			+ "kpi_category_id= :kpiCategoryId AND kpi_id= :kpiId AND date(datetime) between :startDate AND :endDate group by year_value, quarterly_total order by year_value, quarterly_total asc;", nativeQuery = true)
	public List<Object[]> getProcessLinesTotalQuarterly(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("millId") Integer millId,
			@Param("kpiCategoryId") Integer kpiCategoryId, @Param("buId") Integer buId,
			@Param("buTypeId") Integer buTypeId, @Param("kpiId") Integer kpiId);
	
	
	@Query(value = "select extract(month from date(datetime)) monthly_value, avg(nullif(process_line_1,'Nan')) as FL1,avg(nullif(process_line_2,'Nan')) as FL2,\r\n"
			+ "avg(nullif(process_line_3,'Nan')) as FL3, avg(nullif(process_line_4,'Nan')) as PCD, avg(nullif(process_line_5,'Nan')) as PD1,avg(nullif(process_line_6,'Nan')) as PD2,\r\n"
			+ "avg(nullif(process_line_7,'Nan')) as PD3, avg(nullif(process_line_8,'Nan')) as PD4, extract(year from date(datetime)) year_value from daily_kpi_pulp where mill_id= :millId AND bu_id= :buId AND bu_type_id= :buTypeId AND \r\n"
			+ "kpi_category_id= :kpiCategoryId AND kpi_id= :kpiId AND date(datetime) between :startDate AND :endDate group by year_value, monthly_value \r\n"  
			+ "order by year_value, monthly_value asc;", nativeQuery = true)
	public List<Object[]> getProcessLinesTotalMonthly(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("millId") Integer millId,
			@Param("kpiCategoryId") Integer kpiCategoryId, @Param("buId") Integer buId,
			@Param("buTypeId") Integer buTypeId, @Param("kpiId") Integer kpiId);
	
	@Query(value = "select extract(year from date(datetime)) year_value, avg(nullif(process_line_1,'Nan')) as FL1,avg(nullif(process_line_2,'Nan')) as FL2,\r\n"
			+ "avg(nullif(process_line_3,'Nan')) as FL3, avg(nullif(process_line_4,'Nan')) as PCD, avg(nullif(process_line_5,'Nan')) as PD1,avg(nullif(process_line_6,'Nan')) as PD2,\r\n"
			+ "avg(nullif(process_line_7,'Nan')) as PD3, avg(nullif(process_line_8,'Nan')) as PD4 from daily_kpi_pulp where mill_id= :millId AND bu_id= :buId AND bu_type_id= :buTypeId AND \r\n"
			+ "kpi_category_id= :kpiCategoryId AND kpi_id= :kpiId AND date(datetime) between :startDate AND :endDate group by year_value order by year_value asc", nativeQuery = true)
	public List<Object[]> getProcessLinesTotalYearly(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("millId") Integer millId,
			@Param("kpiCategoryId") Integer kpiCategoryId, @Param("buId") Integer buId,
			@Param("buTypeId") Integer buTypeId, @Param("kpiId") Integer kpiId);
	
	
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
			@Param("millId")Integer millId,
			@Param("buId")Integer buId,
			@Param("buTypeId")Integer buTypeId,
			@Param("kpiCategoryId")Integer kpiCategoryId,
			@Param("kpiId")Integer kpiId);
	
}
