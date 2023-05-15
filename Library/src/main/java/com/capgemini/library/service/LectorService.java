package com.capgemini.library.service;

import java.util.List;

import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Prestamo;

public interface LectorService {

	public Lector save(Lector lector);

	public List<Lector> getAllLectores();

	public Lector getLectorById(String id);

	public void deleteLector(String id);

	public String realizarPrestamo(String lectorID, String copiaID, Prestamo prestamoPOJO);

	public boolean puedeRealizarPrestamo(Lector lector);

}