package com.movie.movie.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.movie.movie.entidad.Pelicula;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Long>{
    void deleteByDirectorId(Long directorId);
    
 // Método para encontrar cursos por su título
    List<Pelicula> findByTitulo(String titulo);
    
    @Query(value = "SELECT * FROM pelicula WHERE titulo LIKE %?1%", nativeQuery = true)
    List<Pelicula> buscarPorTituloParcialSQL(String titulo);

}
