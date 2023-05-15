package com.capgemini.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Copia;
import com.capgemini.library.model.Prestamo;

public interface CopiaRepository extends CrudRepository<Copia, String> {

	public List<Copia> findByPrestamo(Prestamo prestamo);
}
