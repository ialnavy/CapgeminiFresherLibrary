package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.Library.model.Copia;
import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Prestamo;
import com.capgemini.library.Library.service.CopiaService;
import com.capgemini.library.Library.service.LectorService;
import com.capgemini.library.Library.service.PrestamoService;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private LectorService lectorService;

    @Autowired
    private CopiaService copiaService;

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping("/realizar")
    public String realizarPrestamo(
            @RequestParam("lectorId") String lectorId,
            @RequestParam("copiaId") String copiaId,
            Model model) {

        Lector lector = lectorService.getLectorById(lectorId);
        Copia copia = copiaService.findById(copiaId);

        if (lector != null && copia != null && lectorService.puedeRealizarPrestamo(lector)) {
            Prestamo prestamo = lectorService.realizarPrestamo(lector, copia);
            prestamoService.save(prestamo);
            model.addAttribute("message", "Préstamo realizado con éxito");
        } else {
            model.addAttribute("message", "No se pudo realizar el préstamo");
        }

        return "prestamo_result";
    }

    
    // TODO: ACABAR ESTE METODO PARA VER EL LISTADO DE PRESTAMOS ACTIVOS
//    @GetMapping("/activos")
//    public String verPrestamosActivos(Model model) {
//        List<Prestamo> prestamosActivos = prestamoService.getPrestamosActivos();
//        model.addAttribute("prestamosActivos", prestamosActivos);
//        return "prestamos_activos";
//    }

    
}