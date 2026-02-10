package com.movie.movie.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.movie.entidad.Director;
import com.movie.movie.entidad.Pelicula;
import com.movie.movie.servicio.DirectorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/directores")
public class DirectorControlador {

	@Autowired
	private DirectorService directorService;

	@GetMapping
	public String listarDirectores(Model model,
			@PageableDefault(size = 20) Pageable page,
			@RequestParam(required = false) String nombre) {
		
		Page<Director> directores;
		if (nombre != null && !nombre.trim().isEmpty()) {
			directores = directorService.buscarDirectoresContienenNombre(nombre, page);
		} else {
			directores = directorService.listarTodosLosDirectores(page);
		}
		
		model.addAttribute("directores", directores);
		model.addAttribute("total", directores.getTotalElements());
		model.addAttribute("nombreBusqueda", nombre);
		
		return "directores/listado";
	}

	@GetMapping("/nuevo")
	public String mostrarFormularioDeNuevoDirector(Model model) {
		model.addAttribute("director", new Director());
		return "directores/formulario";
	}

	@PostMapping("/nuevo")
	public String guardarDirector(@Valid Director director, BindingResult result) {
		if (result.hasErrors()) {
			return "directores/formulario";
		}
		directorService.guardarDirector(director);
		return "redirect:/directores";
	}

	@GetMapping("/ver/{id}")
	public String verDetalleDirector(@PathVariable Long id, Model model) {
		Optional<Director> director = directorService.obtenerPorId(id);

		if (director.isPresent()) {
			model.addAttribute("director", director.get());
			model.addAttribute("pelicula", directorService.obtenerTodasLasPeliculas().stream().distinct());

			return "directores/detalle-director";
		} else {
			return "redirect:/error";
		}
	}

	@GetMapping("/editar/{id}")
	public String mostrarEditarDirector(@PathVariable Long id, Model model) {
		Optional<Director> directorOpt = directorService.obtenerPorId(id);
		if (directorOpt.isPresent()) {
			Director director = directorOpt.get();
			model.addAttribute("director", director);

			List<Pelicula> peliculas = directorService.obtenerPeliculasPorDirector(id);
			model.addAttribute("peliculas", peliculas);

			return "directores/editar-director";
		} else {
			return "redirect:/directores";
		}
	}

	@PostMapping("/editar/{id}/director")
	public String actualizarDirector(@PathVariable Long id, @RequestParam String nombre,
			@RequestParam String fechaNacimiento) {
		directorService.actualizarDirector(id, nombre, LocalDate.parse(fechaNacimiento));
		return "redirect:/directores";
	}

	@PostMapping("/editar/{id}/pelicula")
	public String actualizarPelicula(@PathVariable Long id, @RequestParam Long peliculaId, @RequestParam String titulo,
			@RequestParam int duracion, @RequestParam String fechaLanzamiento) {
		directorService.actualizarPelicula(peliculaId, titulo, duracion, LocalDate.parse(fechaLanzamiento));
		return "redirect:/directores/editar/" + id;
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarDirector(@PathVariable Long id, Model model) {
		Optional<Director> director = directorService.obtenerPorId(id);

		if (director.isPresent()) {
			model.addAttribute("director", director.get());
			return "directores/eliminar-director";
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/eliminar/{id}/confirmar")
	public String eliminarDirector(@PathVariable Long id) {
		Optional<Director> directorOpt = directorService.obtenerPorId(id);
		if (directorOpt.isPresent()) {
			Director director = directorOpt.get();

			// Eliminar películas primero si existen
			directorService.eliminarPeliculasDeDirector(id);

			// Eliminar director
			directorService.eliminarDirector(id);

		}
		return "redirect:/directores";
	}

	@GetMapping("/peliculas")
	public String mostrarDirectores(@RequestParam(value = "directorId", required = false) Long directorId,
			Model model) {

		List<Director> directores = directorService.listarTodos();
		model.addAttribute("directores", directores);
		// Inicializamos variables
		List<Pelicula> peliculas = null;
		Director director = null;

		// Si se selecciona una película
		if (directorId != null) {
			director = directorService.buscarDirectorPorId(directorId);
			if (director != null) {
				peliculas = directorService.obtenerPeliculasPorDirector(directorId);
			}
		}

		// Agregamos al modelo
		model.addAttribute("peliculas", peliculas); // objeto seleccionado
		model.addAttribute("director", director); // lista de directores

		return "directores/director-pelicula";
	}

	@PostMapping("/agregarPelicula")
	public String agregarPeliculaADirector(@RequestParam Long directorId, @RequestParam String titulo,
			@RequestParam int duracion, @RequestParam LocalDate fechaLanzamiento,
			RedirectAttributes redirectAttributes) {

		try {
			directorService.crearPeliculaParaDirector(directorId, titulo, duracion, fechaLanzamiento);

			redirectAttributes.addFlashAttribute("mensajeExito", "Película creada y asignada correctamente.");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensajeError", "Error al crear la película.");
			System.out.print(e);
		}
		return "redirect:/directores/ver/" + directorId;
	}

}
