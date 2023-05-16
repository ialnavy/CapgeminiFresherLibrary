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
import com.capgemini.library.service.PrestamoService;
import com.capgemini.library.service.model.CopiaService;
import com.capgemini.library.service.model.LectorService;

@Controller
public class PrestamoController {

	@Autowired
	private LectorService lectorService;

	@Autowired
	private CopiaService copiaService;

	@Autowired
	private PrestamoService prestamoService;

	@GetMapping("/prestamo/create")
	public String getPrestamoCreate(Model model) {
		initialisePrestamo(model, lectorService, copiaService);
		return "createPrestamo";
	}

	@PostMapping("/prestamo/create")
	public String realizarPrestamo(@ModelAttribute Prestamo prestamo, //
			@RequestParam("lectorID") String lectorID, //
			@RequestParam("copiaID") String copiaID, Model model) {
		if (lectorID == null || lectorID.length() == 0 //
				|| copiaID == null || copiaID.length() == 0)
			return "redirect:/prestamo";

		Lector lector = null;
		try {
			lector = lectorService.readById(lectorID);
		} catch (ServiceException se) {
		}

		if (lector == null) {
			model.addAttribute("message", "No se pudo realizar el préstamo porque el lector con el ID dado no existe");
			initialisePrestamo(model, lectorService, copiaService);
			return "createPrestamo";
		}

		Copia copia = null;
		try {
			copia = copiaService.readById(copiaID);
		} catch (ServiceException se) {

		}
		if (copia == null) {
			model.addAttribute("message", "No se pudo realizar el préstamo porque la copia con el ID dado no existe");
			initialisePrestamo(model, lectorService, copiaService);
			return "createPrestamo";
		}

		if (!lectorService.puedeRealizarPrestamo(lector)) {
			model.addAttribute("message",
					"No se pudo realizar el préstamo porque o bien el lector tiene una multa o bien el lector ya tiene 3 préstamos en activo");
			initialisePrestamo(model, lectorService, copiaService);
			return "createPrestamo";
		}

//		String prestamoID = 
		lectorService.realizarPrestamo(lector.getId(), copia.getId(), prestamo);

		model.addAttribute("message", "Préstamo realizado con éxito");

		initialisePrestamo(model, lectorService, copiaService);
		return "createPrestamo";
	}

	@GetMapping("/prestamo/list")
	public String getPrestamos(Model model) {
		model.addAttribute("prestamos", prestamoService.findAll());
		return "listPrestamo";
	}

	@GetMapping("/prestamo/list/{lectorID}")
	public String getPrestamosByLector(Model model, @PathVariable(value = "lectorID") String lectorID) {
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

	private static void initialisePrestamo(Model model, LectorService lectorService, CopiaService copiaService) {
		List<Lector> lectores = new ArrayList<>();
		List<Copia> copias = new ArrayList<>();

		try {
			lectores = lectorService.readAll();
			copias = copiaService.findAllNoAlquiladas();
		} catch (ServiceException se) {
		}

		model.addAttribute("prestamo", new Prestamo());
		model.addAttribute("lectores", lectores);
		model.addAttribute("copias", copias);
	}

}
