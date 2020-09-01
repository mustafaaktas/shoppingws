package com.shoppingws.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SpringGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InternalServerErrorException.class)
	@ResponseBody
	protected ErrorDetail handleSpringInternalServerErrorException(HttpServletRequest request, Exception exception) {
		return new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnAuthorizedException.class)
	@ResponseBody
	protected ErrorDetail handleSpringUnauthorizedException(HttpServletRequest request, Exception exception) {
		return new ErrorDetail(HttpStatus.UNAUTHORIZED.value(), exception.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	protected ErrorDetail handleSpringBadRequestException(HttpServletRequest request, Exception exception) {
		return new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	protected ErrorDetail handleSpringNotFoundException(HttpServletRequest request, Exception exception) {
		return new ErrorDetail(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "JSON Parsing Error!");
		return new ResponseEntity<Object>(errorDetail, headers, HttpStatus.BAD_REQUEST);
	}

}