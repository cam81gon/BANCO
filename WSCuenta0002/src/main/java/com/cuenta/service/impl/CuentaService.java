package com.cuenta.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgsoft.comun.crud.repo.IGenericoRepo;
import com.cgsoft.comun.crud.service.impl.GenericoServiceImpl;
import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.cgsoft.comun.util.constantes.AdministradorConstantes;
import com.cgsoft.comun.util.conversion.AdministradorConversion;
import com.cuenta.dto.ClienteCuenta;
import com.cuenta.modelo.Cliente;
import com.cuenta.modelo.Cuenta;
import com.cuenta.repo.IClienteRepo;
import com.cuenta.repo.ICuentaRepo;
import com.cuenta.service.ICuentaService;
import com.cuenta.util.Constantes;

import io.vavr.control.Either;
import jakarta.transaction.Transactional;

@Service
public class CuentaService extends GenericoServiceImpl<Cuenta, Integer>  implements ICuentaService{
	
	@Autowired
	private ICuentaRepo iCuentaRepo;	
	@Autowired
	private IClienteRepo iClienteRepo;	
	private EError error;
		
	@Override
	protected IGenericoRepo<Cuenta, Integer> getRepo(){		
		return iCuentaRepo;
	}

	@Override
	public EError registrarCuenta(Entrada<ClienteCuenta> entrada) throws Exception {
		
		Optional<Cliente> cliente = iClienteRepo.findByIdentificacionAndEstado(entrada.getBodyIn().getIdentificacion(), true);
		
		if(cliente.isEmpty()) 
			return new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO02, Constantes.MSM_BUSQUEDA_VACIA );
		
		/*
         * procedo a mapearlo
         */		
		Optional<Cuenta> cuenta = AdministradorConversion.mapOptionalIntoDifOptional(Optional.ofNullable(entrada.getBodyIn()), Cuenta.class);   
		cuenta.get().setCliente(cliente.get());
				
		/*
		 * Guardo respuesta
		 */
		Optional.ofNullable(iCuentaRepo.save(cuenta.get()))
		.ifPresentOrElse((resp)->{error = new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO02);},
						 ()->{error = new EError(Constantes.TIPO_ERROR, Constantes.COD_ERROR_INSERT_CLIENTE,Constantes.MSM_ERROR_INSERT_CLIENTE, Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO02);});
	   	return error;
	}

	@Transactional
	@Override
	public EError actualizarCuenta(Entrada<Cuenta> entrada) {
		//busco por numeo de cuenta la cuenta
		Optional<Cuenta> cuenta = iCuentaRepo.findByNumeroCuentaAndEstado(entrada.getBodyIn().getNumeroCuenta(), true);
		entrada.getBodyIn().setIdCuenta(cuenta.get().getIdCuenta());
		entrada.getBodyIn().setCliente(cuenta.get().getCliente());
		cuenta.ifPresentOrElse(
			(cu)->{
				
				//mapeo a optional
				cu= AdministradorConversion.mapEntityIntoDifEntity(entrada.getBodyIn(), Cuenta.class);
				
				iCuentaRepo.save(cu);
				error = new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO05);				
			},
			()->{
				error = new EError(Constantes.TIPO_ERROR, Constantes.COD_ERROR_MODIFICAR,Constantes.MSM_ERROR_MODIFICAR, Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO05);
			});
		return error;
	}

	@Transactional
	@Override
	public EError eliminarCuenta(Entrada<Cuenta> entrada) {
		Optional<Cuenta> cuenta = iCuentaRepo.findByNumeroCuentaAndEstado(entrada.getBodyIn().getNumeroCuenta(), true);
		cuenta.ifPresentOrElse((c) -> {
									c.setEstado(AdministradorConstantes.inactivo);
									
									iCuentaRepo.save(c);
									error = new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO07);
									},
								()->{
									error = new EError(Constantes.COD_ERROR_ELIMINAR_CUENTA, Constantes.MSM_ERROR_ELIMINAR_CUENTA,
											Constantes.TIPO_ERROR, Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO07);
								});		
		return error;
	}

	

	@Override
	public Either<EError, Cuenta> buscarCuenta(Cuenta entrada) {
		Optional<Cuenta> cuenta = iCuentaRepo.findByNumeroCuentaAndEstado(entrada.getNumeroCuenta(),AdministradorConstantes.activo);	
		if(cuenta.isEmpty()) 
			return Either.left(new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO17, Constantes.MSM_BUSQUEDA_VACIA ));		
		Optional<Cuenta>  salida =  AdministradorConversion.mapOptionalIntoDifOptional(cuenta, Cuenta.class);
		return Either.right(salida.get());		
	
	}



}
