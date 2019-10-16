package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;

public interface ProcessLineConfigurationRepository extends JpaRepository<ProcessLineConfigurationEntity, Integer> {

	List<ProcessLineConfigurationEntity> findByMillIdAndBuTypeIdAndKpiId(Integer millId, Integer buTypeId, Integer kpiId);

	List<ProcessLineConfigurationEntity> findByMillIdAndKpiId(Integer millId, Integer kpiId);

}