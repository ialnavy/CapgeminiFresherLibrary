package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.service.CopiaService;

@RestController
@RequestMapping("/api/copias")
public class CopiaController {

	@Autowired
	private CopiaService copiaService;

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

	@PostMapping
	public Copia createCopia(@RequestBody Copia copia) {
		return copiaService.save(copia);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Copia> updateCopia(@PathVariable String id, @RequestBody Copia copiaUpdated) {
		Copia copia = copiaService.findById(id);
		if (copia == null) {
			return ResponseEntity.notFound().build();
		}
		copia.setEstado(copiaUpdated.getEstado());
		copia.setLibro(copiaUpdated.getLibro());
		copia.setPrestamos(copiaUpdated.getPrestamos());
		return ResponseEntity.ok(copiaService.save(copia));
	}
}

