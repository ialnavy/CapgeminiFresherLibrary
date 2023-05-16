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
import com.capgemini.library.model.Reserva;
import com.capgemini.library.service.model.CopiaService;
import com.capgemini.library.service.model.LectorService;
import com.capgemini.library.service.model.ReservaService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private CopiaService copiaService;

	@Autowired
	private LectorService lectorService;

	@GetMapping("/reserva")
	public String reserva(Model model) {
		model.addAttribute("reserva", new Reserva());
		List<Reserva> reserva = new ArrayList<>();
		try {
			reserva = reservaService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("todasLasReservas", reserva);
		List<Copia> copias = new ArrayList<>();
		try {
			copias = copiaService.findAllYaAlquiladas();
		} catch (ServiceException se) {
		}
		model.addAttribute("todasLasCopias", copias);
		List<Lector> lectores = new ArrayList<>();
		try {
			lectores = lectorService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("todosLosLectores", lectores);
		return "crud/reserva";
	}

	@PostMapping("/reserva/create")
	public String createReserva(Model model, @ModelAttribute Reserva reserva, //
			@RequestParam(name = "lectorID", required = false) String lectorID, //
			@RequestParam(name = "copiaID", required = false) String copiaID) throws ServiceException {
		if (copiaID == null || copiaID.length() == 0 //
				|| lectorID == null || lectorID.length() == 0 //
				|| reserva.getFechaReserva() == null)
			return "redirect:/reserva";

		try {
			reservaService.create(reserva);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		try {
			reservaService.linkReservaToCopia(reserva.getId(), copiaID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		try {
			reservaService.linkReservaToLector(reserva.getId(), lectorID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/reserva";
	}

	@GetMapping("/reserva/delete/{reservaID}")
	public String deleteReserva(Model model, @PathVariable(value = "reservaID") String reservaID) {
		try {
			reservaService.deleteById(reservaID);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/reserva";
	}

	@PostMapping("/reserva/verify")
	public String verifyReservas(@RequestParam("copiaID") String copiaId) {
		try {
			reservaService.verifyReservas(copiaId);
		} catch (ServiceException se) {
			System.err.println(se.getMessage());
		}
		return "redirect:/reserva";
	}
}
