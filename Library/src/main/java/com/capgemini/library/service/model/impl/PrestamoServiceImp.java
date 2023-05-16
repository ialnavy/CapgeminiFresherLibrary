package com.capgemini.library.service.model.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Multa;
import com.capgemini.library.model.Prestamo;
import com.capgemini.library.repository.MultaRepository;
import com.capgemini.library.repository.PrestamoRepository;
import com.capgemini.library.service.model.PrestamoService;

@Service
public class PrestamoServiceImp implements PrestamoService {

	@Autowired
	private MultaRepository multaRepository;

	@Autowired
	private PrestamoRepository prestamoRepository;

	@Override
	public void create(Prestamo prestamo) throws ServiceException {
		
		
		try {
			prestamoRepository.save(prestamo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Prestamo> readAll() throws ServiceException {
		List<Prestamo> prestamos = new ArrayList<>();
		try {
			prestamos = (List<Prestamo>) prestamoRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return prestamos;
	}

	@Override
	public Prestamo readById(String id) throws ServiceException {
		Prestamo prestamo = null;
		try {
			prestamo = prestamoRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return prestamo;
	}

	@Override
	public void update(Prestamo pojo) throws ServiceException {
		// TODO
	}

	@Override
	public void deleteById(String id) throws ServiceException {
		try {
			prestamoRepository.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Prestamo> getPrestamosActivosByLector(Lector lector) {
		return lector.getPrestamos().stream()
				.filter(p -> p.getFechaDevolucion() == null && p.getLector().getId().equals(lector.getId()))
				.collect(Collectors.toList());
	}

	/**
	 * Este método se debería invocar una vez al día.
	 */
	public void verificarPrestamosYMultas() {
		for (Prestamo prestamo : prestamoRepository.findAll()) {
			if (prestamo.getFechaFin().isBefore(LocalDate.now())) {
				Lector lector = prestamo.getLector();
				Multa multa = lector.getMulta();

				/* Ha devuelto el libro y no hay multa: no pasa nada. */
				// if (prestamo.getFechaDevolucion() != null && multa == null){}

				/*
				 * Ha devuelto el libro y sí hay multa. Si la multa ha caducado, se le retira.
				 */
				if (prestamo.getFechaDevolucion() != null && multa != null) {
					if (multa.getfFin().isBefore(LocalDate.now()))
						lector.setMulta(null);
				}

				/*
				 * No ha devuelto el libro y no hay multa. Se le mete multa.
				 */
				if (prestamo.getFechaDevolucion() == null && multa == null) {
					multa = new Multa();
					multa.setfInicio(LocalDate.now());
					multa.setfFin(LocalDate.now().plusDays(2));
					multa.setLector(prestamo.getLector());
					prestamo.getLector().setMulta(multa);

					multaRepository.save(multa);
					prestamoRepository.save(prestamo);
				}

				/*
				 * No ha devuelto el libro y sí hay multa. Se le incrementa multa.
				 */
				if (prestamo.getFechaDevolucion() == null && multa != null) {
					multa.setfFin(multa.getfFin().plusDays(2));

					multa.setLector(prestamo.getLector());
					prestamo.getLector().setMulta(multa);

					multaRepository.save(multa);
					prestamoRepository.save(prestamo);
				}
			}
		}
	}

}
