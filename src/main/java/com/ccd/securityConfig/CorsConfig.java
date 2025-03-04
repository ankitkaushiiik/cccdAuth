package com.ccd.securityConfig;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ccd.constantReader.ConstantReader;

@Configuration
public class CorsConfig {

	 private String allowOrigin = "*";

// 	@Bean
// 	public FilterRegistrationBean<CorsFilter> corsFilter() {
// 		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

// 		CorsConfiguration configAutenticacao = new CorsConfiguration();
// 		configAutenticacao.setAllowCredentials(true);
// 		 configAutenticacao.addAllowedOrigin(allowOrigin);
// 		// configAutenticacao.setAllowedOrigins(Arrays.asList("http://192.168.1.71:4200",
// 		// "http://172.16.1.24:4200"));
// 		//configAutenticacao.setAllowedOrigins(Arrays.asList(ConstantReader.FRONTEND_URL.split(", ")));
// 		configAutenticacao.addAllowedHeader("Access-Control-Allow-Origin: Authorization");
// configAutenticacao.addAllowedHeader("Access-Control-Allow-Origin: Content-Type");
// configAutenticacao.addAllowedHeader("Access-Control-Allow-Origin: Accept");

// 		configAutenticacao.addAllowedMethod("POST");
// 		configAutenticacao.addAllowedMethod("GET");
// 		configAutenticacao.addAllowedMethod("DELETE");
// 		configAutenticacao.addAllowedMethod("PUT");
// 		configAutenticacao.addAllowedMethod("OPTIONS");
// 		// configAutenticacao.setMaxAge(3600L);
// 		// source.registerCorsConfiguration("/oauth/token", configAutenticacao);
// 		source.registerCorsConfiguration("/**", configAutenticacao); // Global for all paths

// 		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
// 		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
// 		return bean;
// 	}
	// To handle cross-origin
	
	  @Bean public FilterRegistrationBean<CorsFilter> corsFilter() {
	  UrlBasedCorsConfigurationSource source = new
	UrlBasedCorsConfigurationSource();
	  
	  CorsConfiguration configAutenticacao = new CorsConfiguration();
	  configAutenticacao.setAllowCredentials(true);
	  configAutenticacao.addAllowedOrigin(allowOrigin);
	  configAutenticacao.addAllowedHeader("Authorization");
	  configAutenticacao.addAllowedHeader("Content-Type");
	  configAutenticacao.addAllowedHeader("Accept");
	  configAutenticacao.addAllowedMethod("POST");
	  configAutenticacao.addAllowedMethod("GET");
	  configAutenticacao.addAllowedMethod("DELETE");
	  configAutenticacao.addAllowedMethod("PUT");
	  configAutenticacao.addAllowedMethod("OPTIONS");
	  configAutenticacao.setMaxAge(3600L); //
	 source.registerCorsConfiguration("/oauth/token", configAutenticacao);
	  source.registerCorsConfiguration("/**", configAutenticacao); // Global forall paths
	 
	 FilterRegistrationBean<CorsFilter> bean = new
	  FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
	  bean.setOrder(Ordered.HIGHEST_PRECEDENCE); return bean; }
	 
}
