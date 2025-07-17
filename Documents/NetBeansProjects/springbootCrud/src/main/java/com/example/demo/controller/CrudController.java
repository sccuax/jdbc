package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.model.Usuario;

/**
 * Controller adaptado con Thymeleaf
 * @author USERS
 */
@Controller
public class CrudController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Página principal - corregido para manejar tanto "/" como la raíz
    @RequestMapping("/demo")
    public String page(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "home/index"; 
    }
    
    // Endpoint adicional para la raíz sin "/" 
    @GetMapping
    public String home(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "home/index";
    }
    
    // Guardar usuario
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/";
    }
    
    // Editar usuario 
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElse(new Usuario()));
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "home/index";
    }
    
    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/";
    }
}
