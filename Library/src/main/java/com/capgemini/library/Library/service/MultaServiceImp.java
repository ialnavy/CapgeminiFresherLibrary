package com.capgemini.library.Library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Multa;
import com.capgemini.library.Library.repository.MultaRepository;

@Service
public class MultaServiceImp implements MultaService{

    @Autowired
    private MultaRepository multaRepository;

    public Multa save(Multa multa) {
        return multaRepository.save(multa);
    }
}
