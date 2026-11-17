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

import com.iitca.tecnodesarrollo.dto.Alertas_pozos;
import com.iitca.tecnodesarrollo.dto.CierreRequest;
import com.iitca.tecnodesarrollo.service.Alertas_pozosService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/alertas_pozos")
public class Alertas_pozosControler {

	//desde aqui
	
	@Autowired
	private Alertas_pozosService alertas_pozosService;
	
	@GetMapping
	public ResponseEntity<?> getAllalertas_pozos(){
		System.out.println("Estoy en el Get All");
		return ResponseEntity.ok(alertas_pozosService.listAll());
	}
	
	@GetMapping("/pendientes")
	public ResponseEntity<?> getalertasPendientes(){
		return ResponseEntity.ok(alertas_pozosService.listPendientes());
	}

	@GetMapping("/historial")
	public ResponseEntity<?> getHistorialalertas(){
		return ResponseEntity.ok(alertas_pozosService.listHistorial());
	}

	@GetMapping("/historial/pozo/{id_lp}")
	public ResponseEntity<?> getHistorialalertasByIdPozo(@PathVariable int id_lp){
		return ResponseEntity.ok(alertas_pozosService.listHistorialPorPozo(id_lp));
	}

	@PutMapping("/{id_al}/completar")
	public ResponseEntity<?> completaralerta(@PathVariable int id_al, @RequestBody(required = false) CierreRequest cierre){
		try {
			return alertas_pozosService.completaralerta(id_al, cierre)
					.<ResponseEntity<?>>map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la alerta " + id_al));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@GetMapping("/{id_al}")
	public ResponseEntity<?> getPozoById(@PathVariable int id_al){
		return ResponseEntity.ok(alertas_pozosService.getalertas_pozosByid(id_al));
	}
	@GetMapping("/pozo/{id_lp}")
	public ResponseEntity<?> getalertas_pozosByIdPozo(@PathVariable int id_lp){
		return ResponseEntity.ok(alertas_pozosService.getalertas_pozosByIdPozo(id_lp));
	}
	
	//hasta aqui
	
	@PostMapping
	public ResponseEntity<Alertas_pozos> addalertas_pozos(@RequestBody Alertas_pozos alertas_pozos){
		System.out.println("En el post");
		System.out.println(alertas_pozos.getAl_clave_de_pozo());
		System.out.println(alertas_pozos.getAl_tipo_de_alerta());
		System.out.println(alertas_pozos.getAl_comentarios());
		System.out.println(alertas_pozos.getAl_fechacap());
		System.out.println(alertas_pozos.getAl_operador());
		return new ResponseEntity<>(alertas_pozosService.savealertas_pozos(alertas_pozos),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id_al}")
	public ResponseEntity<Alertas_pozos> updatealertas_pozos(@RequestBody Alertas_pozos alertas_pozos, @PathVariable int id_al){
		return new ResponseEntity<Alertas_pozos>(alertas_pozosService.updatealertas_pozos(id_al, alertas_pozos),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id_al}")
	public ResponseEntity<?> deletealertas_pozos(@PathVariable int id_al){
		alertas_pozosService.deletealertas_pozos(id_al);
		return ResponseEntity.ok("Se elimino");
	}
}