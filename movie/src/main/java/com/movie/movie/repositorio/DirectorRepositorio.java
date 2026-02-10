package com.movie.movie.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.movie.entidad.Director;

public interface DirectorRepositorio extends JpaRepository<Director, Long> {

	Director findByNombre(String nombre);

	List<Director> findAllByNombre(String nombre);

	// Métodos de búsqueda con paginación
	Page<Director> findByNombre(String nombre, Pageable pageable);
	Page<Director> findByNombreContaining(String nombre, Pageable pageable);
}
