package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.capgemini.library.Library.model.Libro;
import com.capgemini.library.Library.service.LibroService;

@Controller
public class LibroController {

	@Autowired
	private LibroService libroService;

	@GetMapping("/libro/list")
	public String listarLibros(Model model) {
		List<Libro> libros = libroService.obtenerTodosLosLibros();
		model.addAttribute("libros", libros);

		return "listLibro";
	}
}
