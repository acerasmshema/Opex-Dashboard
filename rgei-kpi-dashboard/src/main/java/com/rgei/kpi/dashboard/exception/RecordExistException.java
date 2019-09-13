package com.rgei.kpi.dashboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordExistException extends RuntimeException{

	public RecordExistException(String exception) {
		super(exception);
	}
}
