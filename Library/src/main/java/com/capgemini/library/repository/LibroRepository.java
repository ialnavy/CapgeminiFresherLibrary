package com.capgemini.library.repository;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, String> {

}
