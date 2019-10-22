package com.rgei.kpi.dashboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;

public interface KpiConfigurationRepository extends JpaRepository<KpiConfigurationEntity, Integer> {

	List<KpiConfigurationEntity> findByMillId(Integer millId);

	KpiConfigurationEntity findByKpiConfigurationId(int parseInt);

	@Query(value = "from KpiConfigurationEntity pl where (((pl.startDate <= :endDate) and (pl.endDate >= :startDate)) or ((pl.startDate <= :startDate) and (pl.endDate >= :startDate)))"
			+ "and pl.millId=:millId and pl.buTypeId=:buTypeId and pl.kpiId=:kpiId and pl.isDefault='false'")
	KpiConfigurationEntity getAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("millId") Integer millId, @Param("buTypeId") Integer buTypeId, @Param("kpiId") Integer kpiId);

}
