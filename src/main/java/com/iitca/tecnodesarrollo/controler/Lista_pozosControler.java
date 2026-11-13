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

import com.iitca.tecnodesarrollo.dto.Lista_pozos;
import com.iitca.tecnodesarrollo.service.Lista_pozosService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lista_pozos")
public class Lista_pozosControler {

	//desde aqui
	
	@Autowired
	private Lista_pozosService lista_pozosService;
	
	@GetMapping
	public ResponseEntity<?> getAlllista_pozos(){
		System.out.println("Estoy en el Get All");
		return ResponseEntity.ok(lista_pozosService.listAll());
	}
	
	@GetMapping("/{id_lp}")
	public ResponseEntity<?> getlista_pozosById(@PathVariable int id_lp){
		return ResponseEntity.ok(lista_pozosService.getlista_pozosByid(id_lp));
	}
	
	//hasta aqui
	
	@PostMapping
	public ResponseEntity<Lista_pozos> addlista_pozos(@RequestBody Lista_pozos lista_pozos){
		System.out.println("En el post");
		System.out.println(lista_pozos.getL_poz_clave());
		System.out.println(lista_pozos.getLp_Nombre());
		System.out.println(lista_pozos.getLp_ramal());
		//System.out.println(lista_pozos.getLp_Ubicación());
		System.out.println(lista_pozos.getLp_Profundidad_perforación());
		System.out.println(lista_pozos.getLp_Diámetro_perforación());
		System.out.println(lista_pozos.getLp_Diámetro_ADEME());
		System.out.println(lista_pozos.getLp_subestacion());
		System.out.println(lista_pozos.getLp_circuito_electrico());
		System.out.println(lista_pozos.getLp_aporta_acueducto());
		System.out.println(lista_pozos.getLp_aporta_comunidad());
		System.out.println(lista_pozos.getLp_aporta_riego());
		System.out.println(lista_pozos.getLp_pa_cdmx());
		System.out.println(lista_pozos.getLp_telemetria());
		System.out.println(lista_pozos.getLp_utr());
		System.out.println(lista_pozos.getLp_altura());
		System.out.println(lista_pozos.getLp_estado());
		System.out.println(lista_pozos.getLp_municipio());
		System.out.println(lista_pozos.getLp_dependencia_informante());
		System.out.println(lista_pozos.getLp_columna_succion());

		return new ResponseEntity<>(lista_pozosService.savelista_pozos(lista_pozos),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id_lp}")
	public ResponseEntity<Lista_pozos> updatelista_pozos(@RequestBody Lista_pozos lista_pozos, @PathVariable int id_lp){
		return new ResponseEntity<Lista_pozos>(lista_pozosService.updatelista_pozos(id_lp, lista_pozos),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id_lp}")
	public ResponseEntity<?> deletelista_pozos(@PathVariable int id_lp){
		lista_pozosService.deletelista_pozos(id_lp);
		return ResponseEntity.ok("Se elimino");
	}
}
