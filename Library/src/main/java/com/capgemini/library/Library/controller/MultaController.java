package com.capgemini.library.Library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.library.Library.model.Lector;
import com.capgemini.library.Library.model.Multa;
import com.capgemini.library.Library.service.LectorService;
import com.capgemini.library.Library.service.MultaService;

@Controller
public class MultaController {

    @Autowired
    private LectorService lectorService;

    @Autowired
    private MultaService multaService;

    @GetMapping("/multa")
    public String multaForm(Model model) {
        model.addAttribute("lectores", lectorService.getAllLectores());
        return "multa";
    }

    @PostMapping("/multa")
    public String multarLector(@RequestParam("lectorId") String lectorId, @RequestParam("dias") int dias, Model model) {
        Lector lector = lectorService.getLectorById(lectorId);
        Multa multa = lector.multar(dias);
        multa.setLector(lector);
        multaService.save(multa);
        model.addAttribute("message", "Lector multado con éxito");
        return "redirect:/multa";
    }
}

	
