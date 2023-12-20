/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dosideas.controller;

import com.dosideas.domain.Provincia;
import com.dosideas.service.IProvinciaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller MVC
 * @author Gabriel Romero
 */
@Controller
public class ProvinciaController {

    @Autowired
    private IProvinciaService provinciaService;

    @GetMapping("/provincias")
    public String listarTodasLasProvincias(Model model) {
        List<Provincia> provincias = provinciaService.buscarTodasLasProvincias();
        model.addAttribute("provincias", provincias);
        return "provincia";
    }

    @GetMapping("/provincias/crear")
    public String formularioNuevaProvincia(Model model) {
        Provincia provincia = new Provincia();
        model.addAttribute("provincia", provincia);
        return "crear_provincia";
    }

    @PostMapping("/provincias")
    public String guardarProvincia(@ModelAttribute("provincia") Provincia provincia) {
        provinciaService.guardarProvincia(provincia);
        return "redirect:/provincias";
    }

    @GetMapping("/provincias/editar/{id}")
    public String formularioParaEditarProvincia(@PathVariable Long id, Model model) {
        model.addAttribute("provincia", provinciaService.buscarPorId(id));
        return "editar_provincia";
    }

    @PostMapping("/provincias/editar")
    public String actualizarProvincia(@ModelAttribute("provincia") Provincia provincia, Model model) {
        Provincia provinciaExistente = provinciaService.buscarPorId(provincia.getId());
        provinciaExistente.setNombre(provincia.getNombre());

        provinciaService.modificarProvincia(provinciaExistente);
        return "redirect:/provincias";
    }

    @GetMapping("/provincias/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        provinciaService.eliminarProvincia(id);
        return "redirect:/provincias";
    }

    @GetMapping("/provincias/buscar")
    public String buscarProvinciaPorId(Model model) {
        return "buscar_provincia";
    }

}
