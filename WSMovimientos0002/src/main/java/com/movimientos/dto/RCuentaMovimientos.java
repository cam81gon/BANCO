package com.movimientos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RCuentaMovimientos {

	private LocalDateTime  fecha;
	private String nombreCliente;
	private String cuenta;
	private String tipoCuenta;
	private String tipoMovimiento;
	private Double saldoInicial;
	private boolean estado;
	private Double movimiento;
	private Double saldoDisponible;
	
}
