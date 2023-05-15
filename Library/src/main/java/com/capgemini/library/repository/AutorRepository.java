package com.capgemini.library.repository;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Autor;

public interface AutorRepository extends CrudRepository<Autor, String> {

}
