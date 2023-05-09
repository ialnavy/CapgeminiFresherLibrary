package com.capgemini.library.Library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.repository.LectorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LectorService {

    @Autowired
    private LectorRepository lectorRepository;

    public Lector save(Lector lector) {
        return lectorRepository.save(lector);
    }

    public List<Lector> getAllLectores() {
        return (List<Lector>) lectorRepository.findAll();
    }

    public Lector getLectorById(String id) {
        try {
        	return lectorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Letor no encontrado con ID:"+ id ));
        }catch (EntityNotFoundException e){
        	throw e;
        }
        
    }

    public void deleteLector(String id) {
        lectorRepository.deleteById(id);
    }
}
