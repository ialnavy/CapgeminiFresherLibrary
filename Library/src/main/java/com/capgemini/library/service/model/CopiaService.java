package com.capgemini.library.service.model;

import java.util.List;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Copia;
import com.capgemini.library.service.AbstractService;

public interface CopiaService extends AbstractService<Copia> {

	public void linkCopiaToLibro(String copiaID, String libroID) throws ServiceException;

	public List<Copia> findAllNoAlquiladas();
}
