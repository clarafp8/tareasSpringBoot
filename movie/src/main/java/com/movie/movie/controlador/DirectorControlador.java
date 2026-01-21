package com.movie.movie.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.movie.entidad.Director;
import com.movie.movie.servicio.DirectorServicio;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/directores")
public class DirectorControlador {
	
	@Autowired
	private DirectorServicio directorServicio;
	
	@GetMapping
	public String listarDirectores(Model model) {
		List<Director> directores = directorServicio.listarTodos();
		model.addAttribute("directores",directores);
		return "directores/listado";
	}
	
	@GetMapping("/nuevo")
	public String mostrarFormularioDeNuevoEstudiante(Model model) {
		model.addAttribute("director", new Director());
		return "directores/formulario";
	}
	@PostMapping("/nuevo")
	public String guardarEstudiante(@Valid Director director, BindingResult result) {
		if (result.hasErrors()) {
			return "directores/formulario";
		}
		directorServicio.guardarDirector(director);
		return "redirect:/directores";
	}
	

}
