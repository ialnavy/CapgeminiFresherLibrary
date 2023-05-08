package com.capgemini.library.Library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Libro;
import com.capgemini.library.Library.repository.LibroRepository;

@Service
public class LibroServiceImp implements LibroService {

	@Autowired
	private LibroRepository libroRepository;

	@Override
	public List<Libro> obtenerTodosLosLibros() {
		return (List<Libro>) libroRepository.findAll();
	}

	@Override
	public void añadirLibro(Libro libro) {
		libroRepository.save(libro);
	}

	@Override
	public Libro obtenerLibroPorId(String id) {
		return libroRepository.findById(id).orElse(null);
	}

	@Override
	public void borrarLibroPorId(String id) {
		libroRepository.deleteById(id);
	}

}
