package com.capgemini.library.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.capgemini.library.Library.model.User;
import com.capgemini.library.Library.service.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	MyUserDetailsService userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/login")
	public String getLogin(Model model) {
		initialiseLogin(model);
		return "login";
	}

	@GetMapping("/logout")
	public String getLogout(HttpServletRequest request, Model model) {
		initialiseLogin(model);
		doLogout(request);
		return "redirect:/";
	}

	@PostMapping("/login")
	public String postLogin(HttpServletRequest request, Model model, @ModelAttribute User user, BindingResult result) {
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		} catch (UsernameNotFoundException e) {
			initialiseLogin(model);
			result.rejectValue("username", "error.user", "No existe ningún usuario con el nombre de usuario dado");
			return "redirect:/";
		}

		if (!userDetails.getPassword().equals(encoder.encode(user.getPassword()))) {
			initialiseLogin(model);
			result.rejectValue("password", "error.user", "La contraseña no es correcta");
			return "redirect:/";
		}

		doLogin(authenticationManager, request, userDetails.getUsername(), userDetails.getPassword());
		initialiseLogin(model);
		return "redirect:/";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		initialiseLogin(model);
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(Model model, @ModelAttribute User user, BindingResult result) {
		if (result.hasErrors()) {
			initialiseLogin(model);
			return "register";
		}

		if (user.getUsername() == null || user.getUsername().length() < 3) {
			initialiseLogin(model);
			result.rejectValue("username", "error.user",
					"El nombre de usuario debe tener una longitud mínima de 3 caracteres");
			return "register";
		}

		if (user.getPassword() == null || user.getPassword().length() < 3) {
			initialiseLogin(model);
			result.rejectValue("password", "error.user",
					"La contraseña debe tener una longitud mínima de 3 caracteres");
			return "register";
		}

		boolean existsUser;
		try {
			userDetailsService.loadUserByUsername(user.getUsername());
			existsUser = true;
		} catch (UsernameNotFoundException e) {
			existsUser = false;
		}
		if (existsUser) {
			initialiseLogin(model);
			result.rejectValue("username", "error.user", "Ya existe un usuario con ese nombre");
			return "register";
		}

		userDetailsService.createUser(user);
		return "redirect:/register";
	}

	private static void initialiseLogin(Model model) {
		model.addAttribute("user", new User());
	}

	private static void doLogin(AuthenticationManager authenticationManager, HttpServletRequest request,
			String userName, String password) {
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

		// Authenticate the user
		Authentication authentication = authenticationManager.authenticate(authRequest);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);

		// Create a new session and add the security context.
		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	}

	private static void doLogout(HttpServletRequest request) {
		// Authenticate the user
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(null);

		// Create a new session and add the security context.
		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	}

}
