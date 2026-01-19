package com.movie.movie.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.movie.entidad.Director;
import com.movie.movie.servicio.DirectorServicio;

@Controller
@RequestMapping("/directores")
public class DirectorControlador {
	
	@Autowired
	private DirectorServicio directorServicio;
	
	@GetMapping
	public String listarDirectores(Model model) {
		List<Director> directores = directorServicio.listarTodos();
		model.addAttribute("directores",directores);
		return "directores/lista";
	}

	

}
