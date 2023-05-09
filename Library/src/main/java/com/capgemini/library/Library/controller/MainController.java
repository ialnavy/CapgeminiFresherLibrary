package com.capgemini.library.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.capgemini.library.Library.service.LibroService;

@Controller
public class MainController {

	@Autowired
	LibroService libroservice;

	@GetMapping("/")
	public String index() {
		return "index";
	}

}
