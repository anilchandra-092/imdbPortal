package com.alacriti.imdbportal.exceptions;

public class FailedToAccessData extends Exception{

	private String message;
	private static final long serialVersionUID = 1L;
	
	public FailedToAccessData(String message) {
		this.message=message;
	}
	public FailedToAccessData() {
	}
	@Override
	public String toString() {
		return message;
	}
}
