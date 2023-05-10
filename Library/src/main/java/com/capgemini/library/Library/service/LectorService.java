package com.capgemini.library.Library.service;

import java.util.List;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Prestamo;

public interface LectorService {
	
	public Lector save(Lector lector);
	
	public List<Lector> getAllLectores();
	
	public Lector getLectorById(String id);
	
	public void deleteLector(String id);
	
	public Prestamo realizarPrestamo(Lector lector, Copia copia);
	
	public boolean puedeRealizarPrestamo(Lector lector);
	
	
	
}
