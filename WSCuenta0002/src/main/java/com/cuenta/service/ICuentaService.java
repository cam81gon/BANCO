package com.cuenta.service;

import com.cgsoft.comun.crud.service.IGenericoService;
import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.cuenta.dto.ClienteCuenta;
import com.cuenta.modelo.Cliente;
import com.cuenta.modelo.Cuenta;

import io.vavr.control.Either;

public interface ICuentaService extends IGenericoService<Cuenta, Integer>{

	public EError registrarCuenta(Entrada<ClienteCuenta> entrada)  throws Exception;
	public EError actualizarCuenta(Entrada<Cuenta> entrada);
	public EError eliminarCuenta(Entrada<Cuenta> entrada);
	
	
	public Either<EError,Cuenta> buscarCuenta(Cuenta entrada);
}
