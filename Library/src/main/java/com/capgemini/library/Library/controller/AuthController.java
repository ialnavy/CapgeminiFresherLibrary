package com.capgemini.library.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class AuthController {

	@Autowired
	private MyUserDetailsService lectorService;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/login")
	public String getLogin(Model model) {
		initialiseLector(model);
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(Model model, @ModelAttribute User user, BindingResult result) {
		System.out.println("AAAAAAAA");
		UserDetails userDetails;
		try {
			userDetails = lectorService.loadUserByUsername(user.getUsername());
		} catch (UsernameNotFoundException e) {
			initialiseLector(model);
			result.rejectValue("username", "error.user", "No existe ningún usuario con el nombre de usuario dado");
			return "redirect:/";
		}

		if (!userDetails.getPassword().equals(encoder.encode(user.getPassword()))) {
			initialiseLector(model);
			result.rejectValue("password", "error.user", "La contraseña no es correcta");
			return "redirect:/";
		}

		initialiseLector(model);
		return "redirect:/";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		initialiseLector(model);
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(Model model, @ModelAttribute User user, BindingResult result) {
		if (result.hasErrors()) {
			initialiseLector(model);
			return "register";
		}

		if (user.getUsername() == null || user.getUsername().length() < 3) {
			initialiseLector(model);
			result.rejectValue("username", "error.user",
					"El nombre de usuario debe tener una longitud mínima de 3 caracteres");
			return "register";
		}

		if (user.getPassword() == null || user.getPassword().length() < 3) {
			initialiseLector(model);
			result.rejectValue("password", "error.user",
					"La contraseña debe tener una longitud mínima de 3 caracteres");
			return "register";
		}

		boolean existsUser;
		try {
			lectorService.loadUserByUsername(user.getUsername());
			existsUser = true;
		} catch (UsernameNotFoundException e) {
			existsUser = false;
		}
		if (existsUser) {
			initialiseLector(model);
			result.rejectValue("username", "error.user", "Ya existe un usuario con ese nombre");
			return "register";
		}

		lectorService.createUser(user);
		return "redirect:/register";
	}

	private static void initialiseLector(Model model) {
		model.addAttribute("user", new User());
	}

}
