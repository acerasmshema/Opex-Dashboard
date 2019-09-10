package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.CountryEntity;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer>{

	public List<CountryEntity> findAllByActiveOrderByCountryNameAsc(Boolean active);
}
