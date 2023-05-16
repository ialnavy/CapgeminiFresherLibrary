package com.capgemini.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Autor;
import com.capgemini.library.model.Libro;
import com.capgemini.library.model.TipoLibro;
import com.capgemini.library.service.model.AutorService;
import com.capgemini.library.service.model.LibroService;

@Controller
public class LibroController {

	@Autowired
	private AutorService autorService;

	@Autowired
	private LibroService libroService;

	@GetMapping("/libro")
	public String libro(Model model) {
		List<Libro> libros = new ArrayList<>();
		try {
			libros = libroService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("todosLosLibros", libros);
		model.addAttribute("libro", new Libro());
		model.addAttribute("tiposDeLibro", List.of(TipoLibro.values()));
		List<Autor> autors = new ArrayList<>();
		try {
			autors = autorService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("autores", autors);

		return "crud/libro";
	}

	@PostMapping("/libro/create")
	public String createLibro(Model model, @ModelAttribute Libro libro, //
			@RequestParam(name = "autorID", required = false) String autorID) {
		if (autorID == null || autorID.length() == 0)
			return "redirect:/libro";
		
		boolean preconditions = false;
		try {
			preconditions = libroService.isCreable(libro, autorID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		if (!preconditions)
			return "redirect:/libro";
		
		try {
			libroService.create(libro);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}

		try {
			libroService.linkLibroToAutor(libro.getId(), autorID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}

		return "redirect:/libro";
	}

	@PostMapping("/libro/edit")
	public String editLibro(Model model, @ModelAttribute Libro pojo, //
			@RequestParam(name = "libroID", required = false) String libroID, //
			@RequestParam(name = "autorID", required = false) String autorID) {
		if (libroID == null || libroID.length() == 0 //
				|| autorID == null || autorID.length() == 0)
			return "redirect:/libro";

		pojo.setId(libroID);

		try {
			libroService.update(pojo);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}

		try {
			libroService.linkLibroToAutor(libroID, autorID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}

		return "redirect:/libro";
	}

	@GetMapping("/libro/delete/{libroID}")
	public String deleteLibro(Model model, @PathVariable(value = "libroID") String libroID) {
		try {
			libroService.deleteById(libroID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/libro";
	}

}
