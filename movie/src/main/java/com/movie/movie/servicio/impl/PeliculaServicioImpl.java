package com.movie.movie.servicio.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.movie.entidad.Pelicula;
import com.movie.movie.repositorio.PeliculaRepositorio;
import com.movie.movie.servicio.PeliculaServicio;

@Service
public class PeliculaServicioImpl implements PeliculaServicio {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Override
    @Transactional
    public Pelicula guardarPelicula(Pelicula pelicula) {
        return peliculaRepositorio.save(pelicula);
    }

    @Override
    @Transactional
    public void eliminarPelicula(Long id) {
        peliculaRepositorio.deleteById(id);
    }

    @Override
    public List<Pelicula> listarTodasLasPeliculas() {
        return peliculaRepositorio.findAll();
    }

    @Override
    public Pelicula buscarPeliculaPorId(Long id) {
        return peliculaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Pelicula actualizarPelicula(Long id, Pelicula peliculaDetalles) {
        Pelicula pelicula = buscarPeliculaPorId(id);
        
        pelicula.setTitulo(peliculaDetalles.getTitulo());
        pelicula.setDuracion(peliculaDetalles.getDuracion());
        pelicula.setFechaLanzamiento(peliculaDetalles.getFechaLanzamiento());
        
        if (peliculaDetalles.getDirector() != null) {
            pelicula.setDirector(peliculaDetalles.getDirector());
        }
        
        return peliculaRepositorio.save(pelicula);
    }

    @Override
    public List<Pelicula> buscarPeliculaPorTitulo(String titulo) {
        return peliculaRepositorio.findByTitulo(titulo);
    }

    @Override
    public List<Pelicula> buscarPorTituloParcialSQL(String titulo) {
        return peliculaRepositorio.buscarPorTituloParcialSQL(titulo);
    }
    
}