package com.shoppingws.exceptions;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String errorMessage) {
		super(errorMessage);
	}

	public BadRequestException(Exception exception) {
		super(exception);
	}

}
