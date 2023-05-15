package com.capgemini.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.model.Copia;
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Multa;
import com.capgemini.library.model.Prestamo;
import com.capgemini.library.repository.CopiaRepository;
import com.capgemini.library.repository.LectorRepository;
import com.capgemini.library.repository.PrestamoRepository;

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

	public String realizarPrestamo(String lectorID, String copiaID, Prestamo prestamo) {
		prestamo.setFechaFin(LocalDate.now().plusDays(30));

		Lector lector = lectorRepository.findById(lectorID).orElse(null);
		Copia copia = copiaRepository.findById(copiaID).orElse(null);

		prestamo.setLector(lector);
		prestamo.setCopia(copia);

		prestamoRepository.save(prestamo);

		lector.getPrestamos().add(prestamo);
		copia.setPrestamo(prestamo);

		lector = lectorRepository.save(lector);
		copia = copiaRepository.save(copia);

		return prestamo.getId();
	}

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
}
