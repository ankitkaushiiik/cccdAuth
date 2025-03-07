package com.ccd.common.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.ccd.common.dao.OAuthDAOService;
import com.ccd.common.model.ClientDetailsBean;
import com.ccd.common.model.UserEntity;
import com.ccd.common.utility.PersonalUtility;

@Repository
public class OAuthDAOServiceImpl extends CommonDaoImpl implements OAuthDAOService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PersonalUtility personalUtility;

	@Override
	public ClientDetailsBean getClintDetails(String clientId) {

		Connection conn = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		ClientDetailsBean clientDetailsBean = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT CLIENT_ID, CLIENT_SECRET, RESOURCE_IDS, SCOPE, AUTHORIZED_GRANT_TYPES,";
			sql += " WEB_SERVER_REDIRECT_URI, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY,";
			sql += " ADDITIONAL_INFORMATION, AUTOAPPROVE FROM ccd_oauth_client_details";
			sql += " WHERE CLIENT_ID = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, clientId);

			rs = ps.executeQuery();

			if (rs.next()) {
				clientDetailsBean = new ClientDetailsBean();
				clientDetailsBean.setClientId(rs.getString("CLIENT_ID"));
				clientDetailsBean.setClientSecret(rs.getString("CLIENT_SECRET"));
				clientDetailsBean.setResourceIds(rs.getString("RESOURCE_IDS"));
				clientDetailsBean.setScope(rs.getString("SCOPE"));
				clientDetailsBean.setAuthorizedGrantType(rs.getString("AUTHORIZED_GRANT_TYPES"));
				clientDetailsBean.setWebServerRedirectURI(rs.getString("WEB_SERVER_REDIRECT_URI"));
				clientDetailsBean.setAuthorities(rs.getString("AUTHORITIES"));
				clientDetailsBean.setAccessTokenValidity(rs.getInt("ACCESS_TOKEN_VALIDITY"));
				clientDetailsBean.setRefreshTokenValidity(rs.getInt("REFRESH_TOKEN_VALIDITY"));
				clientDetailsBean.setAdditionalInformation(rs.getString("ADDITIONAL_INFORMATION"));
				clientDetailsBean.setAutoAppove(rs.getString("AUTOAPPROVE"));
			}

			conn.commit();
		} catch (Exception e) {
			try {
				e.printStackTrace();
				conn.rollback();
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

		return clientDetailsBean;
	}

	@Override
	public UserEntity getUserDetails(String userId) {

		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		UserEntity userEntity = null;
		ArrayList<String> grantedAuthoritiesIdList = new ArrayList<>();

		try {

			conn = dataSource.getConnection();

			sql = "SELECT um.user_id, um.user_code, um.email_id, um.user_name, ua.is_default_pass";
			sql += ", AES_DECRYPT(user_pwd, UNHEX(SHA2(HEX(CONCAT(um.user_code, 'CCD_ENCRYPTION')), 256))) AS pwd,";
			sql += " (SELECT dept_name FROM ccd_department_master WHERE dept_id = um.dept_id) AS dept";
			sql += " FROM ccd_user_master um, ccd_user_authentication ua";
			sql += " WHERE um.user_id = ua.user_id";
			sql += " AND um.is_active = ?";

			if (personalUtility.validateEmail(userId)) {
				sql += " AND um.email_id = ?";
			} else {
				sql += " AND um.user_code = ?";
			}

			ps = conn.prepareStatement(sql);
			ps.setString(1, "Y");
			ps.setString(2, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
				userEntity = new UserEntity();
				userEntity.setUserId(rs.getString("user_id"));
				userEntity.setUserCode(rs.getString("user_code"));
				userEntity.setEmailId(rs.getString("email_id"));
				userEntity.setUserName(rs.getString("user_name"));
				userEntity.setPassword(passwordEncoder.encode(rs.getString("pwd")));
				userEntity.setIsDefaultPass(
						rs.getString("is_default_pass").equalsIgnoreCase("N") ? null : rs.getString("is_default_pass"));
				userEntity.setDeptName(rs.getString("dept"));
			}

			if (userEntity != null) {

				ps.clearParameters();

				sql = "SELECT rm.role_id, rm.role_name, rm.is_check_dept";
				sql += " FROM ccd_role_master rm, ccd_role_user_mapping rumap,";
				sql += " ccd_user_master um";
				sql += " WHERE rumap.role_id = rm.role_id";
				sql += " AND um.user_id = rumap.user_id";
				sql += " AND rm.is_active = ?";
				sql += " AND rumap.is_active = ?";
				sql += " AND um.user_id = ?";

				ps = conn.prepareStatement(sql);
				ps.setString(1, "Y");
				ps.setString(2, "Y");
				ps.setString(3, userEntity.getUserId());

				rs = ps.executeQuery();

				if (rs.next()) {
					grantedAuthoritiesIdList.add(rs.getString("role_id"));
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + rs.getString("role_name"));
					grantedAuthoritiesList.add(grantedAuthority);
					userEntity.setCheckDept(rs.getString("is_check_dept"));
					userEntity.setRole(rs.getString("role_name"));
				}
				userEntity.setGrantedAuthoritiesList(grantedAuthoritiesList);
				userEntity.setGrantedAuthoritiesIdList(grantedAuthoritiesIdList);
			}

			conn.commit();

		} catch (Exception e) {
			try {
				userEntity = null;
				e.printStackTrace();
				conn.rollback();
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

		return userEntity;
	}

}
