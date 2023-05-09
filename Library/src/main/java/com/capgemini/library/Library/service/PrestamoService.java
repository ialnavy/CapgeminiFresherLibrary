package com.capgemini.library.Library.service;

import java.util.List;

import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Prestamo;

public interface PrestamoService {
	
	public Prestamo save(Prestamo prestamo);
	
	public void verificarPrestamosYMultas();
	
	public List<Prestamo> getPrestamosActivosByLector(Lector lector);
}
