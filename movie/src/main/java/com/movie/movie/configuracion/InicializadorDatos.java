package com.movie.movie.configuracion;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
	    
	    Date startDate = java.util.Date.from(
	            LocalDate.of(1980, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()
	        );
	        Date endDate = java.util.Date.from(
	            LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()
	        );

	@Override
	public void run(String... args) throws Exception {

		 Faker faker = new Faker();
	        List<Director> directores = new ArrayList<>();
	     // Crear fechas límite
	        
	        // Crear 10 directores
	        for (int i = 0; i < 10; i++) {
	            Director director = new Director();
	            director.setNombre(faker.name().fullName());

	            // Inicializar lista de películas
	            director.setPeliculas(new ArrayList<>());

	            // Crear de 1 a 3 películas por director
	            int numPeliculas = faker.number().numberBetween(1, 4);
	            for (int j = 0; j < numPeliculas; j++) {
	                Pelicula pelicula = new Pelicula();
	                pelicula.setTitulo(faker.book().title());
	                pelicula.setDirector(director);

	                // Asignar campos obligatorios
	                pelicula.setDuracion(faker.number().numberBetween(80, 180)); // minutos
	                pelicula.setFechaLanzamiento(faker.date().between(startDate,
	                		endDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

	                // Añadir película al director
	                director.getPeliculas().add(pelicula);
	            }

	            directores.add(director);
	        }

	        // Guardar todo en cascada
	        directorRepositorio.saveAll(directores);

	        System.out.println("Datos inicializados correctamente.");
	    }

}
