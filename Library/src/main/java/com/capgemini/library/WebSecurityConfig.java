package com.capgemini.library;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new Sha512PasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(withDefaults()).authorizeHttpRequests((requests) -> requests //
				.requestMatchers("/register/**").permitAll() //
				.requestMatchers("/login/**").permitAll() //
				.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") //
				.requestMatchers("/admin/**").hasAnyRole("ADMIN") //
				.anyRequest().authenticated() //
		) //
				.formLogin((form) -> form //
						.loginPage("/login") //
						.loginProcessingUrl("/login") //
						.defaultSuccessUrl("/user/") //
						.permitAll() //
				) //
				.logout((logout) -> logout //
						.permitAll() //
				).exceptionHandling(handling -> handling //
						.accessDeniedPage("/access-denied") //
				);
		return http.build();
	}
}