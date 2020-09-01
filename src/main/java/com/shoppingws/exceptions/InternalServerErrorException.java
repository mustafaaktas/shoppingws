package com.shoppingws.exceptions;


public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InternalServerErrorException() {
		super();
	}

	public InternalServerErrorException(String errorMessage) {
		super(errorMessage);
	}

	public InternalServerErrorException(Exception exception) {
		super(exception);
	}

}
