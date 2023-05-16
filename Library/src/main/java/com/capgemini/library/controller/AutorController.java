package com.capgemini.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Autor;
import com.capgemini.library.service.model.AutorService;

@Controller
public class AutorController {

	@Autowired
	private AutorService autorService;

	@GetMapping("/autor")
	public String autor(Model model) {
		model.addAttribute("autor", new Autor());
		List<Autor> autors = new ArrayList<>();
		try {
			autors = autorService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("todosLosAutores", autors);

		return "crud/autor";
	}

	@PostMapping("/autor/create")
	public String createAutor(Model model, @ModelAttribute Autor autor) {
		try {
			autorService.create(autor);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/autor";
	}

	@PostMapping("/autor/edit")
	public String editAutor(Model model, @ModelAttribute Autor givenAutor, BindingResult result,
			@RequestParam("autorID") String autorID) {
		givenAutor.setId(autorID);
		try {
			autorService.update(givenAutor);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/autor";
	}

	@GetMapping("/autor/delete/{autorID}")
	public String deleteAutor(Model model, @PathVariable(value = "autorID") String autorID) {
		if (autorID == null || autorID.length() == 0)
			return "redirect:/autor";

		try {
			autorService.deleteById(autorID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/autor";
	}

}
