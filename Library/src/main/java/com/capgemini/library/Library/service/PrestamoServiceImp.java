package com.capgemini.library.Library.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Multa;
import com.capgemini.library.Library.model.Prestamo;
import com.capgemini.library.Library.repository.MultaRepository;
import com.capgemini.library.Library.repository.PrestamoRepository;

@Service
public class PrestamoServiceImp implements PrestamoService {

	@Autowired
	private MultaRepository multaRepository;

	@Autowired
	private PrestamoRepository prestamoRepository;

	public Prestamo save(Prestamo prestamo) {
		Lector lector = prestamo.getLector();
		if (lector == null)
			throw new IllegalArgumentException("El préstamo debe tener un lector asociado.");

		if (getPrestamosActivosByLector(lector).size() >= 3)
			throw new IllegalStateException("El lector no puede tener más de 3 libros en préstamo.");

		return prestamoRepository.save(prestamo);
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
			if (prestamo.getFechaFin().toLocalDate().isBefore(LocalDate.now())) {
				Lector lector = prestamo.getLector();
				Multa multa = lector.getMulta();

				/* Ha devuelto el libro y no hay multa: no pasa nada. */
				// if (prestamo.getFechaDevolucion() != null && multa == null){}

				/*
				 * Ha devuelto el libro y sí hay multa. Si la multa ha caducado, se le retira.
				 */
				if (prestamo.getFechaDevolucion() != null && multa != null) {
					if (multa.getfFin().toLocalDate().isBefore(LocalDate.now()))
						lector.setMulta(null);
				}

				/*
				 * No ha devuelto el libro y no hay multa. Se le mete multa.
				 */
				if (prestamo.getFechaDevolucion() == null && multa == null) {
					multa = new Multa();
					multa.setfInicio(Date.valueOf(LocalDate.now()));
					multa.setfFin(Date.valueOf(LocalDate.now().plusDays(2)));
					multa.setLector(prestamo.getLector());
					prestamo.getLector().setMulta(multa);

					multaRepository.save(multa);
					prestamoRepository.save(prestamo);
				}

				/*
				 * No ha devuelto el libro y sí hay multa. Se le incrementa multa.
				 */
				if (prestamo.getFechaDevolucion() == null && multa != null) {
					multa.setfFin(Date.valueOf(multa.getfFin().toLocalDate().plusDays(2)));

					multa.setLector(prestamo.getLector());
					prestamo.getLector().setMulta(multa);

					multaRepository.save(multa);
					prestamoRepository.save(prestamo);
				}
			}
		}
	}

}
