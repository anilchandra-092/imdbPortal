package com.alacriti.imdbportal.exceptions;

public class FailedToAccessData extends Exception{

	private static final long serialVersionUID = 1L;
	
	public FailedToAccessData(String message) {
		super(message);
	}
	public FailedToAccessData() {
	}
	
}
