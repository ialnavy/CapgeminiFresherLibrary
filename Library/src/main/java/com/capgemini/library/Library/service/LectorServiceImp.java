package com.capgemini.library.Library.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Multa;
import com.capgemini.library.Library.model.Prestamo;
import com.capgemini.library.Library.repository.CopiaRepository;
import com.capgemini.library.Library.repository.LectorRepository;
import com.capgemini.library.Library.repository.PrestamoRepository;

@Service
public class LectorServiceImp implements LectorService {

	@Autowired
	private CopiaRepository copiaRepository;

	@Autowired
	private LectorRepository lectorRepository;

	@Autowired
	private PrestamoRepository prestamoRepository;

	public Lector save(Lector lector) {
		return lectorRepository.save(lector);
	}

	public List<Lector> getAllLectores() {
		return (List<Lector>) lectorRepository.findAll();
	}

	public Lector getLectorById(String id) {
		return lectorRepository.findById(id).orElse(null);
//		Optional<Lector> optLector = lectorRepository.findById(id);
//		if (optLector.isEmpty())
//			return null;
//		return optLector.get();
	}

	public void deleteLector(String id) {
		lectorRepository.deleteById(id);
	}

	public String realizarPrestamo(Lector lector, Copia copia) {
		Prestamo prestamo = new Prestamo();

		Date fechaActual = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaActual);
		calendar.add(Calendar.DATE, 30);
		Date fechaDevolucion = new Date(calendar.getTimeInMillis());
		prestamo.setFechaDevolucion(fechaDevolucion);

//		Lector lector = lectorRepository.findById(lectorID).orElse(null);
//		Copia copia = copiaRepository.findById(copiaID).orElse(null);

		prestamo.setLector(lector);
		prestamo.setCopia(copia);

		prestamoRepository.save(prestamo);

//		lector.getPrestamos().add(prestamo);
//		copia.getPrestamos().add(prestamo);
//		
//		lector = lectorRepository.save(lector);
//		copia = copiaRepository.save(copia);

		return prestamo.getId();
	}

	public boolean puedeRealizarPrestamo(Lector lector) {

		Multa multa = lector.getMulta();

		if (multa != null) {
			Date fechaActual = new Date(System.currentTimeMillis());
			if (fechaActual.before(multa.getfFin())) {
				return false;
			}
		}

		if (lector.getPrestamos().size() >= 3) {
			return false;
		}

		return true;
	}
}
