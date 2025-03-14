package com.ccd.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ccd.common.serviceImpl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//http.csrf().disable().authorizeRequests().antMatchers("/oauth/token/").permitAll().anyRequest().authenticated().and()
		//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

		

		
		// http.csrf().disable().authorizeRequests().antMatchers("/registration/**").
		// permitAll().anyRequest()
		// .authenticated().and().sessionManagement().sessionCreationPolicy(
		 // SessionCreationPolicy.NEVER);
		 

		/*
		 * http.csrf().disable().authorizeRequests().antMatchers("/registration/**").
		 * permitAll().anyRequest()
		 * .authenticated().and().sessionManagement().sessionCreationPolicy(
		 * SessionCreationPolicy.NEVER);
		 */

		/*
		 * http.csrf().disable().authorizeRequests().antMatchers("/user/**").permitAll()
		 * .anyRequest().authenticated().and()
		 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		 */
		
		  http.csrf().disable().authorizeRequests().antMatchers("/user/**").permitAll()
		  .anyRequest().authenticated().and()
		  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
		 

	}
}
