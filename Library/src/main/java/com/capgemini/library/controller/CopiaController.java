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
import com.capgemini.library.model.Copia;
import com.capgemini.library.model.Libro;
import com.capgemini.library.service.model.CopiaService;
import com.capgemini.library.service.model.LibroService;

@Controller
public class CopiaController {

	@Autowired
	private CopiaService copiaService;

	@Autowired
	private LibroService libroService;

	@GetMapping("/copia")
	public String copia(Model model) {
		model.addAttribute("copia", new Copia());
		List<Libro> libros = new ArrayList<>();
		try {
			libros = libroService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("todosLosLibros", libros);
		List<Copia> copias = new ArrayList<>();
		try {
			copias = copiaService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("todasLasCopias", copias);
		return "crud/copia";
	}

	@PostMapping("/copia/create")
	public String createCopia(Model model, @ModelAttribute Copia copia, @RequestParam("libroID") String libroID) {
		try {
			copiaService.create(copia);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}

		try {
			copiaService.linkCopiaToLibro(copia.getId(), libroID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}

		return "redirect:/copia";
	}

	@PostMapping("/copia/edit")
	public String editCopia(Model model, @ModelAttribute Copia pojo, @RequestParam("copiaID") String copiaID) {
		pojo.setId(copiaID);
		try {
			copiaService.update(pojo);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}

		return "redirect:/copia";
	}

	@GetMapping("/copia/delete/{copiaID}")
	public String deleteCopia(Model model, @PathVariable(value = "copiaID") String copiaID) {
		try {
			copiaService.deleteById(copiaID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/copia";
	}

}
