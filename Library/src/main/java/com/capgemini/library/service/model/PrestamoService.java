package com.capgemini.library.service.model;

import java.util.List;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Prestamo;
import com.capgemini.library.service.AbstractService;

public interface PrestamoService extends AbstractService<Prestamo> {

	public void verificarPrestamosYMultas();

	public List<Prestamo> getPrestamosActivosByLector(Lector lector);
	
	public boolean isCreable(Prestamo prestamo, String LectorID) throws ServiceException;

}
