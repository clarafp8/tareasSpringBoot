package com.movie.movie.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.movie.entidad.Pelicula;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Long>{

}
