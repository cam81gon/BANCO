package com.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgsoft.comun.entidades.EError;
import com.cgsoft.comun.entidades.entrada.Entrada;
import com.cgsoft.comun.entidades.salida.RespuestaSimple;
import com.cgsoft.comun.entidades.salida.Respuesta;
import com.cgsoft.comun.excepciones.ModeloNotFoundException;
import com.cliente.model.Cliente;
import com.cliente.service.IClienteService;
import com.cliente.util.Constantes;

import io.vavr.control.Either;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private IClienteService iClienteService;
	
	@PostMapping("/agregar")
	public ResponseEntity<?> agregarCliente(@Valid @RequestBody Entrada<Cliente> entrada) throws Exception {
		EError error;		
		RespuestaSimple salidaSimple = new RespuestaSimple(entrada.getHeaderIn(), null);
		try {
			error = iClienteService.registrarCliente(entrada);
			salidaSimple.setError(error);
			return new ResponseEntity<RespuestaSimple>(salidaSimple, HttpStatus.CREATED);
		} catch (Exception e) {
			salidaSimple.setError(new EError(Constantes.COMPONENTE01, Constantes.CLASE01, Constantes.METODO04,
					Constantes.COD_ERROR, e));
			throw new ModeloNotFoundException(salidaSimple);
		}

	}
	
	
	@PutMapping("/modificar")
	public ResponseEntity<?> modificarCliente(@Valid @RequestBody Entrada<Cliente> entrada) throws Exception {
		RespuestaSimple salidaSimple = new RespuestaSimple(entrada.getHeaderIn(), null);
		try {
			EError error = iClienteService.actualizarCliente(entrada);

			salidaSimple.setError(error);
			return new ResponseEntity<RespuestaSimple>(salidaSimple, HttpStatus.OK);
		} catch (Exception e) {
			salidaSimple.setError(new EError(Constantes.COMPONENTE01, Constantes.CLASE01, Constantes.METODO08,
					Constantes.COD_ERROR, e));
			throw new ModeloNotFoundException(salidaSimple);
		}

	}

	@DeleteMapping("/eliminar")
	public ResponseEntity<?> eliminarCliente(@Valid @RequestBody Entrada<Cliente> entrada) throws Exception {
		RespuestaSimple salidaSimple = new RespuestaSimple(entrada.getHeaderIn(), null);
		try {
			EError error = iClienteService.eliminarCliente(entrada);

			salidaSimple.setError(error);
			return new ResponseEntity<RespuestaSimple>(salidaSimple, HttpStatus.OK);
		} catch (Exception e) {
			salidaSimple.setError(new EError(Constantes.COMPONENTE01, Constantes.CLASE01, Constantes.METODO09,
					Constantes.COD_ERROR, e));
			throw new ModeloNotFoundException(salidaSimple);
		}
	}

	@GetMapping("/obtenerPorIdentificacion")
	public ResponseEntity<?> obtenerPorIdentificacion(@RequestBody Entrada<Cliente> entrada)
			throws ModeloNotFoundException {

		EError error = new EError(Constantes.COMPONENTE01, Constantes.CLASE01, Constantes.METODO16);
		Respuesta<Cliente> salida = new Respuesta<Cliente>(entrada.getHeaderIn(), error);
		RespuestaSimple salidaSimple = new RespuestaSimple(entrada.getHeaderIn(), error);

		try {
			
			Either<EError, Cliente> respuesta = iClienteService.buscarPorIdentificacion(entrada.getBodyIn());

			if (respuesta.isLeft()) {
				salidaSimple = new RespuestaSimple(entrada.getHeaderIn(), respuesta.getLeft());
				return new ResponseEntity<RespuestaSimple>(salidaSimple, HttpStatus.BAD_REQUEST);
			}
			salida.setBodyOut(respuesta.get());
			return new ResponseEntity<Respuesta<Cliente>>(salida, HttpStatus.OK);

		} catch (Exception e) {
			error = new EError(Constantes.COMPONENTE01, Constantes.CLASE01, Constantes.METODO16, Constantes.COD_ERROR,
					e);
			salidaSimple.setError(error);
			throw new ModeloNotFoundException(salidaSimple);
		}
	}
	

}
