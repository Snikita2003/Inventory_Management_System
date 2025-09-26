package com.prod.exception;

public class NotFounException extends RuntimeException {

	String Message;
	
	public NotFounException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotFounException(String msg) {
		super(msg);
		this.Message = msg;
	}

	
	
}
