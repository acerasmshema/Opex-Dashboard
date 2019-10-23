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

	@Query(value = "from KpiConfigurationEntity kc where kc.startDate<:yDayDate and kc.endDate>:yDayDate"
			+ " and kc.millId=:millId and kc.kpiId=:kpiId and kc.buTypeId=:buTypeId")
	List<KpiConfigurationEntity> findKpiConfigurationForCurrentDate(@Param("millId") Integer millId,@Param("buTypeId") Integer buTypeId,@Param("kpiId") Integer kpiId,@Param("yDayDate") Date yDayDate);

}
