package com.movie.movie.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.movie.movie.entidad.Director;
import com.movie.movie.repositorio.DirectorRepositorio;

@Service
public class DirectorServicio {

	private final DirectorRepositorio directorRepositorio ;
    public DirectorServicio (DirectorRepositorio directorRepositorio) {
        this.directorRepositorio = directorRepositorio;
    }

    public List<Director> listarTodos() {
        return directorRepositorio.findAll();
    }

    public Optional<Director> obtenerPorId(Long id) {
        return directorRepositorio.findById(id);
    }

    public Director guardarAutor(Director director) {
        return directorRepositorio.save(director);
    }

    public void eliminarAutor(Long id) {
    	directorRepositorio.deleteById(id);
    }
}
