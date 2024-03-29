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
package com.rgei.kpi.dashboard.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


@Entity
@Table(name="rge_user")
public class RgeUserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id",unique = true, nullable = false)
	private Long userId;
	
	@Column(name="user_password")
	private String userPassword;
	
	@Column(name="login_id")
	private String loginId;
	
	private String address;

	private String country;

	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="created_date")
	private Time createdOn;

	private String email;

	@Column(name="first_name")
	private String firstName;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(name="last_name")
	private String lastName;

	@Column(name="middle_name")
	private String middleName;

	private String phone;

	@Column(name="updated_date")
	private Time updatedOn;

	@ManyToMany(mappedBy="rgeUsers", fetch = FetchType.EAGER)
	private List<UserRoleEntity> userRoles;
	
	@OneToMany(mappedBy="rgeUserEntity")
	private List<LoginDetailEntity> loginDetails;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Time getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Time createdOn) {
		this.createdOn = createdOn;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Time getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Time updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<UserRoleEntity> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRoleEntity> userRoles) {
		this.userRoles = userRoles;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<LoginDetailEntity> getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(List<LoginDetailEntity> loginDetails) {
		this.loginDetails = loginDetails;
	}

}
