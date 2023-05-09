package com.capgemini.library.Library.service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Multa;
import com.capgemini.library.Library.model.Prestamo;
import com.capgemini.library.Library.repository.PrestamoRepository;

public class PrestamoServiceImp implements PrestamoService{


	@Autowired
	private PrestamoRepository prestamoRepository;


	public Prestamo save(Prestamo prestamo) {
		Lector lector = prestamo.getLector();
		if (lector == null) {
			throw new IllegalArgumentException("El préstamo debe tener un lector asociado.");
		}

		List<Prestamo> prestamosActivos = lector.getPrestamos().stream()
				.filter(p -> p.getFechaDevolucion() == null)
				.collect(Collectors.toList());

		if (prestamosActivos.size() >= 3) {
			throw new IllegalStateException("El lector no puede tener más de 3 libros en préstamo.");
		}

		return prestamoRepository.save(prestamo);
	}


	public void verificarPrestamosYMultas() {
		Iterable<Prestamo> prestamos = prestamoRepository.findAll();
		Date fechaActual = new Date(System.currentTimeMillis());

		for (Prestamo prestamo : prestamos) {
			if (prestamo.getFechaDevolucion().before(fechaActual)) {
				Lector lector = prestamo.getLector();
				Multa multa = lector.getMulta();
				if (multa == null) {
					multa = new Multa();
				}

				// Calcular la cantidad de días de retraso
				long diffInMillis = Math.abs(fechaActual.getTime() - prestamo.getFechaDevolucion().getTime());
				long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
				@SuppressWarnings("unused")
				long diasDeProhibicion = diffInDays * 2;


			}
		}
	}
}
