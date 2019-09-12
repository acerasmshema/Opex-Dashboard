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
package com.rgei.kpi.dashboard.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.entities.LoginDetailEntity;
import com.rgei.kpi.dashboard.entities.RgeUserEntity;
import com.rgei.kpi.dashboard.entities.UserRoleEntity;
import com.rgei.kpi.dashboard.entities.UserRoleMillEntity;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.response.model.Department;
import com.rgei.kpi.dashboard.response.model.LoginDetailResponse;
import com.rgei.kpi.dashboard.response.model.MillDetail;
import com.rgei.kpi.dashboard.response.model.MillRole;
import com.rgei.kpi.dashboard.response.model.RgeUserResponse;
import com.rgei.kpi.dashboard.response.model.User;
import com.rgei.kpi.dashboard.response.model.UserRole;

public class UserConverter {
	
	private UserConverter() {
	}
	
	public static List<String> splitName(String name) {
		StringTokenizer stok = new StringTokenizer(name);
		List<String> names = new ArrayList<>();
	    while (stok.hasMoreTokens()) {
	    	names.add(stok.nextToken());
	    }
		return names;
	}
	
	public static RgeUserResponse convertToResponse(RgeUserEntity rgeUserEntity) {
		RgeUserResponse rgeUserResponse = null;
		if(Objects.nonNull(rgeUserEntity)) {
			rgeUserResponse = new RgeUserResponse();
			rgeUserResponse.setLoginId(rgeUserEntity.getLoginId());
			rgeUserResponse.setUserName(rgeUserEntity.getFirstName() + " " +rgeUserEntity.getLastName());
			if(!rgeUserEntity.getUserRoles().isEmpty()) {
				rgeUserResponse.setUserRole(rgeUserEntity.getUserRoles().get(0).getRoleName());
			}
		}else {
			throw new RecordNotFoundException("User not found");
		}
		return rgeUserResponse;
	}
	
	public static List<LoginDetailResponse> convertLoginUserInfoToLoginDetailResponse(List<LoginDetailEntity> loginDetailEntityList,
			Integer millId) {
		List<LoginDetailResponse> loginDetailResponseList = new ArrayList<LoginDetailResponse>();
		LoginDetailResponse response = null;
		for (LoginDetailEntity entity : loginDetailEntityList) {
			List<UserRoleMillEntity> rolesMillList = entity.getRgeUserEntity().getUserRoleMills();
			List<UserRoleEntity> roleList = entity.getRgeUserEntity().getUserRoles();
			for (UserRoleMillEntity e : rolesMillList) {
				if (e.getMillId() == millId) {
					response = new LoginDetailResponse();
					response.setFirstName(entity.getRgeUserEntity().getFirstName());
					response.setLastName(entity.getRgeUserEntity().getLastName());
					response.setLoginId(entity.getRgeUserEntity().getLoginId());
					response.setLoginTime(Utility.dateToStringConvertor(entity.getLoginTime(), DashboardConstant.DATE_TIME_FORMAT));

					for(UserRoleEntity roleEntity : roleList) {
						if(roleEntity.getRoleId() == e.getRoleId()) {
							response.setRoleName(roleEntity.getRoleName());
							break;
						}
					}
					loginDetailResponseList.add(response);
				}
			}
		}
		return loginDetailResponseList;
	}
	
	public static List<LoginDetailResponse> convertToLoginDetailResponseFromRgeUserEntity(List<RgeUserEntity> rgeUserEntityList,
			Integer millId) {
		List<LoginDetailResponse> loginDetailResponseList = new ArrayList<>();
		LoginDetailResponse response = null;
		for (RgeUserEntity entity : rgeUserEntityList) {
			List<UserRoleMillEntity> rolesMillList = entity.getUserRoleMills();
			for (UserRoleMillEntity e : rolesMillList) {
				fetchResponse(millId, loginDetailResponseList, entity, e);
			}
		}
		return loginDetailResponseList;
	}

	private static void fetchResponse(Integer millId, List<LoginDetailResponse> loginDetailResponseList,
			RgeUserEntity entity, UserRoleMillEntity e) {
		LoginDetailResponse response;
		if (e.getMillId().equals(millId)) {
			response = new LoginDetailResponse();
			response.setFirstName(entity.getFirstName());
			response.setLastName(entity.getLastName());
			response.setLoginId(entity.getLoginId());

			for(UserRoleEntity roleEntity : entity.getUserRoles()) {
				if(roleEntity.getRoleId() == e.getRoleId()) {
					response.setRoleName(roleEntity.getRoleName());
					break;
				}
			}
			for(LoginDetailEntity loginEntity : entity.getLoginDetails()) {
				if(loginEntity.getRgeUserEntity().getUserId().equals(entity.getUserId())) {
					response.setLoginTime(Utility.dateToStringConvertor(loginEntity.getLoginTime(), DashboardConstant.FORMAT));
					break;
				}
			}
			loginDetailResponseList.add(response);
		}
	}
	
	public static Date getCurrentDate() {
		LocalDate currentDate= LocalDate.now();
		return Date.valueOf(currentDate.toString());
	}

	public static User createUserResponse(RgeUserEntity entity) {
		User userResponse = new User();
		userResponse.setUserId(entity.getUserId().toString());
		userResponse.setFirstName(entity.getFirstName());
		userResponse.setLastName(entity.getLastName());
		userResponse.setEmail(entity.getEmail());
		userResponse.setPhone(entity.getPhone());
		userResponse.setUsername(entity.getLoginId());
		userResponse.setAddress(entity.getAddress());
		userResponse.setCountry(entity.getCountry());
		userResponse.setActive(entity.getIsActive());
		userResponse.setCreatedBy(entity.getCreatedBy());
		userResponse.setCreatedDate(entity.getCreatedOn().toString());
		userResponse.setUpdatedBy(entity.getUpdatedBy());
		userResponse.setUpdatedDate(entity.getUpdatedOn().toString());
		userResponse.setDepartment(getDepartmentDetails(entity));
		userResponse.setMillRoles(getMillRoleDetails(entity));
		return userResponse;
	}

	private static List<MillRole> getMillRoleDetails(RgeUserEntity entity) {
		List<MillRole> millRoles = new ArrayList<>();
		for( UserRoleMillEntity userRoleMill: entity.getUserRoleMills()) {
			MillRole role = new MillRole();
			role.setSelectedUserRole(getUserRoleDetails(userRoleMill));
			role.setSelectedMill(getMillDetails(userRoleMill));
			millRoles.add(role);
		}
		return millRoles;
	}

	private static MillDetail getMillDetails(UserRoleMillEntity userRoleMill) {
		MillDetail millDetail = new MillDetail();
		millDetail.setMillId(userRoleMill.getMillId().toString());
		millDetail.setMillName(userRoleMill.getMill().getMillName());
		millDetail.setMillCode(userRoleMill.getMill().getMillCode());
		millDetail.setActive(userRoleMill.getMill().getActive());
		millDetail.setCountryId(userRoleMill.getMill().getCountry().getCountryId().toString());
		millDetail.setCreatedBy(userRoleMill.getMill().getCreatedBy());
		millDetail.setCreatedDate(userRoleMill.getMill().getCreatedDate().toString());
		millDetail.setUpdatedBy(userRoleMill.getMill().getUpdatedBy());
		millDetail.setUpdatedDate(userRoleMill.getMill().getUpdatedDate().toString());
		return millDetail;
	}

	private static UserRole getUserRoleDetails(UserRoleMillEntity userRoleMill) {
		UserRole userRole = new UserRole();
		userRole.setUserRoleId(userRoleMill.getRoleId().toString());
		userRole.setRoleName(userRoleMill.getRole().getRoleName());
		userRole.setActive(userRole.getActive());
		userRole.setCreatedBy(userRole.getCreatedBy());
		userRole.setCreatedDate(userRole.getCreatedDate());
		userRole.setUpdatedBy(userRole.getUpdatedBy());
		userRole.setUpdatedDate(userRole.getUpdatedDate());
		return userRole;
	}

	private static Department getDepartmentDetails(RgeUserEntity entity) {
		Department department = new Department();
		department.setDepartmentId(entity.getDepartment().getDepartmentId());
		department.setDepartmentName(entity.getDepartment().getDepartmentName());
		department.setDepartmentCode(entity.getDepartment().getDepartmentCode());
		department.setActive(entity.getDepartment().getActive());
		department.setCreatedBy(entity.getDepartment().getCreatedBy());
		department.setCreatedDate(entity.getDepartment().getCreatedDate().toString());
		department.setUpdatedBy(entity.getDepartment().getUpdatedBy());
		department.setUpdatedDate(entity.getDepartment().getUpdatedDate().toString());
		return department;
	}
	
}
