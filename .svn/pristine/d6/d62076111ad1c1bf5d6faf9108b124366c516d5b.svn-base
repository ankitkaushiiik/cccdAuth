package com.ccd.common.service;

import org.springframework.http.ResponseEntity;

import com.ccd.common.dto.ChangePasswordDto;
import com.ccd.common.dto.MailDto;
import com.ccd.common.dto.ForgetPasswordDto;
import com.ccd.common.dto.Response;

public interface UserService {

	ResponseEntity<Response<String>> verifyMail(String email);

	ResponseEntity<Response<String>> forgetPassword(MailDto mailDto);

	ResponseEntity<Response<String>> verifyDefaultCode(ForgetPasswordDto forgetPasswordDto);

	ResponseEntity<Response<String>> resetPassword(ChangePasswordDto changePasswordDto);
}
