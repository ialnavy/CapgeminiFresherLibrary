package com.capgemini.library.Library.service;

import java.util.List;

import com.capgemini.library.Library.model.Libro;

public interface LibroService {

	public List<Libro> obtenerTodosLosLibros();

	public void añadirLibro(Libro libro);

	public Libro obtenerLibroPorId(Integer id);

	public void borrarLibroPorId(Integer id);

}


