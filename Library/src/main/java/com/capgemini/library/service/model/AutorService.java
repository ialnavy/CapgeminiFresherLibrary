package com.capgemini.library.service.model;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Autor;
import com.capgemini.library.service.AbstractService;

public interface AutorService extends AbstractService<Autor> {
	
	public boolean isCreable(Autor autor) throws ServiceException;
}
