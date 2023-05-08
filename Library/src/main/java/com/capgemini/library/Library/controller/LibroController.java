package com.capgemini.library.Library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibroController {

	@GetMapping("/libro/list")
	public String listarLibros(Model model) {
//		List<Libro> libros = libroService.readAll();
//		model.addAttribute("libros", libros);

		return "listLibro";
	}
}
