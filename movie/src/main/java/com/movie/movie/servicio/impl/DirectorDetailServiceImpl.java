package com.movie.movie.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.movie.movie.entidad.Director;
import com.movie.movie.repositorio.DirectorRepositorio;


@Component

public class DirectorDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private DirectorRepositorio directorRepositorio;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String username_ = normalizar(username);

		Director director = directorRepositorio.findByNombre(username_);
		
		if(director == null) throw new UsernameNotFoundException("Usuario no encontrado");
		
		
		return User.withUsername(username)
				.roles(director.getRol().toString())
				.password(director.getContrasena())
				.build();
	}
	
	private String normalizar(String username) {
        if (username == null || username.isBlank()) {
            throw new UsernameNotFoundException("Username vacío");
        }
        // Si en tu BBDD guardas tal cual, deja solo trim().
        // Si guardas en minúsculas, usa lowerCase.
        return username.trim().toLowerCase();
    }
}