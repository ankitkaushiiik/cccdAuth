package com.ccd.common.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccd.common.dto.ChangePasswordDto;
import com.ccd.common.dto.MailDto;
import com.ccd.common.dto.ForgetPasswordDto;
import com.ccd.common.dto.Response;
import com.ccd.common.service.UserService;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	static Logger logger = LogManager.getLogger(UserController.class);

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/verify/mail")
	public ResponseEntity<Response<String>> verifyMail(@RequestParam String email) {
		return userService.verifyMail(email);
	}

	@PostMapping("/forget/password")
	public ResponseEntity<Response<String>> forgetPassword(@RequestBody MailDto mailDto) {
		return userService.forgetPassword(mailDto);
	}

	@PostMapping("/verify/default/code")
	public ResponseEntity<Response<String>> verifyDefaultCode(@RequestBody ForgetPasswordDto forgetPasswordDto) {
		return userService.verifyDefaultCode(forgetPasswordDto);
	}

	@PostMapping("/reset/password")
	public ResponseEntity<Response<String>> resetPassword(@RequestBody ChangePasswordDto changePasswordDto) {
		return userService.resetPassword(changePasswordDto);
	}

}
