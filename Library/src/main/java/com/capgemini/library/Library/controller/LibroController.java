package com.capgemini.library.Library.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.capgemini.library.Library.model.Libro;


@Controller
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/añadir-libro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("libro", new Libro());
        return "añadir-libro";
    }

    @PostMapping("/añadir-libro")
    public String añadirLibro(@ModelAttribute Libro libro) {
        libroRepository.save(libro);
        return "redirect:/lista-libros";
    }

    @GetMapping("/lista-libros")
    public String listarLibros(Model model) {
        List<Libro> libros = libroRepository.findAll();
        model.addAttribute("libros", libros);
        return "lista-libros";
    }
}

