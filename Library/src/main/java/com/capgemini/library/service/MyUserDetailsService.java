package com.capgemini.library.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Role;
import com.capgemini.library.repository.LectorRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	LectorRepository lectorRepository;

	@Autowired
	PasswordEncoder encoder;

	@PostConstruct
	public void initialiseDefaultUser() {
		if (lectorRepository.findByUsername("admin").isEmpty())
			lectorRepository.save(new Lector("admin", encoder.encode("1234"), Role.ROLE_ADMIN, "Administrador", "", "", ""));
	}

	public void createUser(Lector lector) {
		lector.setPassword(encoder.encode(lector.getPassword()));
		lector.setRole(Role.ROLE_USER);
		lectorRepository.save(lector);
	}

	public Lector findUserByUsername(String username) {
		List<Lector> lector = lectorRepository.findByUsername(username);
		if (lector.size() == 0)
			return null;
		return lector.get(0);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // (1)
		List<Lector> lectores = lectorRepository.findByUsername(username);
		if (lectores.size() == 0)
			throw new UsernameNotFoundException("User not found with username: " + username);
		Lector lector = lectores.get(0);

		Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(lector.getRole().name()));

		return new org.springframework.security.core.userdetails.User(lector.getUsername(), lector.getPassword(),
				lector.isEnabled(), true, true, true, authorities);
	}
}