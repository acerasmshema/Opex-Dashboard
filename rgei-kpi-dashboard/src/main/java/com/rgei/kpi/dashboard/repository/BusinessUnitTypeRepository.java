package com.rgei.kpi.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.BusinessUnitTypeEntity;

@Repository
public interface BusinessUnitTypeRepository extends JpaRepository<BusinessUnitTypeEntity, Integer>{

}
