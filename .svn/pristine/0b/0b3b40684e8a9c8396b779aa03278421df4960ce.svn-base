package com.ccd.common.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ccd.common.dao.UserDao;
import com.ccd.common.daoImpl.CommonDaoImpl;
import com.ccd.common.dto.ChangePasswordDto;
import com.ccd.common.dto.MailDto;
import com.ccd.common.dto.ForgetPasswordDto;
import com.ccd.common.dto.Response;
import com.ccd.common.dto.UserDto;
import com.ccd.common.service.MailService;
import com.ccd.common.service.UserService;
import com.ccd.common.utility.LogStackTrace;
import com.ccd.common.utility.PersonalUtility;
import com.ccd.exception.DBReadWriteException;

@Service
public class UserServiceImpl extends CommonDaoImpl implements UserService {

	static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private final MailService mailService;
	private final UserDao userDao;
	private final LogStackTrace logStackTrace;
	private final PersonalUtility personalUtility;

	@Autowired
	public UserServiceImpl(MailService mailService, UserDao userDao, LogStackTrace logStackTrace,
			PersonalUtility personalUtility) {
		this.mailService = mailService;
		this.userDao = userDao;
		this.logStackTrace = logStackTrace;
		this.personalUtility = personalUtility;
	}

	@Override
	public ResponseEntity<Response<String>> verifyMail(String email) {
		ResponseEntity<Response<String>> reponseEntity = null;
		Response<String> reponse = new Response<String>();
		try {

			UserDto userDto = userDao.getuserDtls(email);

			if (userDto == null) {
				reponse.setKey(500);
				reponse.setMessage("Some error has taken place");
				reponseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (userDto.getUserId() == null) {
				reponse.setKey(100);
				reponse.setMessage("Invalid Email");
				reponse.setValue("No Email found");
				reponseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.OK);
			} else {
				if (StringUtils.equals("N", userDto.getIsActive())) {
					reponse.setKey(101);
					reponse.setMessage("Invalid");
					reponse.setValue("Inactive user");
					reponseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.OK);
				} else {
					reponse.setKey(200);
					reponse.setMessage("Valid");
					reponse.setValue("Active User");
					reponseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.OK);
				}
			}

		} catch (Exception ex) {
			reponse = null;
			reponseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("Problem in Class - UserServiceImpl ~~ method-> verifyUser() - " + ex.getMessage());
			logStackTrace.accept(ex);
		}
		return reponseEntity;
	}

	@Override
	public ResponseEntity<Response<String>> forgetPassword(MailDto mailDto) {
		ResponseEntity<Response<String>> responseEntity = null;
		Response<String> reponse = null;
		try {
			reponse = this.verifyUserAndCreateDefaultPassword(mailDto);

			if (reponse == null) {
				responseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.OK);
			}

		} catch (Exception ex) {
			reponse = null;
			responseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("Problem in Class - UserServiceImpl ~~ method-> forgetPassword() - " + ex.getMessage());
			logStackTrace.accept(ex);
		}
		return responseEntity;
	}

	private Response<String> verifyUserAndCreateDefaultPassword(MailDto mailDto) {

		Connection conn = null;
		Response<String> reponse = new Response<String>();

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			UserDto userDto = userDao.getuserDtlsByMailId(conn, mailDto.getEmail());

			if (userDto.getUserId() == null) {
				reponse.setKey(100);
				reponse.setMessage("Invalid Email");
				reponse.setValue("No Email found");
			} else if (StringUtils.equals("N", userDto.getIsActive())) {
				reponse.setKey(101);
				reponse.setMessage("Invalid");
				reponse.setValue("Inactive user");
			} else {

				String otp = StringUtils.substring(userDto.getUserCode(), 0, 3) + personalUtility.passwordGenerator();
				String token = personalUtility.tokenGenerator();

				boolean isDefaultCodeDtlsSaved = userDao.saveDefaultCodeDtls(conn, userDto.getUserId(), otp, token);
				if (!isDefaultCodeDtlsSaved) {
					throw new DBReadWriteException("Some error has taken place while Saving Default Password.");
				}

				int responseCode = mailService.sendDefaultPasswordMail(userDto, otp);

				if (responseCode == 200) {
					reponse.setKey(200);
					reponse.setMessage("Sent successfully");
					reponse.setValue("Default Code sent to mail");
				} else {
					reponse.setKey(102);
					reponse.setMessage("failed");
					reponse.setValue("Mail server down");
				}
			}

			if (reponse.getKey() == 102) {
				conn.rollback();
			} else {
				conn.commit();
			}
		} catch (Exception ex) {
			try {
				reponse = null;
				conn.rollback();
				logger.info("Problem in Class - UserServiceImpl ~~ method-> verifyUserAndCreateDefaultPassword() - "
						+ ex.getMessage());
				logStackTrace.accept(ex);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return reponse;
	}

	@Override
	public ResponseEntity<Response<String>> verifyDefaultCode(ForgetPasswordDto forgetPasswordDto) {
		ResponseEntity<Response<String>> responseEntity = null;
		Response<String> response = null;
		try {

			response = this.verifyUserDtlsAndVerifyDefaultCode(forgetPasswordDto);

			if (response == null) {
				responseEntity = new ResponseEntity<Response<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseEntity = new ResponseEntity<Response<String>>(response, HttpStatus.OK);
			}

		} catch (Exception ex) {
			response = null;
			responseEntity = new ResponseEntity<Response<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("Problem in Class - UserServiceImpl ~~ method-> forgetPassword() - " + ex.getMessage());
			logStackTrace.accept(ex);
		}
		return responseEntity;
	}

	private Response<String> verifyUserDtlsAndVerifyDefaultCode(ForgetPasswordDto forgetPasswordDto) {
		Connection conn = null;

		Response<String> response = new Response<String>();
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			UserDto userDto = userDao.getuserDtlsByMailId(conn, forgetPasswordDto.getEmail());

			if (userDto.getUserId() > 0 && StringUtils.equals("Y", userDto.getIsActive())) {

				String token = userDao.verifyDefaultCodeAndGetToken(conn, userDto.getUserId(),
						forgetPasswordDto.getDefaultCode());

				if (token != null) {

					boolean isUpdated = userDao.updateTokenSentStatus(conn, userDto.getUserId());
					if (!isUpdated) {
						throw new DBReadWriteException("Some error has taken place while updating token sent status");
					}
					response.setKey(200);
					response.setMessage("Varified");
					response.setValue(token);

				} else {
					response.setKey(104);
					response.setMessage("Failed");
					response.setValue("Default Code checking time expired. Try again");
				}
			} else {
				response.setKey(103);
				response.setMessage("Failed");
				response.setValue("No Email found or Inactive mail");
				logger.info("User details not found or inActive.");
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				logger.info("Problem in Class - UserDaoImpl ~~ method-> verifyUserDtlsAndVerifyDefaultCode() - "
						+ ex.getMessage());
				conn.rollback();
				response = null;
				logStackTrace.accept(ex);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<Response<String>> resetPassword(ChangePasswordDto changePasswordDto) {
		ResponseEntity<Response<String>> responseEntity = null;
		Response<String> reponse = null;
		try {
			reponse = this.verifyTokenAndRestPassword(changePasswordDto);

			if (reponse == null) {
				responseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				responseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.OK);
			}

		} catch (Exception ex) {
			reponse = null;
			responseEntity = new ResponseEntity<Response<String>>(reponse, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.info("Problem in Class - UserServiceImpl ~~ method-> forgetPassword() - " + ex.getMessage());
			logStackTrace.accept(ex);
		}
		return responseEntity;
	}

	private Response<String> verifyTokenAndRestPassword(ChangePasswordDto changePasswordDto) {

		Connection conn = null;
		Response<String> response = new Response<String>();

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			UserDto userDto = userDao.getuserDtlsByMailId(conn, changePasswordDto.getEmail());

			if (userDto.getUserId() == null) {
				response.setKey(100);
				response.setMessage("Invalid Email");
				response.setValue("No Email found");
			} else if (StringUtils.equals("N", userDto.getIsActive())) {
				response.setKey(101);
				response.setMessage("Invalid");
				response.setValue("Inactive user");
			} else {

				boolean tokenVarified = userDao.verifyToken(conn, userDto.getUserId(), changePasswordDto.getToken());

				if (!tokenVarified) {
					response.setKey(104);
					response.setMessage("Failed");
					response.setValue("Time expired to update. Try again");
				} else {

					boolean isPasswordReseted = userDao.resetPassword(conn, userDto.getUserId(),
							changePasswordDto.getNewPassword(), userDto.getUserCode());

					if (!isPasswordReseted) {
						throw new DBReadWriteException("Some error has taken place while updating password");
					}

					response.setKey(200);
					response.setMessage("Success");
					response.setValue("Password reset process completed");
				}
			}

			conn.commit();
		} catch (Exception ex) {
			try {
				response = null;
				conn.rollback();
				logger.info("Problem in Class - UserServiceImpl ~~ method-> verifyTokenAndRestPassword() - "
						+ ex.getMessage());
				logStackTrace.accept(ex);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

}
