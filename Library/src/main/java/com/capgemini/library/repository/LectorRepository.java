package com.capgemini.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Lector;

public interface LectorRepository extends CrudRepository<Lector, String> {
	
	@Query("SELECT l FROM Lector l WHERE l.multa IS NULL AND size(l.prestamos)<3 ")
	public List<Lector> puedeRealizarPrestamo();
}
