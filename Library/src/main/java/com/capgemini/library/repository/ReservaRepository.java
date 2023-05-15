package com.capgemini.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Reserva;

public interface  ReservaRepository  extends CrudRepository<Reserva, String>{

    List<Reserva> findByCopiaIdOrderByFechaReservaAsc(String copiaId);

}
