package com.capgemini.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Copia;
import com.capgemini.library.model.Libro;
import com.capgemini.library.service.CopiaService;
import com.capgemini.library.service.model.LibroService;

@Controller
public class CopiaController {

	@Autowired
	private CopiaService copiaService;

	@Autowired
	private LibroService libroService;

	@GetMapping
	public List<Copia> getAllCopias() {
		return copiaService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Copia> getCopiaById(@PathVariable String id) {
		Copia copia = copiaService.findById(id);
		if (copia == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(copia);
	}

	@GetMapping("/copia/create")
	public String getCreateCopia(Model model) {

		model.addAttribute("copia", new Copia());
		List<Libro> libros = new ArrayList<>();
		try {
			libros = libroService.readAll();
		} catch (ServiceException se) {
		}
		model.addAttribute("libros", libros);
		return "createCopia";
	}

	@PostMapping("/copia/create")
	public String createCopia(@ModelAttribute Copia copia, @RequestParam("libroID") String libroID) {
		Libro libro = null;
		try {
			libro = libroService.readById(libroID);
		} catch (ServiceException se) {
		}
		copia.setLibro(libro);
		copiaService.save(copia);
		return "redirect:/copia/list";
	}

	@GetMapping("/copia/list")
	public String getListCopia(Model model) {
		model.addAttribute("copias", copiaService.findAll());
		return "listCopia";
	}

	@PutMapping("/{id}")
	public ResponseEntity<Copia> updateCopia(@PathVariable String id, @RequestBody Copia copiaUpdated) {
		Copia copia = copiaService.findById(id);
		if (copia == null) {
			return ResponseEntity.notFound().build();
		}
		copia.setEstado(copiaUpdated.getEstado());
//		copia.setLibro(copiaUpdated.getLibro());
		copia.setPrestamo(copiaUpdated.getPrestamo());
		return ResponseEntity.ok(copiaService.save(copia));
	}
}