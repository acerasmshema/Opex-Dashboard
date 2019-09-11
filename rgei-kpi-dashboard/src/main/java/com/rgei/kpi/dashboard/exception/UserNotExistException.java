package com.rgei.kpi.dashboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotExistException extends RuntimeException{
	public UserNotExistException(String exception) {
		super(exception);
	}
}
