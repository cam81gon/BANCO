package com.cuenta.modelo;

import jakarta.persistence.Column;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@MappedSuperclass
public class Persona {
	@Column(name = "identificacion", nullable = false, length=13, unique = true)
	@NotEmpty
	private String identificacion; 
	@Column(name = "nombre", nullable = false, length=255)
	private String nombre; 
	@Column(name = "genero", nullable = false, length=255)
	private String genero; 
	@Column(name = "edad", nullable = false, length=3)
	private String edad;	
	@Column(name = "direccion", nullable = false, length=500)
	private String direccion; 
	@Column(name = "telefono", nullable = false, length=13)
	private String telefono;
}
