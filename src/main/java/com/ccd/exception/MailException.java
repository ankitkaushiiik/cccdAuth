package com.ccd.exception;

public class MailException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

	public MailException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
