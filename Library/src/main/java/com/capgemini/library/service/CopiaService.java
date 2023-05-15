package com.capgemini.library.service;

import java.util.List;

import com.capgemini.library.model.Copia;

public interface CopiaService {

	public List<Copia> findAll();

	public List<Copia> findAllNoAlquiladas();

	public Copia findById(String id);

	public Copia save(Copia copia);

	public void deleteById(String id);
}
