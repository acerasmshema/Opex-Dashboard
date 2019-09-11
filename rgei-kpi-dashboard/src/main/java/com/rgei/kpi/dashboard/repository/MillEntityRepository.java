package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rgei.kpi.dashboard.entities.MillEntity;

@Repository
public interface MillEntityRepository extends JpaRepository<MillEntity, Integer>{
	
	
	@Query("Select ME from  MillEntity ME where ME.country.countryId IN :countryIds And ME.active = :status")
	List<MillEntity> findByCountry(@Param("countryIds") List<Integer> countryIds, @Param("status") Boolean status);
	
	List<MillEntity> findByActive(@Param("active") Boolean active);
	
	MillEntity findByMillId(Integer millId);

}
