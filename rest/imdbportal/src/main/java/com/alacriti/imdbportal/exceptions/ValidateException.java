package com.alacriti.imdbportal.exceptions;

public class ValidateException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ValidateException(String message) {
		super(message);
	}
	public ValidateException() {
	}
}
