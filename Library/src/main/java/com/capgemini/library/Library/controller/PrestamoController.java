package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Prestamo;
import com.capgemini.library.Library.service.CopiaService;
import com.capgemini.library.Library.service.LectorServiceImp;
import com.capgemini.library.Library.service.PrestamoService;

@Controller
public class PrestamoController {

	@Autowired
	private LectorServiceImp lectorService;

	@Autowired
	private CopiaService copiaService;

	@Autowired
	private PrestamoService prestamoService;

	@PostMapping("/prestamo/create")
	public String realizarPrestamo(@RequestParam("lectorId") String lectorId, @RequestParam("copiaId") String copiaId,
			Model model) {

		Lector lector = lectorService.getLectorById(lectorId);
		Copia copia = copiaService.findById(copiaId);

		if (lector != null && copia != null && lectorService.puedeRealizarPrestamo(lector)) {
			Prestamo prestamo = lectorService.realizarPrestamo(lector, copia);
			prestamoService.save(prestamo);
			model.addAttribute("message", "Préstamo realizado con éxito");
		} else {
			model.addAttribute("message", "No se pudo realizar el préstamo");
		}

		return "createPrestamo";
	}

	@GetMapping("/prestamo/list/{lectorID}")
	public String verPrestamosActivos(Model model, @PathVariable(value = "lectorID") String lectorID) {
		List<Prestamo> prestamosActivos = prestamoService
				.getPrestamosActivosByLector(lectorService.getLectorById(lectorID));
		model.addAttribute("prestamosActivos", prestamosActivos);
		return "listPrestamo";
	}

}
