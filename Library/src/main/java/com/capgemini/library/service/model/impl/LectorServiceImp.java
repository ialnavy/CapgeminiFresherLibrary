package com.capgemini.library.service.model.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Copia;
import com.capgemini.library.model.EstadoCopia;
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Multa;
import com.capgemini.library.model.Prestamo;
import com.capgemini.library.repository.CopiaRepository;
import com.capgemini.library.repository.LectorRepository;
import com.capgemini.library.repository.PrestamoRepository;
import com.capgemini.library.service.model.LectorService;

@Service
public class LectorServiceImp implements LectorService {

	@Autowired
	private CopiaRepository copiaRepository;

	@Autowired
	private LectorRepository lectorRepository;

	@Autowired
	private PrestamoRepository prestamoRepository;

	@Override
	public void create(Lector lector) throws ServiceException {
		try {
			lectorRepository.save(lector);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Lector> readAll() throws ServiceException {
		List<Lector> lectores = new ArrayList<>();
		try {
			lectores = (List<Lector>) lectorRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return lectores;
	}

	@Override
	public Lector readById(String id) throws ServiceException {
		Lector lector = null;
		try {
			lector = lectorRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return lector;
	}

	@Override
	public void update(Lector pojo) throws ServiceException {
		Lector lector;
		try {
			lector = lectorRepository.findById(pojo.getId()).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		if (lector == null)
			throw new ServiceException("El lector con ID " + pojo.getId() + " no existe");

		if (pojo.getNombre() != null && pojo.getNombre().length() > 0)
			lector.setNombre(pojo.getNombre());
		if (pojo.getTelefono() != null && pojo.getTelefono().length() > 0)
			lector.setTelefono(pojo.getTelefono());
		if (pojo.getEmail() != null && pojo.getEmail().length() > 0)
			lector.setEmail(pojo.getEmail());
		if (pojo.getDireccion() != null && pojo.getDireccion().length() > 0)
			lector.setDireccion(pojo.getDireccion());

		lectorRepository.save(lector);
	}

	@Override
	public void deleteById(String id) throws ServiceException {
		try {
			lectorRepository.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String realizarPrestamo(String lectorID, String copiaID, Prestamo prestamo)  throws ServiceException{
		prestamo.setFechaFin(LocalDate.now().plusDays(30));

		Lector lector = lectorRepository.findById(lectorID).orElse(null);
		Copia copia = copiaRepository.findById(copiaID).orElse(null);
		copia.setEstado(EstadoCopia.PRESTADO);

		prestamo.setLector(lector);
		prestamo.setCopia(copia);

		prestamoRepository.save(prestamo);

		lector.getPrestamos().add(prestamo);
		copia.setPrestamo(prestamo);

		lector = lectorRepository.save(lector);
		copia = copiaRepository.save(copia);

		return prestamo.getId();
	}

	@Override
	public boolean puedeRealizarPrestamo(Lector lector) {

		Multa multa = lector.getMulta();

		if (multa != null) {
			if (LocalDate.now().isBefore(multa.getfFin())) {
				return false;
			}
		}

		if (lector.getPrestamos().size() >= 3) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isCreable(Lector lector) throws ServiceException {
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(lector.getEmail());
		if(lector == null || lector.getId() == null || lector.getId().length() == 0
				|| lector.getTelefono() == null || mather.find() == true
				|| lector.getDireccion().isEmpty())
			return false;
		try {
			if(lectorRepository.findById(lector.getId()).isPresent())
				return false;
		}catch (Exception e) {
			throw new ServiceException(e);
		}
			
		return true;
	}

}
