package com.capgemini.library.Library.service;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Multa;
import com.capgemini.library.Library.model.Prestamo;
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
    
    public Prestamo realizarPrestamo(Lector lector, Copia copia) {
        // (Verificar si el lector puede tomar un nuevo préstamo)

        Prestamo prestamo = new Prestamo();
 
        Date fechaActual = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DATE, 30);
        Date fechaDevolucion = new Date(calendar.getTimeInMillis());
        prestamo.setFechaDevolucion(fechaDevolucion);
        

        return prestamo;
    }
    
    public boolean puedeRealizarPrestamo(Lector lector) {
    	
        Multa multa = lector.getMulta();
        
        if (multa != null) {
            Date fechaActual = new Date(System.currentTimeMillis());
            if (fechaActual.before(multa.getfFin())) {
                return false;
            }
        }

        if (lector.getPrestamos().size() >= 3) {
            return false;
        }

        return true;
    }
}
