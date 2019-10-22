package com.rgei.kpi.dashboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;

public interface ProcessLineConfigurationRepository extends JpaRepository<ProcessLineConfigurationEntity, Integer> {

	List<ProcessLineConfigurationEntity> findByMillIdAndBuTypeIdAndKpiId(Integer millId, Integer buTypeId, Integer kpiId);
	
	ProcessLineConfigurationEntity findByStartDateBetween(String startDate,String endDate);
	
	@Query(value = "from ProcessLineConfigurationEntity pl where (((pl.startDate <= :endDate) and (pl.endDate >= :startDate)) or ((pl.startDate <= :startDate) and (pl.endDate >= :startDate)))"
			+ "and pl.millId=:millId and pl.buTypeId=:buTypeId and pl.kpiId=:kpiId and pl.processLineId=:processLineId and pl.isDefault='false'")
	ProcessLineConfigurationEntity getAllBetweenDates(@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("millId") Integer millId,@Param("buTypeId") Integer buTypeId,@Param("kpiId") Integer kpiId,
			@Param("processLineId") Integer processLineId);


	ProcessLineConfigurationEntity findByProcessLineConfigurationId(Integer processLineConfigurationId);

	List<ProcessLineConfigurationEntity> findByMillIdAndKpiId(Integer millId, Integer kpiId);
	
	List<ProcessLineConfigurationEntity> findByMillIdAndKpiIdAndBuTypeId(Integer millId, Integer kpiId, Integer buTypeId);

	@Query(value = "from ProcessLineConfigurationEntity pl where pl.startDate<:yDayDate and pl.endDate>:yDayDate"
			+ " and pl.millId=:millId and pl.kpiId=1")
	List<ProcessLineConfigurationEntity> fetchConfigurationDataForProcessLine(@Param("millId") Integer millId,@Param("yDayDate") Date yDayDate);

	


}