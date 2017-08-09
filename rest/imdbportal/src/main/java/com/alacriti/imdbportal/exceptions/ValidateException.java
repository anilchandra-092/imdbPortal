package com.alacriti.imdbportal.exceptions;

public class ValidateException extends Exception{

	private String message;
	private static final long serialVersionUID = 1L;
	
	public ValidateException(String message) {
		this.message=message;
	}
	public ValidateException() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return message;
	}
	
}
