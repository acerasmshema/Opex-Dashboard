/*******************************************************************************
 * Copyright (c) 2019 Ace Resource Advisory Services Sdn. Bhd., Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Ace Resource Advisory Services Sdn. Bhd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Ace Resource Advisory Services Sdn. Bhd.
 * 
 * Ace Resource Advisory Services Sdn. Bhd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Ace Resource Advisory Services Sdn. Bhd. SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 ******************************************************************************/
package com.rgei.kpi.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rgei.kpi.dashboard.entities.RgeUserEntity;

@Repository
public interface RgeUserEntityRepository extends JpaRepository<RgeUserEntity, Long>{
	
	public RgeUserEntity findByLoginId(@Param("loginId") String loginId);
	
	public RgeUserEntity findByEmail(@Param("email") String email);
	
	public RgeUserEntity findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName")String lastName);
	
	public RgeUserEntity findByFirstNameOrLastName(@Param("firstName") String firstName, @Param("lastName")String lastName);
	
	@Query("Select ru from RgeUserEntity ru, IN (ru.userRoleMills) AS urm WHERE urm.millId = :millId Order By ru.firstName Asc")
	public List<RgeUserEntity> findAllUsersByMillId(@Param("millId") Integer millId);
}
