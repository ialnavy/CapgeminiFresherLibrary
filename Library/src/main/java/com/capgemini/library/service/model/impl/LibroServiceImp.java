package com.capgemini.library.service.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Autor;
import com.capgemini.library.model.Libro;
import com.capgemini.library.repository.AutorRepository;
import com.capgemini.library.repository.LibroRepository;
import com.capgemini.library.service.model.LibroService;

@Service
public class LibroServiceImp implements LibroService {

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroRepository libroRepository;

	@Override
	public void create(Libro libro) throws ServiceException {
		try {
			libroRepository.save(libro);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Libro> readAll() throws ServiceException {
		List<Libro> libros = new ArrayList<>();
		try {
			libros = (List<Libro>) libroRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return libros;
	}

	@Override
	public Libro readById(String id) throws ServiceException {
		Libro libro = null;
		try {
			libro = libroRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return libro;
	}

	@Override
	public void update(Libro pojo) throws ServiceException {
		Libro libro;
		try {
			libro = libroRepository.findById(pojo.getId()).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		if (libro == null)
			throw new ServiceException("El libro con ID " + pojo.getId() + " no existe");

		if (pojo.getTitulo() != null && pojo.getTitulo().length() > 0)
			libro.setTitulo(pojo.getTitulo());
		if (pojo.getEditorial() != null && pojo.getEditorial().length() > 0)
			libro.setEditorial(pojo.getEditorial());
		if (pojo.getAnyo() != null)
			libro.setAnyo(pojo.getAnyo());
		if (pojo.getAutor() != null)
			libro.setAutor(pojo.getAutor());
		if (pojo.getTipo() != null)
			libro.setTipo(pojo.getTipo());
		try {
			libroRepository.save(libro);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteById(String id) throws ServiceException {
		try {
			libroRepository.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void linkLibroToAutor(String libroID, String autorID) throws ServiceException {
		Libro libro;
		try {
			libro = libroRepository.findById(libroID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		Autor autor;
		try {
			autor = autorRepository.findById(autorID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		try {
			libro.setAutor(autor);
			libroRepository.save(libro);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public boolean isCreable(Libro libro, String autorID) throws ServiceException {
		if (libro == null || libro.getId() == null || libro.getId().length() == 0 //
				|| libro.getTipo() == null || libro.getEditorial() == null //
				|| libro.getEditorial().length() == 0 || libro.getAnyo() == null)
			return true;
		try {
			if(libroRepository.findById(libro.getId()).isPresent())
				return false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		try {
			if(libroRepository.findById(autorID).isEmpty())
				return false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return true;
	}

}
