package com.capgemini.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.model.Reserva;
import com.capgemini.library.repository.ReservaRepository;

@Service
public class ReservaServiceImp implements ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Reserva createReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public void cancelReserva(String id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public void verifyReservas(String copiaId) {
         List<Reserva> reservas = reservaRepository.findByCopiaIdOrderByFechaReservaAsc(copiaId);
        if (!reservas.isEmpty()) {
            Reserva reserva = reservas.get(0);
            // Enviar un correo electrónico al Socio
            // ...
        }
    }

    @Override
    public List<Reserva> getAllReservas() {
        return (List<Reserva>) reservaRepository.findAll();
    }
}
