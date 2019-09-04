package com.rgei.kpi.dashboard.controller.exception;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

import com.rgei.kpi.dashboard.exception.handling.ApiResponse;
import com.rgei.kpi.dashboard.exception.handling.MessageConstant;
import com.rgei.kpi.dashboard.exception.handling.RecordNotFoundException;
import com.rgei.kpi.dashboard.exception.handling.StatusCodes;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionController {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ApiResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		ApiResponse apiResponse = new ApiResponse(StatusCodes.BAD_REQUEST, error, ex);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public HttpEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {

		String msg = exception.getMethod() + " method not supported only "
				+ Arrays.toString(exception.getSupportedMethods()) + " supported.";
		ApiResponse apiResponse = new ApiResponse(StatusCodes.INTERNAL_SERVER_ERROR, msg, exception);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		ApiResponse apiResponse = new ApiResponse(StatusCodes.BAD_REQUEST,
				"Request parsing error,please check request body data.", ex);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TypeMismatchException.class)
	protected ResponseEntity<ApiResponse> handleTypeMismatchException(TypeMismatchException ex) {
		ApiResponse apiResponse = new ApiResponse(StatusCodes.BAD_REQUEST, "Type mismatch " + ex.getValue(), ex);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		Set vioations = ex.getConstraintViolations();
		ConstraintViolation v = (ConstraintViolation) vioations.toArray()[0];
		ApiResponse apiResponse = new ApiResponse(StatusCodes.BAD_REQUEST, MessageConstant.UNKNOWN_EXCEPTION,
				ex.getClass().getCanonicalName() + " (" + v.getPropertyPath() + " " + v.getMessage() + ")");
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MultipartException.class)
	protected ResponseEntity<ApiResponse> handleMultipartException(MultipartException ex) {
		ApiResponse apiResponse = new ApiResponse(StatusCodes.BAD_REQUEST, "File required.", ex);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public HttpEntity<ApiResponse> handleException(Exception exception, WebRequest request) {
		ApiResponse apiResponse = new ApiResponse(new Date(System.currentTimeMillis()).toString(), exception,
				MessageConstant.UNKNOWN_EXCEPTION, request.getDescription(false), StatusCodes.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public HttpEntity<ApiResponse> handleRecordNotFoundException(RecordNotFoundException exception, WebRequest request) {
		ApiResponse apiResponse = new ApiResponse(new Date(System.currentTimeMillis()).toString(), exception,
				exception.getMessage(), request.getDescription(false),
				StatusCodes.RECORD_NOT_FOUND);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
