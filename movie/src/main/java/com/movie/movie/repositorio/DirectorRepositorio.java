package com.movie.movie.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.movie.entidad.Director;

public interface DirectorRepositorio extends JpaRepository<Director, Long> {


}
