package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rgei.kpi.dashboard.entities.AnnualConfigurationEntity;
import com.rgei.kpi.dashboard.entities.ProcessLineConfigurationEntity;

public interface AnnualConfigurationRepository extends JpaRepository<AnnualConfigurationEntity, Integer> {

	public List<AnnualConfigurationEntity> findByMillId(Integer millId);
	
	AnnualConfigurationEntity findByAnnualConfigurationId(Integer annualConfigurationId);
	
	AnnualConfigurationEntity findByYear(Integer year);
}
