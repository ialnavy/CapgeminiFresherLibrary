package com.capgemini.library.service;

import java.util.List;

import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Prestamo;

public interface PrestamoService {

	public List<Prestamo> findAll();

	public Prestamo save(Prestamo prestamo);

	public void verificarPrestamosYMultas();

	public List<Prestamo> getPrestamosActivosByLector(Lector lector);
}
