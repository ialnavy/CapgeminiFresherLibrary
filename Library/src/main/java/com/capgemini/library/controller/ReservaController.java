//package com.capgemini.library.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.capgemini.library.model.Lector;
//import com.capgemini.library.model.Multa;
//import com.capgemini.library.model.Reserva;
//import com.capgemini.library.service.MultaService;
//import com.capgemini.library.service.ReservaService;
//
//@Controller
//public class ReservaController {
//
//    @Autowired
//    private ReservaService reservaService;
//    
//    @Autowired
//    private MultaService multaService;
//    
//    @Autowired
//    private Lector lector;
//
//    @PostMapping("/reserva")
//    public String createReserva(@ModelAttribute Reserva reserva) {
//    	Multa multa = lector.getMulta();
//        // Comprobar si el Socio está multado
//        if (multa != null) {
//            // Redirigir a una página de error
//            return "redirect:/error";
//        }
//
//        // Crear la reserva
//        reservaService.createReserva(reserva);
//        return "redirect:/reserva/list";
//    }
//
//    @DeleteMapping("/reserva/{id}")
//    public String cancelReserva(@PathVariable String id) {
//        // Cancelar la reserva
//        reservaService.cancelReserva(id);
//        return "redirect:/reserva/list";
//    }
//
//    @PostMapping("/reserva/verify")
//    public String verifyReservas(@RequestParam("copiaID") String copiaId) {
//        // Verificar las reservas para la copia devuelta
//        reservaService.verifyReservas(copiaId);
//        return "redirect:/reserva/list";
//    }
//}
