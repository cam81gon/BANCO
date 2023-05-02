package com.movimientos.modelo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="cliente", schema = "banco")
public class Cliente extends Persona{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idCliente;
	@Column(name = "contrasena", nullable = false, length=255)
	private String contrasena;
	@Column(name="estado", nullable = false)
	private boolean estado;
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Cuenta> cuentas;
}
