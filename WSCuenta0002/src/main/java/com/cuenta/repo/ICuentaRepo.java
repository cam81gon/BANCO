package com.cuenta.repo;

import java.util.Optional;

import com.cgsoft.comun.crud.repo.IGenericoRepo;
import com.cuenta.modelo.Cuenta;

public interface ICuentaRepo  extends IGenericoRepo<Cuenta, Integer>{
	public Optional<Cuenta> findByNumeroCuentaAndEstado(String numeroCuenta, boolean estado);
	
}
