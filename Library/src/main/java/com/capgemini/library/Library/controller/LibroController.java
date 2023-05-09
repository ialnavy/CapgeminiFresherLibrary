package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.capgemini.library.Library.model.Libro;
import com.capgemini.library.Library.model.TipoLibro;
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

	@GetMapping("/libro/create")
	public String getCreateLibro(Model model) {
		initialiseLibro(model);

		return "createLibro";
	}

	@PostMapping("/libro/create")
	public String postCreateLibro(Model model, @ModelAttribute Libro libro, BindingResult result) {
		if (result.hasErrors()) {
			initialiseLibro(model);
			
			return "createLibro";
		}

		// Validación para asegurarse de que no se creen libros duplicados
		if (libroService.obtenerLibroPorId(libro.getId()) != null) {
			initialiseLibro(model);
			
			result.rejectValue("nombre", "error.libro", "Ya existe un libro con ese nombre");
			return "createLibro";
		}

		libroService.añadirLibro(libro);
		return "redirect:/libro/list";
	}
	
	private static void initialiseLibro(Model model) {
		model.addAttribute("libro", new Libro());
		model.addAttribute("tiposDeLibro", List.of(TipoLibro.values()));
	}

}
