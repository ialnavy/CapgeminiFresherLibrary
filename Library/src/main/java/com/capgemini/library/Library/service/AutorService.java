package com.capgemini.library.Library.service;

import java.util.List;

import com.capgemini.library.Library.model.Autor;

public interface AutorService {

	public List<Autor> findAll();

	public void create(Autor autor);

	public Autor findById(String id);

	public void deleteById(String id);

}
