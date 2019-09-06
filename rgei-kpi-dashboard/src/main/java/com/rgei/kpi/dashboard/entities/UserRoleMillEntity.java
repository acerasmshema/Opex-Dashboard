package com.rgei.kpi.dashboard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rge_user_role")
public class UserRoleMillEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rge_user_role_id",unique = true, nullable = false)
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="role_id")
	private Long roleId;
	
	@Column(name="mill_id")
	private Integer millId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	private RgeUserEntity user;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getMillId() {
		return millId;
	}

	public void setMillId(Integer millId) {
		this.millId = millId;
	}

	
}
