package com.shoppingws.exceptions;

public class SpringControllerExceptionThrower {

	public static void defineAndThrowSpringException(Exception e) {
		if (e instanceof BadRequestException) {
			throw new BadRequestException(e.getLocalizedMessage());
		} else if (e instanceof NotFoundException) {
			throw new NotFoundException(e.getLocalizedMessage());
		} else if (e instanceof UnAuthorizedException) {
			throw new UnAuthorizedException(e.getLocalizedMessage());
		} else if (e instanceof InternalServerErrorException){
			throw new InternalServerErrorException(e.getLocalizedMessage());
		} else {
			throw new NotFoundException(e.getLocalizedMessage());
		}
	}

}
