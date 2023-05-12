package com.capgemini.library.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		userRepository.save(user);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // (1)
		// 1. Load the user from the users table by username. If not found, throw
		// UsernameNotFoundException.
		Optional<User> optUser = userRepository.findByUsername(username);

		if (optUser.isEmpty())
			throw new UsernameNotFoundException("User '" + username + "' does not exist");

		User user = optUser.get();

		// 2. Convert/wrap the user to a UserDetails object and return it.
		return user;
	}
}