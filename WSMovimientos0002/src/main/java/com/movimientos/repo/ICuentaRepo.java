package com.movimientos.repo;

import java.util.Optional;

import com.cgsoft.comun.crud.repo.IGenericoRepo;
import com.movimientos.modelo.Cuenta;


public interface ICuentaRepo  extends IGenericoRepo<Cuenta, Integer>{
	public Optional<Cuenta> findByNumeroCuentaAndEstado(String numeroCuenta, boolean estado);
	
}
