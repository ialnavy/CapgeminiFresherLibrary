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
import com.capgemini.library.model.Lector;
import com.capgemini.library.service.model.LectorService;

@Controller
public class LectorController {

	@Autowired
	private LectorService lectorService;

	@GetMapping("/lector")
	public String lector(Model model) {
		model.addAttribute("lector", new Lector());
		List<Lector> lectors = new ArrayList<>();
		try {
			lectors = lectorService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("todosLosLectores", lectors);

		return "crud/lector";
	}

	@PostMapping("/lector/create")
	public String createLector(Model model, @ModelAttribute Lector lector) {
		try {
			lectorService.create(lector);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/lector";
	}

	@PostMapping("/lector/edit")
	public String editLector(Model model, @ModelAttribute Lector givenLector, //
			@RequestParam(name = "lectorID", required = false) String lectorID) {
		if (lectorID == null || lectorID.length() == 0)
			return "redirect:/lector";

		givenLector.setId(lectorID);
		try {
			lectorService.update(givenLector);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/lector";
	}

	@GetMapping("/lector/delete/{lectorID}")
	public String deleteLector(Model model, @PathVariable(value = "lectorID") String lectorID) {
		try {
			lectorService.deleteById(lectorID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/lector";
	}

}
