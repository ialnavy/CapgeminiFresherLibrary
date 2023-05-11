package com.capgemini.library.Library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.model.Prestamo;

public interface CopiaRepository extends CrudRepository<Copia, String> {

	public List<Copia> findByPrestamo(Prestamo prestamo);
}
