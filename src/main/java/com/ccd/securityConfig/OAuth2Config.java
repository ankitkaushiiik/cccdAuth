package com.ccd.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder).tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(cds());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(converter());
	}

	@Bean
	public CustomeClientDetailsService cds() {
		return new CustomeClientDetailsService();
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(converter());
	}

	@Bean
	public JwtAccessTokenConverter converter() {
		JwtAccessTokenConverter conv = new CustomTokenEnhancer();
		KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(new ClassPathResource("cccda.elogixsoft.com.jks"),
				"nLkLZ4?2BgXr9(wJ".toCharArray());
		conv.setKeyPair(keyFactory.getKeyPair("cccda"));
		return conv;
	}

	/*
	 * @Bean public JwtTokenStore tokenStore() { return new
	 * JwtTokenStore(tokenEnhancer()); }
	 */

	
	/*
	 * @Bean public JwtAccessTokenConverter tokenEnhancer() {
	 * JwtAccessTokenConverter converter = new CustomTokenEnhancer();
	 * converter.setSigningKey(privateKey); converter.setVerifierKey(publicKey);
	 * return converter; }
	 */
	 
}
