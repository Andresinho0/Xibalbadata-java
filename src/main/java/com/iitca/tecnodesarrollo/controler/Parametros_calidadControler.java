package com.iitca.tecnodesarrollo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iitca.tecnodesarrollo.dto.Parametros_calidad;
import com.iitca.tecnodesarrollo.service.Parametros_calidadService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/parametros_calidad")
public class Parametros_calidadControler {

	@Autowired
	private Parametros_calidadService parametros_calidadService;

	@GetMapping
	public ResponseEntity<?> getAllparametros_calidad() {
		return ResponseEntity.ok(parametros_calidadService.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getparametros_calidadById(@PathVariable int id) {
		return parametros_calidadService.getparametros_calidadByid(id)
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// Parámetros de calidad de un pozo: GET /api/parametros_calidad/pozo/{Id_lp}
	@GetMapping("/pozo/{pozoId}")
	public ResponseEntity<?> getparametros_calidadByPozo(@PathVariable int pozoId) {
		return ResponseEntity.ok(parametros_calidadService.getparametros_calidadByPozo(pozoId));
	}

	@PostMapping
	public ResponseEntity<Parametros_calidad> addparametros_calidad(@RequestBody Parametros_calidad parametros_calidad) {
		return new ResponseEntity<>(parametros_calidadService.saveparametros_calidad(parametros_calidad), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Parametros_calidad> updateparametros_calidad(@RequestBody Parametros_calidad parametros_calidad, @PathVariable int id) {
		return new ResponseEntity<Parametros_calidad>(parametros_calidadService.updateparametros_calidad(id, parametros_calidad), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteparametros_calidad(@PathVariable int id) {
		parametros_calidadService.deleteparametros_calidad(id);
		return ResponseEntity.ok("Se elimino");
	}
}