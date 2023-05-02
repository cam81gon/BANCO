package com.movimientos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.cgsoft.comun.entidades.salida.Respuesta;
import com.cgsoft.comun.entidades.salida.RespuestaSimple;
import com.cgsoft.comun.excepciones.ModeloNotFoundException;

import com.movimientos.dto.CuentaMovimientos;
import com.movimientos.dto.RCuentaMovimientos;
import com.movimientos.service.IMovimientosService;
import com.movimientos.util.Constantes;

import io.vavr.control.Either;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin("*")
public class MovimientoController {

	@Autowired
	private IMovimientosService iMovimientosService; 
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregarCuenta(@Valid @RequestBody Entrada<CuentaMovimientos> entrada) throws Exception {
		EError error = new EError(Constantes.COMPONENTE01, Constantes.CLASE01, Constantes.METODO16);
		Respuesta<RCuentaMovimientos> salida = new Respuesta<RCuentaMovimientos>(entrada.getHeaderIn(), error);
		RespuestaSimple salidaSimple = new RespuestaSimple(entrada.getHeaderIn(), error);

		try {
			Either<EError, RCuentaMovimientos> respuesta = iMovimientosService.registrarMovimientos(entrada);
			
			if (respuesta.isLeft()) {
				salidaSimple = new RespuestaSimple(entrada.getHeaderIn(), respuesta.getLeft());
				return new ResponseEntity<RespuestaSimple>(salidaSimple, HttpStatus.BAD_REQUEST);
			}
			salida.setBodyOut(respuesta.get());
			return new ResponseEntity<Respuesta<RCuentaMovimientos>>(salida, HttpStatus.OK);


		} catch (Exception e) {
			error = new EError(Constantes.COMPONENTE01, Constantes.CLASE01, Constantes.METODO16, Constantes.COD_ERROR,
					e);
			salidaSimple.setError(error);
			throw new ModeloNotFoundException(salidaSimple);
		}

	}
}
