package com.capgemini.library.Library.service;

import com.capgemini.library.Library.model.Prestamo;

public interface PrestamoService {
	
	public Prestamo save(Prestamo prestamo);
	
	public void verificarPrestamosYMultas();
}
