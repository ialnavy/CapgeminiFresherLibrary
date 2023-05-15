package com.capgemini.library.repository;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Prestamo;

public interface PrestamoRepository extends CrudRepository<Prestamo, String> {

}
