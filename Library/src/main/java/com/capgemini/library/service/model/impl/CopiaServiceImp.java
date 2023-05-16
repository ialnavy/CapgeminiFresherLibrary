package com.capgemini.library.service.model.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Copia;
import com.capgemini.library.model.Libro;
import com.capgemini.library.repository.CopiaRepository;
import com.capgemini.library.repository.LibroRepository;
import com.capgemini.library.service.model.CopiaService;

@Service
public class CopiaServiceImp implements CopiaService {

	@Autowired
	private CopiaRepository copiaRepository;

	@Autowired
	private LibroRepository libroRepository;

	@Override
	public void create(Copia copia) {
		copiaRepository.save(copia);
	}

	@Override
	public List<Copia> readAll() {
		return (List<Copia>) copiaRepository.findAll();
	}

	@Override
	public Copia readById(String id) {
		return copiaRepository.findById(id).orElse(null);
	}

	@Override
	public List<Copia> findAllNoAlquiladas() {
		return (List<Copia>) copiaRepository.findByPrestamo(null);
	}

	@Override
	public void update(Copia pojo) throws ServiceException {
		Copia copia;
		try {
			copia = copiaRepository.findById(pojo.getId()).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		if (copia == null)
			throw new ServiceException("La copia con ID " + pojo.getId() + " no existe");

		if (pojo.getEstado() != null)
			copia.setEstado(pojo.getEstado());

		copiaRepository.save(copia);
	}

	@Override
	public void deleteById(String id) {
		copiaRepository.deleteById(id);
	}

	@Override
	public void linkCopiaToLibro(String copiaID, String libroID) throws ServiceException {
		Copia copia;
		try {
			copia = copiaRepository.findById(copiaID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		Libro libro;
		try {
			libro = libroRepository.findById(libroID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		copia.setLibro(libro);
		copiaRepository.save(copia);
	}

}