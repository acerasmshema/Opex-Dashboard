package com.rgei.kpi.dashboard.entities;

import java.util.Date;

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
	private Long rgeUserRoleId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="role_id")
	private Long roleId;
	
	@Column(name="mill_id")
	private Integer millId;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="active")
	private Boolean status;
	
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="updated_date")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	private RgeUserEntity user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="mill_id", insertable = false, updatable = false)
	private MillEntity mill;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id", insertable = false, updatable = false)
	private UserRoleEntity role;
	
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

	public Long getRgeUserRoleId() {
		return rgeUserRoleId;
	}

	public void setRgeUserRoleId(Long rgeUserRoleId) {
		this.rgeUserRoleId = rgeUserRoleId;
	}

	public RgeUserEntity getUser() {
		return user;
	}

	public void setUser(RgeUserEntity user) {
		this.user = user;
	}

	public MillEntity getMill() {
		return mill;
	}

	public void setMill(MillEntity mill) {
		this.mill = mill;
	}

	public UserRoleEntity getRole() {
		return role;
	}

	public void setRole(UserRoleEntity role) {
		this.role = role;
	}
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
