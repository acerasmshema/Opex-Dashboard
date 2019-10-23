package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rgei.kpi.dashboard.entities.KpiConfigurationEntity;

public interface KpiConfigurationRepository extends JpaRepository<KpiConfigurationEntity, Integer> {

	List<KpiConfigurationEntity> findByMillId(Integer millId);

	KpiConfigurationEntity findByKpiConfigurationId(int parseInt);

}
