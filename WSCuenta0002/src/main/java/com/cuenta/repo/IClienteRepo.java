package com.cuenta.repo;

import java.util.Optional;

import com.cgsoft.comun.crud.repo.IGenericoRepo;
import com.cuenta.modelo.Cliente;


public interface IClienteRepo extends IGenericoRepo<Cliente, Integer>{
	public Optional<Cliente> findByIdentificacionAndEstado(String Identificacion, boolean estado);
}
