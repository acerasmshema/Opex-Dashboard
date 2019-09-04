package com.rgei.kpi.dashboard.exception.handling;

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
	
	public static final String RECORD_NOT_FOUND = "204";

}
