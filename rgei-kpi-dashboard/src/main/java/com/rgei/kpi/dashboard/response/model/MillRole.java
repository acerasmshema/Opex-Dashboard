package com.rgei.kpi.dashboard.response.model;

public class MillRole {
	private Long millRoleId ;
	private MillDetail selectedMill;
	private UserRole selectedUserRole;

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

	public Long getMillRoleId() {
		return millRoleId;
	}

	public void setMillRoleId(Long millRoleId) {
		this.millRoleId = millRoleId;
	}

}
