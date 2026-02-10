package com.movie.movie.servicio;

import java.util.List;

import com.movie.movie.entidad.Pelicula;


public interface PeliculaServicio {
	Pelicula guardarPelicula(Pelicula pelicula);
    void eliminarPelicula(Long id);
    List<Pelicula> listarTodosLosPelicula();
    Pelicula buscarPeliculaPorId(Long id);
    Pelicula actualizarPelicula(Long id, Pelicula pelicula);
    List<Pelicula> buscarPeliculaPorTitulo(String titulo);
    List<Pelicula> buscarPorTituloParcialSQL(String titulo);
}
