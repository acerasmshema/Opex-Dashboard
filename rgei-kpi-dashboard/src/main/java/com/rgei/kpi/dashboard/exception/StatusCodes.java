package com.rgei.kpi.dashboard.exception;

public class StatusCodes {
	private StatusCodes() {
	}
	/**
	 * public and final
	 */
	public static final String SUCCESS = "200";
	/**
	 * public and final
	 */
	public static final String NOT_FOUND = "404";
	/**
	 * public and final
	 */
	public static final String INTERNAL_SERVER_ERROR = "500";
	/**
	 * public and final
	 */
	public static final String ACCESS_RESTRICTED = "401";
	/**
	 * public and final
	 */
	public static final String ACCESS_FORBIDDEN = "403";
	/**
	 * public and final
	 */
	public static final String BAD_REQUEST = "400";
	
	/**
	 * Custom Status Codes 
	 */
	
	public static final String INVALID_CREDENTIALS = "1001";
	public static final String MAINTENANCE_DAY_CREATE_RECORD_ERROR = "1002";
	public static final String TARGET_DAYS_UPDATE_ERROR = "1003";
	public static final String MAINTENANCE_DAY_UPDATE_RECORD_ERROR = "1004";
	public static final String MAINTENANCE_DAY_DELETE_RECORD_ERROR = "1005";
	public static final String ANNOTATION_CREATE_RECORD_ERROR = "1006";
	public static final String ANNOTATION_DELETE_RECORD_ERROR = "1007";
	public static final String USER_NOT_EXIST = "1008";
	public static final String LOGOUT_ERROR = "1009";
	public static final String RECORD_NOT_FOUND = "1010";
	public static final String INACTIVE_USER = "1011";
	
	

}
