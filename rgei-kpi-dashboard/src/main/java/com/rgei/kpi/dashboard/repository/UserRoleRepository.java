package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.UserRoleEntity;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long>{

	public List<UserRoleEntity> findAllByStatusOrderByRoleIdAsc(Boolean active);
	
	public List<UserRoleEntity> findAllByOrderByRoleIdAsc();
	
	public UserRoleEntity findByRoleId(Long roleId);
	
}
