package com.movie.movie.servicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.movie.movie.entidad.Director;
import com.movie.movie.entidad.Pelicula;

public interface DirectorService {
    List<Director> listarTodos();
    Optional<Director> obtenerPorId(Long id);
    Director guardarDirector(Director director);
    void eliminarDirector(Long id);
    void eliminarPeliculasDeDirector(Long id);
    List<Pelicula> obtenerTodasLasPeliculas();
    void agregarPeliculaADirector(Long directorId, Long peliculaId);
    Pelicula buscarPeliculaPorId(Long id);
    Director buscarDirectorPorId(Long id);
    Pelicula guardarCurso(Pelicula pelicula);
    Director obtenerDirectoresPorPelicula(Long peliculaId);
    void crearPeliculaParaDirector(Long directorId, String titulo, int duracion, LocalDate fechaLanzamiento);
    void actualizarDirector(Long directorId, String nombre, LocalDate fechaNacimiento);
    void actualizarPelicula(Long peliculaId, String titulo, int duracion, LocalDate fechaLanzamiento);
    List<Pelicula> obtenerPeliculasPorDirector(Long directorId);
    
    //Métodos de paginación
    Page<Director> listarTodosLosDirectores(Pageable pageable);

    Page<Director> buscarDirectoresPorNombre(String nombre, Pageable pageable);

    Page<Director> buscarDirectoresContienenNombre(String nombre, Pageable pageable);
}