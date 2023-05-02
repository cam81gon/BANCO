package com.movimientos.dto;

import java.util.Calendar;


import lombok.Data;

@Data
public class CuentaMovimientos {

	private String cuenta;
	
	
	private Calendar fecha;
	private String tipoMovimiento;
	private Double valor;
	//private Double saldo;
}
