package com.capgemini.library.service.model.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Copia;
import com.capgemini.library.model.Lector;
import com.capgemini.library.model.Reserva;
import com.capgemini.library.repository.CopiaRepository;
import com.capgemini.library.repository.LectorRepository;
import com.capgemini.library.repository.ReservaRepository;
import com.capgemini.library.service.EmailService;
import com.capgemini.library.service.model.ReservaService;

@Service
public class ReservaServiceImp implements ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private CopiaRepository copiaRepository;

	@Autowired
	private LectorRepository lectorRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public void deleteById(String id) throws ServiceException {
		try {
			reservaRepository.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);

		}
	}

	public void verifyReservas(String libroId) throws ServiceException {
		List<Reserva> reservas = reservaRepository.findByCopiaIdOrderByFechaReservaAsc(libroId);
		if (!reservas.isEmpty()) {
			try {
				Reserva reserva = reservas.get(0);
				// Enviar un correo electrónico al socio.
				emailService.sendSimpleMessage(reserva.getLector().getEmail(), // Asegúrate de que tu clase Socio tenga
						// un método getEmail()
						"Su libro reservado está disponible",
						"El libro que reservaste está ahora disponible. Tienes 48 horas para recogerlo.");
				// Guardar la fecha y hora actuales para poder verificar si han pasado 48 horas.
				reserva.setFechaNotificacion(LocalDate.now());
				reservaRepository.save(reserva);
			} catch (Exception e) {
				throw new ServiceException(e);
			}

		}
	}

	@Scheduled(fixedRate = 60000) // Correr cada minuto (IMAGINO)
	public void checkReservas() throws ServiceException {
		List<Reserva> reservas = (List<Reserva>) reservaRepository.findAll();
		for (Reserva reserva : reservas) {
			if (reserva.getFechaNotificacion() != null
					&& ChronoUnit.HOURS.between(reserva.getFechaNotificacion(), LocalDateTime.now()) >= 48) {
				// Si han pasado 48 horas desde la notificación, eliminar la reserva
				try {
					reservaRepository.delete(reserva);
				} catch (Exception e) {
					throw new ServiceException(e);
				}
			}
		}
	}

	@Override
	public List<Reserva> readAll() throws ServiceException {
		List<Reserva> reserva = new ArrayList<>();
		try {
			reserva = (List<Reserva>) reservaRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return reserva;
	}

	@Override
	public void create(Reserva reserva) throws ServiceException {
		// Guardar la reserva
		reservaRepository.save(reserva);
	}

	@Override
	public Reserva readById(String id) throws ServiceException {
		// TODO: POR HACER
		return null;
	}

	@Override
	public void update(Reserva pojo) throws ServiceException {
		// TODO: POR HACER
	}

	@Override
	public void linkReservaToCopia(String reservaID, String copiaID) throws ServiceException {
		Reserva reserva;
		try {
			reserva = reservaRepository.findById(reservaID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		Copia copia;
		try {
			copia = copiaRepository.findById(copiaID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		reserva.setCopia(copia);
		reservaRepository.save(reserva);
	}

	@Override
	public void linkReservaToLector(String reservaID, String lectorID) throws ServiceException {
		boolean puedeRealizarPrestamo = false;
		List<Lector> lectores = lectorRepository.puedeRealizarPrestamo();
		for (Lector lector : lectores) {
			if (lector.getId().equals(lectorID))
				puedeRealizarPrestamo = true;
		}
		if (!puedeRealizarPrestamo)
			throw new ServiceException("Todavía hay copias del libro disponibles, no se puede realizar la reserva.");

		Reserva reserva;
		try {
			reserva = reservaRepository.findById(reservaID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		Lector lector;
		try {
			lector = lectorRepository.findById(lectorID).orElse(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		reserva.setLector(lector);
		reservaRepository.save(reserva);
	}
}
