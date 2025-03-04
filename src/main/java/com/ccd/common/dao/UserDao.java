package com.ccd.common.dao;

import java.sql.Connection;

import com.ccd.common.dto.UserDto;

public interface UserDao {

	UserDto getuserDtls(String email);

	UserDto getuserDtlsByMailId(Connection conn, String email) throws Exception;

	Boolean saveDefaultCodeDtls(Connection conn, Integer userId, String otpText, String token) throws Exception;

	String verifyDefaultCodeAndGetToken(Connection conn, Integer userId, String otp) throws Exception;

	Boolean updateTokenSentStatus(Connection conn, Integer userId) throws Exception;

	Boolean verifyToken(Connection conn, Integer userId, String token) throws Exception;

	Boolean resetPassword(Connection conn, Integer userId, String newPassword, String userCode) throws Exception;

}
