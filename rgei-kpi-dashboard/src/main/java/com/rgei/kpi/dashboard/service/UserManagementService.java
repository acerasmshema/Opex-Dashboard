package com.rgei.kpi.dashboard.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.rgei.kpi.dashboard.response.model.ChangePasswordRequest;
import com.rgei.kpi.dashboard.response.model.CountryResponse;
import com.rgei.kpi.dashboard.response.model.Department;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.response.model.UserRole;

public interface UserManagementService {
	List<CountryResponse> getCountryList();

	List<UserRole> getUserRolesByStatus(Boolean activeRoles);
	
	void createUserRole(UserRole userRole);
	
	void updateUserRole(UserRole userRole);
	
	void createUser(User user);
	
	List<Department> getDepartments();
	
	List<User> getUsersByMillId(Integer millId);
	
	void changePassword(ChangePasswordRequest changePasswordRequest) throws NoSuchAlgorithmException;

	void updateUser(User user);

	void encryptPasswordForAllUsers() throws NoSuchAlgorithmException;

}
