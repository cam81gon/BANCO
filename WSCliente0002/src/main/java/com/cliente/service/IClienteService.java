package com.cliente.service;

import com.cgsoft.comun.crud.service.IGenericoService;
import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.cliente.model.Cliente;

import io.vavr.control.Either;

public interface IClienteService extends IGenericoService<Cliente, Integer>{

	public EError registrarCliente(Entrada<Cliente> entrada)  throws Exception;
	public EError actualizarCliente(Entrada<Cliente> entrada);
	public EError eliminarCliente(Entrada<Cliente> entrada);
	
	public Either<EError,Cliente> buscarPorIdentificacion(Cliente entrada);
}
