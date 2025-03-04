package com.ccd.securityConfig;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import com.ccd.common.dao.OAuthDAOService;
import com.ccd.common.model.ClientDetailsBean;

public class CustomeClientDetailsService implements ClientDetailsService {

	static Logger logger = LogManager.getLogger(CustomeClientDetailsService.class);

	// private JsonMapper mapper = createJsonMapper();

	@Autowired
	private OAuthDAOService oauthDaoService;

	@Override
	public ClientDetails loadClientByClientId(String clientId) {

		ClientDetailsBean clientDetails = oauthDaoService.getClintDetails(clientId);

		if (clientDetails != null) {

			BaseClientDetails details = new BaseClientDetails(clientDetails.getClientId(),
					clientDetails.getResourceIds(), clientDetails.getScope(), clientDetails.getAuthorizedGrantType(),
					clientDetails.getAuthorities(), clientDetails.getWebServerRedirectURI());
			details.setClientSecret(clientDetails.getClientSecret());
			if (clientDetails.getAccessTokenValidity() != null) {
				details.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValidity());
			}
			if (clientDetails.getRefreshTokenValidity() != null) {
				details.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValidity());
			}
			String json = clientDetails.getAdditionalInformation();
			if (json != null) {
				try {
					// @SuppressWarnings("unchecked")
					// Map<String, Object> additionalInformation = mapper.read(json, Map.class);
					Map<String, Object> additionalInformation = null;
					details.setAdditionalInformation(additionalInformation);
				} catch (Exception e) {
					logger.info("Could not decode JSON for additional information: " + details, e);
				}
			}
			String scopes = clientDetails.getAutoAppove();
			if (scopes != null) {
				details.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
			}
			return details;
		}

		throw new ClientRegistrationException("Client Not Found");

	}

	/*
	 * private JsonMapper createJsonMapper() { if
	 * (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
	 * return new JacksonMapper(); } else if
	 * (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
	 * return new Jackson2Mapper(); } return new NotSupportedJsonMapper(); }
	 */

}
