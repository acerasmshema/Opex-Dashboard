package com.rgei.kpi.dashboard.controller.exception;

import java.util.Date;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.rgei.kpi.dashboard.exception.handling.ApiResponse;
import com.rgei.kpi.dashboard.exception.handling.StatusCodes;
import com.rgei.kpi.dashboard.exception.handling.ApiSuccess;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalSuccessController {

	@ExceptionHandler(ApiSuccess.class)
	protected ResponseEntity<ApiResponse> handleSuccessResponse(ApiSuccess response, WebRequest request){
		ApiResponse apiResponse = new ApiResponse(new Date(System.currentTimeMillis()).toString(), response, response.getMessage(), request.getDescription(false), StatusCodes.SUCCESS);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
}
