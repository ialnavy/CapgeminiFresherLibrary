package com.capgemini.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.model.Copia;
import com.capgemini.library.repository.CopiaRepository;

@Service
public class CopiaServiceImp implements CopiaService {

	@Autowired
	private CopiaRepository copiaRepository;

	public List<Copia> findAll() {
		return (List<Copia>) copiaRepository.findAll();
	}

	public List<Copia> findAllNoAlquiladas() {
		return (List<Copia>) copiaRepository.findByPrestamo(null);
	}

	public Copia findById(String id) {
		return copiaRepository.findById(id).orElse(null);
	}

	public Copia save(Copia copia) {
		return copiaRepository.save(copia);
	}

	public void deleteById(String id) {
		copiaRepository.deleteById(id);
	}

}