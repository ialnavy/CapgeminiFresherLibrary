package com.capgemini.library.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.capgemini.library.model.Reserva;
import com.capgemini.library.repository.ReservaRepository;
import com.capgemini.library.service.model.LectorService;

@Service
public class ReservaServiceImp implements ReservaService {
	
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired 
    private LectorService lectorService;

//    @Autowired
//    private EmailService emailService;
//    
    @Override
    public void cancelReserva(String id) {
        reservaRepository.deleteById(id);
    }

    public void verifyReservas(String libroId) {
        List<Reserva> reservas = reservaRepository.findByCopiaIdOrderByFechaReservaAsc(libroId);
        if (!reservas.isEmpty()) {
            Reserva reserva = reservas.get(0);
            // Enviar un correo electrónico al socio.
//            emailService.sendSimpleMessage(
//                    reserva.getLector().getEmail(),  // Asegúrate de que tu clase Socio tenga un método getEmail()
//                    "Su libro reservado está disponible",
//                    "El libro que reservaste está ahora disponible. Tienes 48 horas para recogerlo."
//                );
            // Guardar la fecha y hora actuales para poder verificar si han pasado 48 horas.
            reserva.setFechaNotificacion(LocalDateTime.now());
            reservaRepository.save(reserva);
        }
    }
    
    @Scheduled(fixedRate = 60000)  // Correr cada minuto (IMAGINO)
    public void checkReservas() {
        List<Reserva> reservas = (List<Reserva>) reservaRepository.findAll();
        for (Reserva reserva : reservas) {
            if (reserva.getFechaNotificacion() != null &&
                ChronoUnit.HOURS.between(reserva.getFechaNotificacion(), LocalDateTime.now()) >= 48) {
                // Si han pasado 48 horas desde la notificación, eliminar la reserva
                reservaRepository.delete(reserva);
            }
        }
    }
    
    @Override
    public List<Reserva> getAllReservas() {
        return (List<Reserva>) reservaRepository.findAll();
    }

    @Override
    public Reserva createReserva(Reserva reserva) throws Exception {
       
        // Asegurarse de que todas las copias del libro están prestadas
        if (!lectorService.puedeRealizarPrestamo(reserva.getLector())) {
            throw new Exception("Todavía hay copias del libro disponibles, no se puede realizar la reserva");
        }
        // Guardar la reserva
        return reservaRepository.save(reserva);
    }

}
