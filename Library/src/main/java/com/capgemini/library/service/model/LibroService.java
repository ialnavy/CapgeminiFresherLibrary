package com.capgemini.library.service.model;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Libro;
import com.capgemini.library.service.AbstractService;

public interface LibroService extends AbstractService<Libro> {
	public void linkLibroToAutor(String libroID, String autorID) throws ServiceException;
}
