package com.capgemini.library.Library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.capgemini.library.Library.service.MyUserDetailsService;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()//
				.requestMatchers(HttpMethod.GET, "/").permitAll()//
				.requestMatchers(HttpMethod.GET, "/register").permitAll() //
				.requestMatchers(HttpMethod.GET, "/login").permitAll() //
				.requestMatchers(HttpMethod.POST, "/login").permitAll() //
				.anyRequest().authenticated() //
		;
		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new Sha512PasswordEncoder();
	}

}
