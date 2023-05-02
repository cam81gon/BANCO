package com.cliente.repo;

import java.util.Optional;

import com.cgsoft.comun.crud.repo.IGenericoRepo;
import com.cliente.model.Cliente;


public interface IClienteRepo extends IGenericoRepo<Cliente, Integer>{
	public Optional<Cliente> findByIdentificacionAndEstado(String Identificacion, boolean estado);
}
 