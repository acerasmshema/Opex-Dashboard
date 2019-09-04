package com.rgei.kpi.dashboard.exception.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class ApiSuccess extends RuntimeException {

	public ApiSuccess(String successMsg) {
		super(successMsg);
	}
}
