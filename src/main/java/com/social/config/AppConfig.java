package com.social.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		    http.sessionManagement(management -> management
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(Authorize -> Authorize.requestMatchers("/api/**")
			    .authenticated()
			    .anyRequest()
			    .permitAll())
				.addFilterBefore(new jwtValidator(), BasicAuthenticationFilter.class)
				.csrf(csrf -> csrf.disable())
				.cors(cors->cors.configurationSource( corsConfigurationSource() ) );
		    
		    
		return http.build();

	}

	private CorsConfigurationSource corsConfigurationSource() {
	
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
			    CorsConfiguration cfg = new CorsConfiguration();
			    cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000",
			    		"https://socialspherecom-anurag-sisodiyas-projects.vercel.app"));
			    cfg.setAllowedMethods(Collections.singletonList("*"));
			    cfg.setAllowCredentials(true);
			    cfg.setAllowedHeaders(Collections.singletonList("*"));
			    cfg.setExposedHeaders(Arrays.asList("Authorization"));
			    cfg.setMaxAge(3600L);
				return cfg;
			}
		};
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
