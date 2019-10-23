package com.rgei.kpi.dashboard.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;;

@JsonInclude(Include.NON_DEFAULT)
public class ApiSuccessResponse {

	private String status;
	private String description;
	private String timestamp;


	public ApiSuccessResponse() {

	}

	public ApiSuccessResponse(String timestamp, String description,
			String status) {

		this.timestamp = timestamp;
		this.description = description;
		this.status = status;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String message) {
		this.description = message;
	}

	@Override
	public String toString() {
		return "RequestMessage [status=" + status + ", timestamp=" + timestamp
				+ ", message=" + description + "]";
	}

}

