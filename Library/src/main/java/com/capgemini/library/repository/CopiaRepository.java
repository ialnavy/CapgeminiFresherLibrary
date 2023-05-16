package com.capgemini.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Copia;
import com.capgemini.library.model.Prestamo;

public interface CopiaRepository extends CrudRepository<Copia, String> {

	public List<Copia> findByPrestamo(Prestamo prestamo);
    
    @Query("SELECT c FROM Copia c WHERE c.prestamo IS NOT NULL OR c.estado = com.capgemini.library.model.EstadoCopia.PRESTADO")
    public List<Copia> findYaAlquiladas();

}
