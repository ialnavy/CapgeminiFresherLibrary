package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.Library.model.Libro;
import com.capgemini.library.Library.model.TipoLibro;
import com.capgemini.library.Library.service.AutorService;
import com.capgemini.library.Library.service.LibroService;

@Controller
public class LibroController {

	@Autowired
	private AutorService autorService;

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
		initialiseLibro(model, autorService);

		return "createLibro";
	}

	@PostMapping("/libro/create")
	public String postCreateLibro(Model model, @ModelAttribute Libro libro, BindingResult result,
			@RequestParam("autorID") String autorID) {
		if (result.hasErrors()) {
			initialiseLibro(model, autorService);

			return "createLibro";
		}

		// Validación para asegurarse de que no se creen libros duplicados
		if (libroService.obtenerLibroPorId(libro.getId()) != null) {
			initialiseLibro(model, autorService);

			result.rejectValue("nombre", "error.libro", "Ya existe un libro con ese nombre");
			return "createLibro";
		}
		libro.setAutor(autorService.findById(autorID));
		libroService.añadirLibro(libro);
		return "redirect:/libro/list";
	}
	

	private void initialiseLibro(Model model, AutorService autorService) {
		model.addAttribute("libro", new Libro());
		model.addAttribute("tiposDeLibro", List.of(TipoLibro.values()));
		model.addAttribute("autores", autorService.findAll());
	}

}
