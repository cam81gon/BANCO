package com.movimientos.service;

import com.cgsoft.comun.crud.service.IGenericoService;
import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.movimientos.dto.CuentaMovimientos;
import com.movimientos.dto.RCuentaMovimientos;
import com.movimientos.modelo.Movimientos;

import io.vavr.control.Either;

public interface IMovimientosService extends IGenericoService<Movimientos, Integer>{

	public Either<EError, RCuentaMovimientos>  registrarMovimientos(Entrada<CuentaMovimientos> entrada)  throws Exception;
	public EError actualizarMovimientos(Entrada<Movimientos> entrada);
	public EError eliminarMovimientos(Entrada<Movimientos> entrada);
	
	
	public Either<EError,Movimientos> buscarMovimientos(Movimientos entrada);
}
