package com.capgemini.library.service.model.impl;

import java.util.ArrayList;
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
	public void create(Copia copia) throws ServiceException {
		try {
			copiaRepository.save(copia);
		}catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<Copia> readAll() throws ServiceException {
		List<Copia> copias = new ArrayList<>();
		try {
			copias = (List<Copia>) copiaRepository.findAll();
		}catch (Exception e) {
			throw new ServiceException(e);
		}
		return copias;
	}

	@Override
	public Copia readById(String id) throws ServiceException {
		Copia copia = null;
		try {
			copia = copiaRepository.findById(id).orElse(null);
		}catch(Exception e) {
			throw new ServiceException(e);
		}
		return copia;
	}

	@Override
	public List<Copia> findAllNoAlquiladas() throws ServiceException {
		List<Copia> copia = new ArrayList<>();
		try {
			copia = copiaRepository.findByPrestamo(null);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
		return copia;
	}
	
	@Override
	public List<Copia> findAllYaAlquiladas() throws ServiceException {
		List<Copia> copia = new ArrayList<>();
		try {
			copia = copiaRepository.findYaAlquiladas();
		} catch(Exception e) {
			throw new ServiceException(e);
		}
		return copia;
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
			throw new ServiceException("La copia con ID " + pojo.getId() + " no existe.");

		if (pojo.getEstado() != null)
			copia.setEstado(pojo.getEstado());
		try {
			copiaRepository.save(copia);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteById(String id) throws ServiceException {
		try {
			copiaRepository.deleteById(id);
		} catch(Exception e) {
			throw new ServiceException(e);
		}
		
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