package com.capgemini.library.service.model;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Prestamo;
import com.capgemini.library.service.AbstractService;

public interface LectorService extends AbstractService<Lector> {
	public String realizarPrestamo(String lectorID, String copiaID, Prestamo prestamoPOJO) throws ServiceException;

	public boolean puedeRealizarPrestamo(Lector lector) throws ServiceException;
	
	public boolean isCreable(Lector lector) throws ServiceException;
}
