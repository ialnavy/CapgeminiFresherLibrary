package com.capgemini.library.service;

import java.util.List;

import com.capgemini.library.model.Reserva;

public interface ReservaService {
	
    Reserva createReserva(Reserva reserva);
    
    void cancelReserva(String id);
    
    void verifyReservas(String copiaId);
    
    List<Reserva> getAllReservas();
}
