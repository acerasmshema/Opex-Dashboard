package com.rgei.kpi.dashboard.response.model;

public class KpiAnnotationDeleteRequest {
	private String userId; 
	private String annotationId;
	
	public String getAnnotationId() {
		return annotationId;
	}
	public void setAnnotationId(String annotationId) {
		this.annotationId = annotationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
