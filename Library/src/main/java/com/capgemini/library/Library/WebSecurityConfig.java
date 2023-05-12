package com.capgemini.library.Library;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.capgemini.library.Library.service.MyUserDetailsService;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	@Value("${spring.websecurity.debug:false}")
	boolean webSecurityDebug;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests() //
				.requestMatchers(HttpMethod.GET, "/").permitAll() //
				.requestMatchers(HttpMethod.GET, "/register").permitAll() //
				.requestMatchers(HttpMethod.GET, "/login").permitAll() //
				.requestMatchers(HttpMethod.POST, "/login").permitAll() //
				.requestMatchers(HttpMethod.GET, "/logout").permitAll() //
				.anyRequest().authenticated() //
				.and().csrf().disable() //
		;
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new Sha512PasswordEncoder();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.debug(webSecurityDebug);
	}

}
