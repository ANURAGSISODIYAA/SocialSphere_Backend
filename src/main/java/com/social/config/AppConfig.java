package com.social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						Authorize -> Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
				.addFilterBefore(new jwtValidator(), BasicAuthenticationFilter.class).csrf(csrf -> csrf.disable());
		return http.build();

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

/*
 * Flow: When a request comes in, it goes through the security filter chain
 * configured in AppConfig.
 * 
 * The jwtValidator filter intercepts the request to validate the JWT token.
 * 
 * If a valid token is found, the user's email is extracted and set in the
 * security context.
 * 
 * The CustomUserDetailService is then called to load user details based on the
 * email extracted from the token.
 * 
 * If the user is found and authenticated successfully, the request proceeds to
 * the controller layer for further processing.
 * 
 * This setup ensures that incoming requests are authenticated using JWT tokens,
 * and the appropriate user details are loaded for further authorization and
 * processing within the application.
 */
