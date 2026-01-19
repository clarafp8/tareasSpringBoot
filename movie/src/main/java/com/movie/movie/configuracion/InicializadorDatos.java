package com.movie.movie.configuracion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.movie.movie.entidad.Director;
import com.movie.movie.entidad.Pelicula;
import com.movie.movie.repositorio.DirectorRepositorio;
import com.movie.movie.repositorio.PeliculaRepositorio;


import com.github.javafaker.Faker;
import com.movie.movie.repositorio.DirectorRepositorio;
import com.movie.movie.repositorio.PeliculaRepositorio;

@Component
public class InicializadorDatos implements CommandLineRunner  {
	 @Autowired
	    private DirectorRepositorio directorRepositorio;

	    @Autowired
	    private PeliculaRepositorio peliculaRepositorio;

	@Override
	public void run(String... args) throws Exception {

		 Faker faker = new Faker();
	        List<Director> directores = new ArrayList<>();

	        // Crear 10 directores
	        for (int i = 0; i < 10; i++) {
	        	Director director = new Director();
	        	director.setNombre(faker.name().fullName());
	            directores.add(director);

	            // Para cada director, crear de 1 a 3 películas
	            int numPeliculas = faker.number().numberBetween(1, 4);
	            for (int j = 0; j < numPeliculas; j++) {
	                Pelicula pelicula = new Pelicula();
	                pelicula.setTitulo(faker.book().title());
	                pelicula.setDirector(director);
	                director.getPeliculas().add(pelicula);
	            }
	        }

	        directorRepositorio.saveAll(directores);
	}

}
