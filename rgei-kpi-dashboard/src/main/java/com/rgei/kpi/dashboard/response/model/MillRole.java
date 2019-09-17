package com.rgei.kpi.dashboard.response.model;

public class MillRole {
	private String millRoleId;
	private MillDetail selectedMill;
	private UserRole selectedUserRole;
	private Boolean active;

	public String getMillRoleId() {
		return millRoleId;
	}

	public void setMillRoleId(String millRoleId) {
		this.millRoleId = millRoleId;
	}

	public MillDetail getSelectedMill() {
		return selectedMill;
	}

	public void setSelectedMill(MillDetail selectedMill) {
		this.selectedMill = selectedMill;
	}

	public UserRole getSelectedUserRole() {
		return selectedUserRole;
	}

	public void setSelectedUserRole(UserRole selectedUserRole) {
		this.selectedUserRole = selectedUserRole;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
