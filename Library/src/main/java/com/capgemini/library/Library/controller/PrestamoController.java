package com.capgemini.library.Library.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Prestamo;
import com.capgemini.library.Library.service.CopiaService;
import com.capgemini.library.Library.service.LectorService;
import com.capgemini.library.Library.service.PrestamoService;

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
	public String realizarPrestamo(@ModelAttribute Prestamo prestamo, @RequestParam("lectorID") String lectorId,
			@RequestParam("copiaID") String copiaId, Model model) {
		Lector lector = lectorService.getLectorById(lectorId);
		if (lector == null) {
			model.addAttribute("message", "No se pudo realizar el préstamo porque el lector con el ID dado no existe");
			initialisePrestamo(model, lectorService, copiaService);
			return "createPrestamo";
		}

		Copia copia = copiaService.findById(copiaId);
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
		Lector lector = lectorService.getLectorById(lectorID);
		if (lector == null) {
			model.addAttribute("message", "No existe ningún lector con el ID dado");
			model.addAttribute("prestamos", new ArrayList<Prestamo>());
			return "listPrestamo";
		}
		model.addAttribute("prestamos", prestamoService.getPrestamosActivosByLector(lector));
		return "listPrestamo";
	}

	private static void initialisePrestamo(Model model, LectorService lectorService, CopiaService copiaService) {
		model.addAttribute("prestamo", new Prestamo());
		model.addAttribute("lectores", lectorService.getAllLectores());
		model.addAttribute("copias", copiaService.findAllNoAlquiladas());
	}

}
