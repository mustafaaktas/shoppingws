package com.shoppingws.exceptions;

public class UnAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 3324480045339476443L;

	public UnAuthorizedException(){
        super();
    }

    public UnAuthorizedException(String message){
        super(message);
    }
    public UnAuthorizedException(Exception e){
        super(e);
    }
}
