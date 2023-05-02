package com.cuenta.dto;


import lombok.Data;

@Data
public class ClienteCuenta {
	
	private String identificacion;
	
	private String numeroCuenta;
	private String tipoCuenta;
	private Double saldoInicial;
	private boolean estado;

}
