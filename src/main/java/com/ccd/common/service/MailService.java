package com.ccd.common.service;

import com.ccd.common.dto.UserDto;

public interface MailService {

	public Integer sendDefaultPasswordMail(UserDto userDto, String otp);

}
