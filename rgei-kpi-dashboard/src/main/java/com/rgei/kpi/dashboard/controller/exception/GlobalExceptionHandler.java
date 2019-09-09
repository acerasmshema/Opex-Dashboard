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

import com.rgei.kpi.dashboard.exception.MessageConstant;
import com.rgei.kpi.dashboard.exception.RecordNotFoundException;
import com.rgei.kpi.dashboard.exception.StatusCodes;
import com.rgei.kpi.dashboard.response.model.ApiErrorResponse;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, error, ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public HttpEntity<ApiErrorResponse> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {

		String msg = exception.getMethod() + " method not supported only "
				+ Arrays.toString(exception.getSupportedMethods()) + " supported.";
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INTERNAL_SERVER_ERROR, msg, exception);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST,
				"Request parsing error,please check request body data.", ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TypeMismatchException.class)
	protected ResponseEntity<ApiErrorResponse> handleTypeMismatchException(TypeMismatchException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, "Type mismatch " + ex.getValue(), ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<ApiErrorResponse> handleNullPointerException(NullPointerException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INTERNAL_SERVER_ERROR, "NullPointerException", ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	protected ResponseEntity<ApiErrorResponse> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INTERNAL_SERVER_ERROR, "ArrayIndexOutOfBoundsException", ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		Set vioations = ex.getConstraintViolations();
		ConstraintViolation v = (ConstraintViolation) vioations.toArray()[0];
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, MessageConstant.UNKNOWN_EXCEPTION,
				ex.getClass().getCanonicalName() + " (" + v.getPropertyPath() + " " + v.getMessage() + ")");
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MultipartException.class)
	protected ResponseEntity<ApiErrorResponse> handleMultipartException(MultipartException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, "File required.", ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public HttpEntity<ApiErrorResponse> handleException(Exception exception, WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(new Date(System.currentTimeMillis()).toString(), exception,
				MessageConstant.UNKNOWN_EXCEPTION, request.getDescription(false), StatusCodes.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public HttpEntity<ApiErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception, WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(new Date(System.currentTimeMillis()).toString(), exception,
				exception.getMessage(), request.getDescription(false),
				StatusCodes.RECORD_NOT_FOUND);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
