package com.capgemini.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import com.capgemini.library.model.Lector;
import com.capgemini.library.service.MyUserDetailsService;

@Controller
public class AuthController {

	@Autowired
	MyUserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/login")
	public String getLogin(Model model) {
		initialiseLogin(model);
		return "login";
	}

	@GetMapping("/logout")
	public String getLogout(Model model) {
		initialiseLogin(model);
		SecurityContextHolder.getContext().setAuthentication(null);
		return "redirect:/";
	}

	@PostMapping("/login")
	public String postLogin(Model model, @ModelAttribute Lector user, BindingResult result) {
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

		Authentication authentication = new AnonymousAuthenticationToken(userDetails.getUsername(),
				userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		initialiseLogin(model);
		return "redirect:/";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		initialiseLogin(model);
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(Model model, @ModelAttribute Lector user, BindingResult result) {
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
		model.addAttribute("user", new Lector());
	}

}
