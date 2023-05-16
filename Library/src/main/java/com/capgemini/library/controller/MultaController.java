package com.capgemini.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Multa;
import com.capgemini.library.service.MultaService;
import com.capgemini.library.service.model.impl.LectorServiceImp;

@Controller
public class MultaController {

	@Autowired
	private LectorServiceImp lectorService;

	@Autowired
	private MultaService multaService;

	@GetMapping("/multa")
	public String multaForm(Model model) {
		List<Lector> lectores = new ArrayList<>();
		try {
			lectores = lectorService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("lectores", lectores);
		return "multa";
	}

	@PostMapping("/multa")
	public String multarLector(@RequestParam("lectorId") String lectorId, @RequestParam("dias") int dias, Model model) {
		List<Lector> lectores = new ArrayList<>();
		try {
			lectores = lectorService.readAll();
		} catch (ServiceException se) {
		}

		Lector lector = null;
		try {
			lector = lectorService.readById(lectorId);
		} catch (ServiceException se) {
		}

		Multa multa = lector.multar(dias);
		multa.setLector(lector);
		multaService.save(multa);
		model.addAttribute("message", "Lector multado con éxito");

		model.addAttribute("lectores", lectores);
		return "multa";
	}
}
