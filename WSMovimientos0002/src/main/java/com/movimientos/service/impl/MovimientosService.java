package com.movimientos.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgsoft.comun.crud.repo.IGenericoRepo;
import com.cgsoft.comun.crud.service.impl.GenericoServiceImpl;
import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.cgsoft.comun.util.constantes.AdministradorConstantes;
import com.cgsoft.comun.util.conversion.AdministradorConversion;
import com.cgsoft.comun.util.fechas.AdministradorFechas;
import com.movimientos.dto.CuentaMovimientos;
import com.movimientos.dto.RCuentaMovimientos;
import com.movimientos.modelo.Cuenta;
import com.movimientos.modelo.Movimientos;
import com.movimientos.repo.ICuentaRepo;
import com.movimientos.repo.IMovimientosRepo;
import com.movimientos.service.IMovimientosService;
import com.movimientos.util.Constantes;

import io.vavr.control.Either;
import jakarta.transaction.Transactional;

@Service
public class MovimientosService extends GenericoServiceImpl<Movimientos, Integer>  implements IMovimientosService{

	@Autowired
	private ICuentaRepo iCuentaRepo;
	@Autowired
	private IMovimientosRepo iMovimientosRepo;	
	private EError error;
	
	@Override
	protected IGenericoRepo<Movimientos, Integer> getRepo() {
		
		return iMovimientosRepo;
	}

	@Transactional
	@Override
	public Either<EError, RCuentaMovimientos> registrarMovimientos(Entrada<CuentaMovimientos> entrada) throws Exception {
	
		Double saldoInicial = 0.000;
		Double movimiento = 0.000;
		Double saldoDisponible = 0.000;
		

		
		Optional<Cuenta> cuenta = iCuentaRepo.findByNumeroCuentaAndEstado(entrada.getBodyIn().getCuenta(), true);
		
		if(cuenta.isEmpty()) 			
			return Either.left(new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO17, Constantes.MSM_BUSQUEDA_VACIA ));		
		
		//saldo actual
		saldoInicial = cuenta.get().getSaldoInicial();
		saldoDisponible = cuenta.get().getSaldoInicial() - entrada.getBodyIn().getValor(); 
		cuenta.get().setSaldoInicial(saldoDisponible);
		
		
		
		/*
         * procedo a mapearlo
         */		
		Optional<Movimientos> movimientos = AdministradorConversion.mapOptionalIntoDifOptional(Optional.ofNullable(entrada.getBodyIn()), Movimientos.class);   
		movimientos.get().setFecha(AdministradorFechas.generarFechaActual());
		movimientos.get().setCuenta(cuenta.get());
		movimientos.get().setSaldo(saldoDisponible);
				
		/*
		 * Guardo respuesta
		 */
		Optional.ofNullable(iMovimientosRepo.save(movimientos.get()))
		.ifPresentOrElse((resp)->{error = new EError(Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO02);},
						 ()->{error = new EError(Constantes.TIPO_ERROR, Constantes.COD_ERROR_INSERT_CLIENTE,Constantes.MSM_ERROR_INSERT_CLIENTE, Constantes.COMPONENTE01, Constantes.CLASE02, Constantes.METODO02);});
	   	

		RCuentaMovimientos cuentMov = new RCuentaMovimientos();
		cuentMov.setFecha(movimientos.get().getFecha());
		cuentMov.setCuenta(movimientos.get().getCuenta().getNumeroCuenta());
		cuentMov.setNombreCliente(movimientos.get().getCuenta().getCliente().getNombre());
		cuentMov.setTipoCuenta(movimientos.get().getCuenta().getTipoCuenta());
		cuentMov.setSaldoInicial(saldoInicial);
		cuentMov.setEstado(AdministradorConstantes.activo);
		cuentMov.setMovimiento(movimientos.get().getValor());
		cuentMov.setSaldoDisponible(saldoDisponible);
		cuentMov.setTipoMovimiento(movimientos.get().getTipoMovimiento());
		
		
		
		return Either.right(cuentMov);
	}


	@Override
	public EError actualizarMovimientos(Entrada<Movimientos> entrada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EError eliminarMovimientos(Entrada<Movimientos> entrada) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Either<EError, Movimientos> buscarMovimientos(Movimientos entrada) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
