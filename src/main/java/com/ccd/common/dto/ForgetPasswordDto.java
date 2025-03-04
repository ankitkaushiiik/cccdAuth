package com.ccd.common.dto;

public class ForgetPasswordDto {

	private String defaultCode;
	private String email;

	public String getDefaultCode() {
		return defaultCode;
	}

	public void setDefaultCode(String defaultCode) {
		this.defaultCode = defaultCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
