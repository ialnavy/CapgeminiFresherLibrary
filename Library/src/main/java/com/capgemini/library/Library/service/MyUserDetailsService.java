package com.capgemini.library.Library.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Role;
import com.capgemini.library.Library.model.User;
import com.capgemini.library.Library.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@PostConstruct
	public void initialiseDefaultUser() {
		if (userRepository.findByUsername("admin").isEmpty())
			userRepository.save(new User("admin", encoder.encode("1234"), Role.ROLE_ADMIN));
	}

	public void createUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(Role.ROLE_USER);
		userRepository.save(user);
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // (1)
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getRole().name()));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, true, authorities);
	}
}