package com.rgei.kpi.dashboard.response.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRole {
	
	
	private String userRoleId;
	
	@NotNull(message="Role name cannot be missing or empty")
    @Size(min=2, message="First name must not be less than 2 characters")
	private String roleName;
	
	@Size(max=200, message="First name must not be less than 2 characters")
	private String description;
	
	private Boolean showUserManagement;
	
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private Boolean active;

	public Boolean getShowUserManagement() {
		return showUserManagement;
	}

	public void setShowUserManagement(Boolean showUserManagement) {
		this.showUserManagement = showUserManagement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
