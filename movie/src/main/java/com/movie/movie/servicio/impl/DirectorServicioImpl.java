package com.movie.movie.servicio.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.movie.entidad.Director;
import com.movie.movie.entidad.Pelicula;
import com.movie.movie.repositorio.DirectorRepositorio;
import com.movie.movie.repositorio.PeliculaRepositorio;
import com.movie.movie.servicio.DirectorService;

@Service
public class DirectorServicioImpl implements DirectorService {

    @Autowired
    private DirectorRepositorio directorRepositorio;

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Override
    public List<Director> listarTodos() {
        return directorRepositorio.findAll();
    }

    @Override
    public Optional<Director> obtenerPorId(Long id) {
        return directorRepositorio.findById(id);
    }

    @Override
    public Director guardarDirector(Director director) {
        return directorRepositorio.save(director);
    }

    @Override
    @Transactional
    public void eliminarDirector(Long id) {
        directorRepositorio.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarPeliculasDeDirector(Long id) {
        peliculaRepositorio.deleteByDirectorId(id);
    }

    @Override
    public List<Pelicula> obtenerTodasLasPeliculas() {
        return peliculaRepositorio.findAll();
    }

    @Override
    @Transactional
    public void agregarPeliculaADirector(Long directorId, Long peliculaId) {
        Director director = directorRepositorio.findById(directorId)
                .orElseThrow(() -> new RuntimeException("Director no encontrado"));

        Pelicula pelicula = peliculaRepositorio.findById(peliculaId)
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrado"));

        director.agregaPeliculas(pelicula);
        pelicula.setDirector(director);

        directorRepositorio.save(director);
        peliculaRepositorio.save(pelicula);
    }

    @Override
    public Pelicula buscarPeliculaPorId(Long id) {
        return peliculaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    @Override
    public Director buscarDirectorPorId(Long id) {
        return directorRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    @Override
    public Pelicula guardarCurso(Pelicula pelicula) {
        return peliculaRepositorio.save(pelicula);
    }

    @Override
    public Director obtenerDirectoresPorPelicula(Long peliculaId) {
        Pelicula pelicula = buscarPeliculaPorId(peliculaId);
        return pelicula.getDirector();
    }

    @Override
    @Transactional
    public void crearPeliculaParaDirector(Long directorId, String titulo, int duracion, LocalDate fechaLanzamiento) {
        Director director = directorRepositorio.findById(directorId)
                .orElseThrow(() -> new RuntimeException("Director no encontrado"));

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(titulo);
        pelicula.setDuracion(duracion);
        pelicula.setFechaLanzamiento(fechaLanzamiento);
        pelicula.setDirector(director);

        director.agregaPeliculas(pelicula);
        peliculaRepositorio.save(pelicula);
    }

    @Override
    @Transactional
    public void actualizarDirector(Long directorId, String nombre, LocalDate fechaNacimiento) {
        Optional<Director> directorOpt = directorRepositorio.findById(directorId);
        if (directorOpt.isPresent()) {
            Director director = directorOpt.get();
            director.setNombre(nombre);
            director.setFechaNacimiento(fechaNacimiento);
            directorRepositorio.save(director);
        }
    }

    @Override
    @Transactional
    public void actualizarPelicula(Long peliculaId, String titulo, int duracion, LocalDate fechaLanzamiento) {
        Optional<Pelicula> peliculaOpt = peliculaRepositorio.findById(peliculaId);
        if (peliculaOpt.isPresent()) {
            Pelicula pelicula = peliculaOpt.get();
            pelicula.setTitulo(titulo);
            pelicula.setDuracion(duracion);
            pelicula.setFechaLanzamiento(fechaLanzamiento);
            peliculaRepositorio.save(pelicula);
        }
    }
    
//Paginación
    @Override
    public List<Pelicula> obtenerPeliculasPorDirector(Long directorId) {
        Director director = buscarDirectorPorId(directorId);
        return director.getPeliculas();
    }
    
    @Override
    public Page<Director> listarTodosLosDirectores(Pageable pageable) {
        return directorRepositorio.findAll(pageable);
    }

    @Override
    public Page<Director> buscarDirectoresPorNombre(String nombre, Pageable pageable) {
        // Asumiendo que tienes este método en tu DirectorRepositorio
        return directorRepositorio.findByNombre(nombre, pageable);
    }

    @Override
    public Page<Director> buscarDirectoresContienenNombre(String nombre, Pageable pageable) {
        return directorRepositorio.findByNombreContaining(nombre, pageable);
    }
}