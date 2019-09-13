package com.rgei.kpi.dashboard.exception;

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

import com.rgei.kpi.dashboard.constant.DashboardConstant;
import com.rgei.kpi.dashboard.response.model.ApiErrorResponse;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, error, ex);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public HttpEntity<ApiErrorResponse> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {

		String msg = exception.getMethod() + " method not supported only "
				+ Arrays.toString(exception.getSupportedMethods()) + " supported.";
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INTERNAL_SERVER_ERROR, msg, exception);
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST,
				"Request parsing error,please check request body data.", ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TypeMismatchException.class)
	protected ResponseEntity<ApiErrorResponse> handleTypeMismatchException(TypeMismatchException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, "Type mismatch " + ex.getValue(),
				ex);
		return new ResponseEntity<ApiErrorResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<ApiErrorResponse> handleNullPointerException(NullPointerException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INTERNAL_SERVER_ERROR, "NullPointerException",
				ex);
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	protected ResponseEntity<ApiErrorResponse> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INTERNAL_SERVER_ERROR,
				"ArrayIndexOutOfBoundsException", ex);
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		Set vioations = ex.getConstraintViolations();
		ConstraintViolation v = (ConstraintViolation) vioations.toArray()[0];
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, MessageConstant.UNKNOWN_EXCEPTION,
				ex.getClass().getCanonicalName() + " (" + v.getPropertyPath() + " " + v.getMessage() + ")");
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MultipartException.class)
	protected ResponseEntity<ApiErrorResponse> handleMultipartException(MultipartException ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.BAD_REQUEST, "File required.", ex);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public HttpEntity<ApiErrorResponse> handleException(Exception exception, WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(new Date(System.currentTimeMillis()).toString(), exception,
				MessageConstant.UNKNOWN_EXCEPTION, request.getDescription(false), StatusCodes.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public HttpEntity<ApiErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception,
			WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.RECORD_NOT_FOUND, exception.getMessage(),
				MessageConstant.RECORD_NOT_FOUND, request.getDescription(false));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotExistException.class)
	public HttpEntity<ApiErrorResponse> handleUserNotFoundException(UserNotExistException exception,
			WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.USER_NOT_EXIST, exception.getMessage(),
				MessageConstant.USER_NOT_EXIST, request.getDescription(false));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public HttpEntity<ApiErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException exception,
			WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INVALID_CREDENTIALS, exception.getMessage(),
				MessageConstant.INVALID_CREDENTIALS, request.getDescription(false));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InActiveUserException.class)
	public HttpEntity<ApiErrorResponse> handleInActiveUserException(InActiveUserException exception,
			WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.INACTIVE_USER, exception.getMessage(),
				MessageConstant.INACTIVE_USER, request.getDescription(false));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(LogoutException.class)
	public HttpEntity<ApiErrorResponse> handleLogoutException(LogoutException exception, WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.LOGOUT_ERROR, exception.getMessage(),
				MessageConstant.LOGOUT_ERROR, request.getDescription(false));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotCreatedException.class)
	public HttpEntity<ApiErrorResponse> handleCreateRecordException(RecordNotCreatedException exception, WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse();
		if(request.getDescription(false).contains(DashboardConstant.MAINTENANCE)) {
		apiResponse = new ApiErrorResponse(StatusCodes.MAINTENANCE_DAY_CREATE_RECORD_ERROR, exception.getMessage(), MessageConstant.CREATE_RECORD_ERROR, request.getDescription(false));
		}else if(request.getDescription(false).contains(DashboardConstant.ANNOTATION)) {
		apiResponse = new ApiErrorResponse(StatusCodes.ANNOTATION_CREATE_RECORD_ERROR, exception.getMessage(), MessageConstant.CREATE_RECORD_ERROR, request.getDescription(false));
		}
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RecordNotUpdatedException.class)
	public HttpEntity<ApiErrorResponse> handleRecordNotUpdatedException(RecordNotUpdatedException exception, WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse();
		if(request.getDescription(false).contains(DashboardConstant.MAINTENANCE)) {
		apiResponse = new ApiErrorResponse(StatusCodes.MAINTENANCE_DAY_UPDATE_RECORD_ERROR, exception.getMessage(), MessageConstant.UPDATE_RECORD_ERROR, request.getDescription(false));
		}else if(request.getDescription(false).contains(DashboardConstant.TARGET)) {
		apiResponse = new ApiErrorResponse(StatusCodes.TARGET_DAYS_UPDATE_ERROR, exception.getMessage(), MessageConstant.UPDATE_RECORD_ERROR, request.getDescription(false));
		}
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RecordNotDeletedException.class)
	public HttpEntity<ApiErrorResponse> handleRecordNotDeletedException(RecordNotDeletedException exception, WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse();
		if(request.getDescription(false).contains(DashboardConstant.MAINTENANCE)) {
		apiResponse = new ApiErrorResponse(StatusCodes.MAINTENANCE_DAY_DELETE_RECORD_ERROR, exception.getMessage(), MessageConstant.DELETE_RECORD_ERROR, request.getDescription(false));
		}else if(request.getDescription(false).contains(DashboardConstant.ANNOTATION)) {
		apiResponse = new ApiErrorResponse(StatusCodes.ANNOTATION_DELETE_RECORD_ERROR, exception.getMessage(), MessageConstant.DELETE_RECORD_ERROR, request.getDescription(false));
		}
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RecordExistException.class)
	public HttpEntity<ApiErrorResponse> handleRecordExistException(RecordExistException exception,
			WebRequest request) {
		ApiErrorResponse apiResponse = new ApiErrorResponse(StatusCodes.RECORD_EXISTS, exception.getMessage(),
				MessageConstant.RECORD_EXISTS, request.getDescription(false));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
