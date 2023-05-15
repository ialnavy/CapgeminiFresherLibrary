package com.capgemini.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.library.model.Lector;

public interface LectorRepository extends CrudRepository<Lector, String> {

	public List<Lector> findByUsername(String username);
	
}
