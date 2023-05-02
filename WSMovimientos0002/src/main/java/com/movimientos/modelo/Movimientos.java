package com.movimientos.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="movimientos", schema = "banco")
public class Movimientos {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idMovimiento;
    @Column(name = "fecha", updatable = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fecha;
    @Column(name = "tipo_movimiento", nullable = false, length=255)
	private String tipoMovimiento;
    @Column(name = "valor", nullable = false,length = 20, precision = 3)
	private Double valor;
    @Column(name = "saldo", nullable = false,length = 20, precision = 3)
	private Double saldo;
    
  //@JsonIgnoreProperties("cuenta") 
  	@JsonIgnore
  	@ManyToOne(cascade = CascadeType.ALL)
  	@JoinColumn(name="id", nullable=false)
  	private Cuenta cuenta;
    
    
}
