package com.rgei.kpi.dashboard.response.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;;

public class ApiErrorResponse {

	private String status;
	private String description;
	private String timestamp;
	private String error;
	private String path;
	@JsonIgnore
	private String stackTrace;
	@JsonIgnore
	private Throwable throwable;

	public ApiErrorResponse() {

	}

	public ApiErrorResponse(String timestamp, Throwable ex, String description, String path, String status) {

		this.timestamp = timestamp;
		if (ex != null) {
			this.error = ex.getClass().getCanonicalName();
			this.stackTrace = "";
		}
		this.description = description;
		this.path = path;
		this.status = status;
	}

	public ApiErrorResponse(String errorCode, String errorMessage) {

		this.status = errorCode;
		this.error = errorMessage;
	}

	public ApiErrorResponse(String errorCode, String errorMessage, String developerMessage, String path) {

		this.timestamp = new Date(System.currentTimeMillis()).toString();
		this.status = errorCode;
		this.error = errorMessage;
		this.description = developerMessage;
		this.path = path;
	}

	public ApiErrorResponse(String errorCode, String errorMessage, String developerMessage) {

		this.status = errorCode;
		this.error = errorMessage;
		this.description = developerMessage;

	}

	public ApiErrorResponse(String errorCode, String errorMessage, Throwable throwable) {

		this.status = errorCode;
		this.description = errorMessage;

		if (throwable != null) {
			this.error = throwable.getClass().getCanonicalName();
			this.stackTrace = "";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String message) {
		this.description = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	@Override
	public String toString() {
		return "RequestMessage [status=" + status + ", path=" + path + ", timestamp=" + timestamp + ", message="
				+ description + ", error=" + error + "]";
	}

}
