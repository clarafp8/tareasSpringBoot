package com.movie.movie.servicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.movie.entidad.Pelicula;
import com.movie.movie.entidad.Director;
import com.movie.movie.repositorio.DirectorRepositorio;
import com.movie.movie.repositorio.PeliculaRepositorio;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class DirectorServicio {
	 @Autowired
	    private DirectorRepositorio directorRepositorio;
	    
	    @Autowired
	    private PeliculaRepositorio peliculaRepositorio;

    public DirectorServicio (DirectorRepositorio directorRepositorio) {
        this.directorRepositorio = directorRepositorio;
    }

    public List<Director> listarTodos() {
        return directorRepositorio.findAll();
    }

    public Optional<Director> obtenerPorId(Long id) {
        return directorRepositorio.findById(id);
    }

    public Director guardarDirector(Director director) {
        return directorRepositorio.save(director);
    }
    @Transactional

    public void eliminarDirector(Long id) {
    	directorRepositorio.deleteById(id);
    }
    @Transactional

    public void eliminarPeliculasDeDirector(Long id) {
    	peliculaRepositorio.deleteByDirectorId(id);
    }


	public List<Pelicula> obtenerTodasLasPeliculas(){
		return peliculaRepositorio.findAll();
	}
	// La etiqueta @Transactional facilita el manejo correcto y eficiente de las transacciones en la base de datos, ayudando a asegurar la integridad y consistencia de los datos.
	/**
	 *
	 * 
	 * @param directorId
	 * @param peliculaId
	 */
	@Transactional
	public void agregarPeliculaADirector(Long directorId, Long peliculaId) {
		Director Director = directorRepositorio.findById(directorId)
                .orElseThrow(() -> new RuntimeException("Director no encontrado"));

        Pelicula Pelicula = peliculaRepositorio.findById(peliculaId)
                .orElseThrow(() -> new RuntimeException("Pelicula no encontrado"));
        
        
        Director.agregaPeliculas(Pelicula);
        Pelicula.setDirector(Director);
        
       
        directorRepositorio.save(Director);
        peliculaRepositorio.save(Pelicula); 

	}


	public Pelicula buscarPeliculaPorId(Long id) {
		return peliculaRepositorio.findById(id).get();
	}
	public Director buscarDirectorPorId(Long id) {
		return directorRepositorio.findById(id).get();
	}
	
	public Pelicula guardarCurso(Pelicula Pelicula) {
		return peliculaRepositorio.save(Pelicula);
	}


	public Director obtenerDirectoresPorPelicula(Long peliculaId) {
		 Pelicula pelicula = buscarPeliculaPorId(peliculaId);
		return pelicula.getDirector();
	}
	
	public void crearPeliculaParaDirector(
	        Long directorId,
	        String titulo,
	        int duracion,
	        LocalDate fechaLanzamiento) {

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

	    public List<Pelicula> obtenerPeliculasPorDirector(Long directorId) {
			 Director director = buscarDirectorPorId(directorId);
			return director.getPeliculas();
		}
}
