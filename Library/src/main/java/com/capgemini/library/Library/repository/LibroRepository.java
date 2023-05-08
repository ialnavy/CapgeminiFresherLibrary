package com.capgemini.library.Library.repository;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.Library.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, String> {

}
