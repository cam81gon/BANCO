package com.cuenta.modelo;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="cuenta", schema = "banco")
public class Cuenta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idCuenta;
	@Column(name = "numero_cuenta", nullable = false, length=255, unique = true)
	private String numeroCuenta;
	@Column(name = "tipo_cuenta", nullable = false, length=255)
	private String tipoCuenta;
	@Column(name = "saldo_inicial", nullable = false,length = 20, precision = 3)
	private Double saldoInicial;
	@Column(name = "estado", nullable = false)
	private boolean estado;
	
	//@JsonIgnoreProperties("cuenta") 
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_cliente", nullable=false)
	private Cliente cliente;
	
}
