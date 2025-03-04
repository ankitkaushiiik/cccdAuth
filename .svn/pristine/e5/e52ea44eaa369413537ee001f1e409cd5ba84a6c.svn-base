package com.ccd.common.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ccd.common.dao.UserDao;
import com.ccd.common.dto.UserDto;
import com.ccd.common.utility.LogStackTrace;

@Repository
public class UserDaoImpl extends CommonDaoImpl implements UserDao {

	static Logger logger = LogManager.getLogger(UserDaoImpl.class);

	private final LogStackTrace logStackTrace;

	@Autowired
	public UserDaoImpl(LogStackTrace logStackTrace) {
		this.logStackTrace = logStackTrace;
	}

	@Override
	public UserDto getuserDtls(String email) {
		Connection conn = null;
		UserDto userDto = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			userDto = getuserDtlsByMailId(conn, email);

			conn.commit();
		} catch (Exception ex) {
			try {
				userDto = null;
				logger.info("Problem in Class - UserDaoImpl ~~ method-> varifyUser() - " + ex.getMessage());
				conn.rollback();
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
		return userDto;
	}

	@Override
	public UserDto getuserDtlsByMailId(Connection conn, String email) throws Exception {
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		UserDto userDto = new UserDto();

		sql = "SELECT user_id, user_code, user_name, is_active";
		sql += " FROM ccd_user_master";
		sql += " WHERE email_id = ?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, email);
		rs = ps.executeQuery();

		if (rs.next()) {
			userDto.setUserId(rs.getInt("user_id"));
			userDto.setUserCode(rs.getString("user_code"));
			userDto.setEmail(email);
			userDto.setUserName(rs.getString("user_name"));
			userDto.setIsActive(rs.getString("is_active"));
		}

		return userDto;
	}

	@Override
	public Boolean saveDefaultCodeDtls(Connection conn, Integer userId, String otpText, String token) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		boolean otpSaved = false;
		int cntRecordInOtp = 0;
		int validForTimeInMin = 10;

		sql = "SELECT COUNT(1) AS cnt FROM ccd_forget_pwd_code WHERE user_id = ?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, userId);

		rs = ps.executeQuery();

		if (rs.next()) {
			cntRecordInOtp = rs.getInt("cnt");
		}

		ps.clearParameters();

		if (cntRecordInOtp > 0) {

			sql = "DELETE FROM ccd_forget_pwd_code WHERE user_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);

			ps.executeUpdate();
			ps.clearParameters();
		}

		sql = "INSERT INTO ccd_forget_pwd_code(user_id, forget_pwd_text, valid_for, token,";
		sql += " is_active, created_by, created_on, modified_by)";
		sql += " VALUES  (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP,?)";

		ps = conn.prepareStatement(sql);

		ps.setInt(1, userId);
		ps.setString(2, otpText);
		ps.setInt(3, validForTimeInMin);
		ps.setString(4, token);
		ps.setString(5, "Y");
		ps.setInt(6, userId);
		ps.setInt(7, userId);

		int rowsInserted = ps.executeUpdate();

		if (rowsInserted == 1) {
			otpSaved = true;
		}

		return otpSaved;
	}

	@Override
	public String verifyDefaultCodeAndGetToken(Connection conn, Integer userId, String otp) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		String token = null;

		sql = "SELECT token FROM ccd_forget_pwd_code WHERE user_id = ? AND forget_pwd_text = ? AND is_token_sent = ?";
		sql += " AND MINUTE(TIMEDIFF(NOW(),created_on)) < valid_for";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, userId);		
		ps.setString(2, otp);
		ps.setString(3, "N");

		rs = ps.executeQuery();

		if (rs.next()) {
			token = rs.getString("token");
		}

		return token;
	}

	@Override
	public Boolean updateTokenSentStatus(Connection conn, Integer userId) throws Exception {
		String sql = null;
		PreparedStatement ps = null;
		boolean isUpdated = false;
		int updatedRow = 0;

		sql = "UPDATE ccd_forget_pwd_code SET";
		sql += " is_token_sent = ?,";
		sql += " modified_by = ?,";
		sql += " modified_on = CURRENT_TIMESTAMP";
		sql += " WHERE user_id = ?";

		ps = conn.prepareStatement(sql);

		ps.setString(1, "Y");
		ps.setInt(2, userId);
		ps.setInt(3, userId);

		updatedRow = ps.executeUpdate();

		if (updatedRow == 1) {
			isUpdated = true;
		}

		return isUpdated;
	}

	@Override
	public Boolean verifyToken(Connection conn, Integer userId, String token) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		boolean isValid = false;
		int cnt = 0;

		sql = "SELECT COUNT(1) as cnt FROM ccd_forget_pwd_code WHERE user_id = ? AND token = ?";
		sql += " AND MINUTE(TIMEDIFF(NOW(),created_on)) < 15";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, userId);
		ps.setString(2, token);

		rs = ps.executeQuery();

		if (rs.next()) {
			cnt = rs.getInt("cnt");
		}

		if (cnt == 1) {
			isValid = true;
		}

		return isValid;
	}

	@Override
	public Boolean resetPassword(Connection conn, Integer userId, String newPassword, String userCode)
			throws Exception {

		String sql = null;
		PreparedStatement ps = null;
		boolean passwordChanged = false;
		int updatedRow = 0;

		sql = "UPDATE ccd_user_authentication SET";
		sql += " user_pwd = AES_ENCRYPT(?, UNHEX(SHA2(HEX(CONCAT(?, 'CCD_ENCRYPTION')), 256))),";
		sql += " user_pwd_txt = ?,";
		sql += " modified_by = ?,";
		sql += " modified_on = CURRENT_TIMESTAMP";
		sql += " WHERE user_id = ?";

		ps = conn.prepareStatement(sql);

		ps.setString(1, newPassword);
		ps.setString(2, userCode);
		ps.setString(3, newPassword);
		ps.setInt(4, userId);
		ps.setInt(5, userId);

		updatedRow = ps.executeUpdate();

		if (updatedRow == 1) {
			passwordChanged = true;
		}

		return passwordChanged;
	}

}
