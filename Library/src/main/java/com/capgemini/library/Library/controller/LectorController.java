package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.service.LectorService;

@Controller
public class LectorController {

	@Autowired
	private LectorService lectorService;

	@GetMapping("/lector/list")
	public String listarAutors(Model model) {
		List<Lector> lectors = lectorService.getAllLectores();
		model.addAttribute("lectores", lectors);

		return "listLector";
	}

	@GetMapping("/lector/create")
	public String getCreateLector(Model model) {
		initialiseLector(model);

		return "createLector";
	}

	@PostMapping("/lector/create")
	public String postCreateLector(Model model, @ModelAttribute Lector lector, BindingResult result) {
		if (result.hasErrors()) {
			initialiseLector(model);

			return "createLector";
		}

		// Validación para asegurarse de que no se creen autors duplicados
		if (lectorService.getLectorById(lector.getId()) != null) {
			initialiseLector(model);

			result.rejectValue("nombre", "error.lector", "Ya existe un lector con ese nombre");
			return "createAutor";
		}

		lectorService.save(lector);
		return "redirect:/lector/list";
	}

	private static void initialiseLector(Model model) {
		model.addAttribute("lector", new Lector());
	}

}
