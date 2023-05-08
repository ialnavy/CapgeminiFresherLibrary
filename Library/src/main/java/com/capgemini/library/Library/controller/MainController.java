package com.capgemini.library.Library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.capgemini.library.Library.model.Libro;
import com.capgemini.library.Library.model.TipoLibro;

@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/libro/create")
	public String getCreateLibro(Model model) {
		model.addAttribute("libro", new Libro());
		model.addAttribute("tiposDeLibro", List.of(TipoLibro.values()).stream().map(tl -> tl.name()));

		return "createLibro";
	}

}
