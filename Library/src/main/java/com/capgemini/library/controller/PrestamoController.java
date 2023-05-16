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
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Prestamo;
import com.capgemini.library.service.model.CopiaService;
import com.capgemini.library.service.model.LectorService;
import com.capgemini.library.service.model.PrestamoService;

@Controller
public class PrestamoController {

	@Autowired
	private LectorService lectorService;

	@Autowired
	private CopiaService copiaService;

	@Autowired
	private PrestamoService prestamoService;

	@GetMapping("/prestamo")
	public String prestamo(Model model) {
		List<Lector> lectores = new ArrayList<>();
		List<Copia> copias = new ArrayList<>();
		List<Prestamo> prestamos = new ArrayList<>();
		try {
			lectores = lectorService.readAll();
			copias = copiaService.findAllNoAlquiladas();
			prestamos = prestamoService.readAll();
		} catch (ServiceException se) {
		}

		model.addAttribute("prestamo", new Prestamo());
		model.addAttribute("todosLosLectores", lectores);
		model.addAttribute("todasLasCopias", copias);
		model.addAttribute("tododsLosPrestamos", prestamos);
		return "crud/prestamo";
	}

	@PostMapping("/prestamo/create")
	public String createPrestamo(@ModelAttribute Prestamo prestamo, //
			@RequestParam("lectorID") String lectorID, //
			@RequestParam("copiaID") String copiaID) {
		if (lectorID == null || lectorID.length() == 0 //
				|| copiaID == null || copiaID.length() == 0)
			return "redirect:/prestamo";

		Lector lector = null;
		try {
			lector = lectorService.readById(lectorID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		if (lector == null)
			return "redirect:/prestamo";

		Copia copia = null;
		try {
			copia = copiaService.readById(copiaID);
		} catch (ServiceException se) {

		}
		if (copia == null)
			return "redirect:/prestamo";

		boolean puedeRealizarPrestamo = false;
		try {
			puedeRealizarPrestamo = lectorService.puedeRealizarPrestamo(lector);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		if (!puedeRealizarPrestamo)
			return "redirect:/prestamo";

		try {
			lectorService.realizarPrestamo(lector.getId(), copia.getId(), prestamo);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/prestamo";
	}

	@GetMapping("/prestamo/delete/{prestamoID}")
	public String deletePrestamo(Model model, @PathVariable(value = "prestamoID") String prestamoID) {
		try {
			prestamoService.deleteById(prestamoID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/prestamo";
	}

	@GetMapping("/prestamo/list/{lectorID}")
	public String getPrestamosByLector(Model model, @PathVariable(value = "lectorID") String lectorID) {
		// TODO
		Lector lector = null;
		try {
			lector = lectorService.readById(lectorID);
		} catch (ServiceException se) {
		}

		if (lector == null) {
			model.addAttribute("message", "No existe ningún lector con el ID dado");
			model.addAttribute("prestamos", new ArrayList<Prestamo>());
			return "listPrestamo";
		}
		model.addAttribute("prestamos", prestamoService.getPrestamosActivosByLector(lector));
		return "listPrestamo";
	}

}
