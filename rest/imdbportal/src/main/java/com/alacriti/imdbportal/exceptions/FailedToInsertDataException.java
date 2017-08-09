package com.alacriti.imdbportal.exceptions;

public class FailedToInsertDataException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public FailedToInsertDataException(String message) {
		super(message);
	}
	public FailedToInsertDataException() {
	}
	
	
}