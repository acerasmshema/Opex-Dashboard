package com.rgei.kpi.dashboard.service;

public interface ValidationService {
	Boolean validateUserName(String username);
	
	Boolean validateEmail(String email);
}
