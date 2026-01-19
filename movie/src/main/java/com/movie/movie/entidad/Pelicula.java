package com.movie.movie.entidad;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class Pelicula {
	   
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull(message = "El título es obligatorio")
	    private String titulo;

	    @NotNull(message = "La fecha de lanzamiento es obligatoria")
	    @Past(message = "La fecha de lanzamiento debe ser en el pasado")
	    private LocalDate fechaLanzamiento;

	    @NotNull(message = "La duración es obligatoria")
	    private int duracion;

	    @ManyToOne (fetch = FetchType.LAZY)
	    @JoinColumn(name = "director_id", nullable = false)
	    private Director director;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int i) {
		this.duracion = i;
	}
    
	 public void setDirector(Director director) {
	        this.director = director;
	    }
    

}
