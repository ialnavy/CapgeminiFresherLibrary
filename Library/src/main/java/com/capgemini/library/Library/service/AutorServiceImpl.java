package com.capgemini.library.Library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Autor;
import com.capgemini.library.Library.repository.AutorRepository;

@Service
public class AutorServiceImpl implements AutorService {

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public List<Autor> findAll() {
		return (List<Autor>) autorRepository.findAll();
	}

	@Override
	public void create(Autor autor) {
		autorRepository.save(autor);
	}

	@Override
	public Autor findById(String id) {
		return autorRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id) {
		autorRepository.deleteById(id);
	}

}
