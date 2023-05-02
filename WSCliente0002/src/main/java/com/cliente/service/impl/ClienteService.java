package com.cliente.service.impl;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgsoft.comun.crud.repo.IGenericoRepo;
import com.cgsoft.comun.crud.service.impl.GenericoServiceImpl;
import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.cgsoft.comun.util.constantes.AdministradorConstantes;
import com.cgsoft.comun.util.conversion.AdministradorConversion;
import com.cliente.model.Cliente;
import com.cliente.repo.IClienteRepo;
import com.cliente.service.IClienteService;
import com.cliente.util.Constantes;

import io.vavr.control.Either;
import jakarta.transaction.Transactional;

@Service
public class ClienteService extends GenericoServiceImpl<Cliente, Integer>  implements IClienteService{


	@Autowired
	private IClienteRepo iClienteRepo;	
	private EError error;
		
	@Override
	protected IGenericoRepo<Cliente, Integer> getRepo(){		
		return iClienteRepo;
	}
	
	
	
	@Override
	public EError registrarCliente(Entrada<Cliente> entrada) throws Exception {
			
		Optional<Cliente> respuesta = Optional.ofNullable(iClienteRepo.save(entrada.getBodyIn()));
		/*
		 * Arma respuesta
		 */
		respuesta.ifPresentOrElse((resp)->{error = new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO02);},
								  ()->{error = new EError(Constantes.TIPO_ERROR, Constantes.COD_ERROR_INSERT_CLIENTE,Constantes.MSM_ERROR_INSERT_CLIENTE, Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO02);});
	   	return error;
	}

	@Transactional
	@Override
	public EError actualizarCliente(Entrada<Cliente> entrada) {
		//busco por id a la persona 
				Optional<Cliente> cliente = iClienteRepo.findByIdentificacionAndEstado(entrada.getBodyIn().getIdentificacion(), true);
				entrada.getBodyIn().setIdCliente(cliente.get().getIdCliente());
				cliente.ifPresentOrElse(
					(cli)->{
						
						//mapeo a optional
						cli= AdministradorConversion.mapEntityIntoDifEntity(entrada.getBodyIn(), Cliente.class);
						
						iClienteRepo.save(cli);
						error = new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO05);				
					},
					()->{
						error = new EError(Constantes.TIPO_ERROR, Constantes.COD_ERROR_MODIFICAR,Constantes.MSM_ERROR_MODIFICAR, Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO05);
					});
				return error;
	}

	@Transactional
	@Override
	public EError eliminarCliente(Entrada<Cliente> entrada) {
		Optional<Cliente> cliente = iClienteRepo.findByIdentificacionAndEstado(entrada.getBodyIn().getIdentificacion(), true);
		cliente.ifPresentOrElse((c) -> {
									c.setEstado(AdministradorConstantes.inactivo);
									c.getCuentas().stream().forEach(cuenta -> cuenta.setEstado(AdministradorConstantes.inactivo));
									
									iClienteRepo.save(c);
									error = new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO07);
									},
								()->{
									error = new EError(Constantes.COD_ERROR_ELIMINAR_PERSONA, Constantes.MSM_ERROR_ELIMINAR_PERSONA,
											Constantes.TIPO_ERROR, Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO07);
								});		
		return error;
	}

	@Override
	public Either<EError, Cliente> buscarPorIdentificacion(Cliente entrada) {
		Optional<Cliente> cliente = iClienteRepo.findByIdentificacionAndEstado(entrada.getIdentificacion(),AdministradorConstantes.activo);	
		if(cliente.isEmpty()) 
			return Either.left(new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO17, Constantes.MSM_BUSQUEDA_VACIA ));		
		Optional<Cliente>  salida =  AdministradorConversion.mapOptionalIntoDifOptional(cliente, Cliente.class);
		return Either.right(salida.get());	
	}

	

}
