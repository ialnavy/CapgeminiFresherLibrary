package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.capgemini.library.Library.model.Autor;
import com.capgemini.library.Library.service.AutorService;

@Controller
public class AutorController {

	@Autowired
	private AutorService autorService;

	@GetMapping("/autor/list")
	public String listarAutors(Model model) {
		List<Autor> autors = autorService.findAll();
		model.addAttribute("autores", autors);

		return "listAutor";
	}

	@GetMapping("/autor/create")
	public String getCreateAutor(Model model) {
		initialiseAutor(model);

		return "createAutor";
	}

	@PostMapping("/autor/create")
	public String postCreateAutor(Model model, @ModelAttribute Autor autor, BindingResult result) {
		if (result.hasErrors()) {
			initialiseAutor(model);

			return "createAutor";
		}

		// Validación para asegurarse de que no se creen autors duplicados
		if (autorService.findById(autor.getId()) != null) {
			initialiseAutor(model);

			result.rejectValue("nombre", "error.autor", "Ya existe un autor con ese nombre");
			return "createAutor";
		}

		autorService.create(autor);
		return "redirect:/autor/list";
	}

	private static void initialiseAutor(Model model) {
		model.addAttribute("autor", new Autor());
	}

}
