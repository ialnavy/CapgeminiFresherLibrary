package com.capgemini.library.Library.service;

import java.util.List;

import com.capgemini.library.Library.model.Lector;

public interface LectorService {

	public Lector save(Lector lector);

	public List<Lector> getAllLectores();

	public Lector getLectorById(String id);

	public void deleteLector(String id);

	public String realizarPrestamo(String lectorID, String copiaID);

	public boolean puedeRealizarPrestamo(Lector lector);

}
