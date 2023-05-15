package com.capgemini.library.service.model.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Autor;
import com.capgemini.library.repository.AutorRepository;
import com.capgemini.library.service.model.AutorService;

@Service
public class AutorServiceImpl implements AutorService {

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public void create(Autor autor) throws ServiceException {
		try {
			autorRepository.save(autor);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Autor readById(String id) throws ServiceException {
		Autor autor;
		try {
			autor = autorRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return autor;
	}

	@Override
	public List<Autor> readAll() throws ServiceException {
		List<Autor> autores;
		try {
			autores = (List<Autor>) autorRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return autores;
	}

	@Override
	public void update(Autor givenAutor) throws ServiceException {
		Autor autor;
		try {
			autor = autorRepository.findById(givenAutor.getId()).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		if (autor == null)
			throw new ServiceException("El autor con ID " + givenAutor.getId() + " no existe");

		if (givenAutor.getNombre() != null && givenAutor.getNombre().length() > 0)
			autor.setNombre(givenAutor.getNombre());
		if (givenAutor.getNacionalidad() != null && givenAutor.getNacionalidad().length() > 0)
			autor.setNacionalidad(givenAutor.getNacionalidad());
		if (givenAutor.getFechaNacimiento() != null)
			autor.setFechaNacimiento(givenAutor.getFechaNacimiento());

		autorRepository.save(autor);
	}

	@Override
	public void deleteById(String id) throws ServiceException {
		try {
			autorRepository.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
