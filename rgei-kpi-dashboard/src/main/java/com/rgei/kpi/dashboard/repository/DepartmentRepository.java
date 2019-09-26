package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.DepartmentEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer>{

	List<DepartmentEntity> findAllByActiveOrderByDepartmentNameAsc(Boolean isActive);
	
}
