package com.ccd.securityConfig;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.ccd.common.model.CustomUser;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		Map<String, Object> info = null;
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		boolean isTempUse = false;

		for (GrantedAuthority authoritie : authorities) {
			if (StringUtils.equalsIgnoreCase(authoritie.getAuthority(), "partial")) {
				isTempUse = true;
				break;
			}
		}

		if (!isTempUse) {

			CustomUser user = (CustomUser) authentication.getPrincipal();

			info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
			if (user.getId() != null)
				info.put("id", user.getId());
			if (user.getEmail() != null)
				info.put("email", user.getEmail());
			if (user.getIsDefaultPass() != null)
				info.put("default", user.getIsDefaultPass());
			if (user.getGrantedAuthoritiesIdList() != null)
				info.put("authoritiesIds", user.getGrantedAuthoritiesIdList());
			if (user.getUcode() != null)
				info.put("ucode", user.getUcode());
			if (user.getUname() != null)
				info.put("uname", user.getUname());
			if (StringUtils.equalsIgnoreCase("Y", user.getCheckDept()))
				info.put("checkedDept", user.getCheckDept());
			if (user.getDeptName() != null)
				info.put("department", user.getDeptName());
			if (user.getRole() != null)
				info.put("role", user.getRole());

			customAccessToken.setAdditionalInformation(info);
		}
		return super.enhance(customAccessToken, authentication);
	}

	/*
	 * @Override public OAuth2Authentication extractAuthentication(Map<String, ?>
	 * map) { OAuth2Authentication auth = super.extractAuthentication(map);
	 * AccessTokenMapper details = new AccessTokenMapper();
	 * 
	 * if (map.get("id") != null) details.setId((String) map.get("id"));
	 * 
	 * if (map.get("user_name") != null) details.setUserName((String)
	 * map.get("user_name"));
	 * 
	 * if (map.get("authoritiesIds") != null)
	 * details.setAuthoritiesIds((ArrayList<String>) map.get("authoritiesIds"));
	 * 
	 * auth.setDetails(details); return auth; }
	 */
}
