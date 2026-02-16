package com.movie.movie.configuracion;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.movie.movie.entidad.Director;
import com.movie.movie.entidad.Pelicula;
import com.movie.movie.entidad.enumerado.Rol;
import com.movie.movie.repositorio.DirectorRepositorio;
import com.movie.movie.repositorio.PeliculaRepositorio;

@Component
public class InicializadorDatos implements CommandLineRunner {

    @Autowired
    private DirectorRepositorio directorRepositorio;

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Date startDate = java.util.Date.from(
            LocalDate.of(1980, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()
    );
    Date endDatePeli = java.util.Date.from(
            LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()
    );
    Date endDateDire = java.util.Date.from(
            LocalDate.of(1999, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()
    );

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<Director> directores = new ArrayList<>();

        // Crear 30 directores ficticios
        for (int i = 0; i < 30; i++) {
            Director director = new Director();
            director.setNombre(faker.name().fullName());
            director.setPeliculas(new ArrayList<>());
            director.setFechaNacimiento(faker.date().between(startDate, endDateDire)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            // Crear de 1 a 3 películas
            int numPeliculas = faker.number().numberBetween(1, 4);
            for (int j = 0; j < numPeliculas; j++) {
                Pelicula pelicula = new Pelicula();
                pelicula.setTitulo(faker.book().title());
                pelicula.setDirector(director);
                pelicula.setDuracion(faker.number().numberBetween(80, 180));
                pelicula.setFechaLanzamiento(faker.date().between(startDate, endDatePeli)
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                director.getPeliculas().add(pelicula);
            }
            directores.add(director);
            directorRepositorio.saveAll(directores);

        }

        // CREAR DOS USUARIOS FIJOS
        crearUsuarioSiNoExiste("admin", "admin123", Rol.ADMIN);
        crearUsuarioSiNoExiste("usuario", "usuario123", Rol.USUARIO);

        // Guardar todo en cascada

        System.out.println("Datos inicializados correctamente.");
    }

    private void crearUsuarioSiNoExiste(String nombre, String contrasena, Rol rol) {
        // Verificar que no exista un director con ese nombre
        if (directorRepositorio.findByNombre(nombre) != null) {
            return;
        }

        Director director = new Director();
        director.setNombre(nombre);
        director.setContrasena(passwordEncoder.encode(contrasena));
        director.setRol(rol);
        director.setPeliculas(new ArrayList<>());

        // Asignar una fecha de nacimiento por defecto
        director.setFechaNacimiento(LocalDate.of(1980, 1, 1));

        directorRepositorio.save(director);
    }
}

