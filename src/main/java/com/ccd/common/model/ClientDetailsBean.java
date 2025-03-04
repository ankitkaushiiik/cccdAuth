package com.ccd.common.model;

public class ClientDetailsBean {
	private String clientId;
	private String clientSecret;
	private String resourceIds;
	private String scope;
	private String authorizedGrantType;
	private String webServerRedirectURI;
	private String authorities;
	private Integer accessTokenValidity;
	private Integer refreshTokenValidity;
	private String additionalInformation;
	private String autoAppove;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizedGrantType() {
		return authorizedGrantType;
	}

	public void setAuthorizedGrantType(String authorizedGrantType) {
		this.authorizedGrantType = authorizedGrantType;
	}

	public String getWebServerRedirectURI() {
		return webServerRedirectURI;
	}

	public void setWebServerRedirectURI(String webServerRedirectURI) {
		this.webServerRedirectURI = webServerRedirectURI;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getAutoAppove() {
		return autoAppove;
	}

	public void setAutoAppove(String autoAppove) {
		this.autoAppove = autoAppove;
	}

}
