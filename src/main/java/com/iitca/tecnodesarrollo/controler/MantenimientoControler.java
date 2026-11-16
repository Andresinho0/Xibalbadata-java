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

import com.iitca.tecnodesarrollo.dto.CierreRequest;
import com.iitca.tecnodesarrollo.dto.Mantenimiento;
import com.iitca.tecnodesarrollo.service.MantenimientoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mantenimiento")
public class MantenimientoControler {
    
    @Autowired
    private MantenimientoService mantenimientoService;
    
    @GetMapping
    public ResponseEntity<?> getAllmantenimiento(){
        System.out.println("Estoy en el Get All");
        return ResponseEntity.ok(mantenimientoService.listAll());
    }
    
    @GetMapping("/{id_mtto}")
    public ResponseEntity<?> getmantenimientoById(@PathVariable int id_mtto){
        return ResponseEntity.ok(mantenimientoService.getmantenimientoByid(id_mtto));
    }
    
    @GetMapping("/pozo/{id_lp}")
    public ResponseEntity<?> getmantenimientoByIdPozo(@PathVariable int id_lp){
        return ResponseEntity.ok(mantenimientoService.getmantenimientoByIdPozo(id_lp));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<?> getmantenimientosPendientes(){
        return ResponseEntity.ok(mantenimientoService.listPendientes());
    }

    @GetMapping("/historial")
    public ResponseEntity<?> getHistorialmantenimiento(){
        return ResponseEntity.ok(mantenimientoService.listHistorial());
    }

    @GetMapping("/historial/pozo/{clave}")
    public ResponseEntity<?> getHistorialmantenimientoByClave(@PathVariable String clave){
        return ResponseEntity.ok(mantenimientoService.listHistorialPorClave(clave));
    }

    @PutMapping("/{id_mtto}/completar")
    public ResponseEntity<?> completarmantenimiento(@PathVariable int id_mtto, @RequestBody(required = false) CierreRequest cierre){
        try {
            return mantenimientoService.completarmantenimiento(id_mtto, cierre)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el mantenimiento " + id_mtto));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<Mantenimiento> addmantenimiento(@RequestBody Mantenimiento mantenimiento){
        System.out.println("En el post");
        System.out.println(mantenimiento.getMtto_operador());
        System.out.println(mantenimiento.getMtto_clave_pozo());
        System.out.println(mantenimiento.getMtto_fecha_captura());
        System.out.println(mantenimiento.getMtto_motor_tipo());
        System.out.println(mantenimiento.getMtto_motor_hp());
        System.out.println(mantenimiento.getMtto_motor_kw());
        System.out.println(mantenimiento.getMtto_motor_eficiencia());
        System.out.println(mantenimiento.getMtto_tablero_tipo());
        System.out.println(mantenimiento.getMtto_tablero_capacidad());
        System.out.println(mantenimiento.getMtto_transformador_tipo());
        System.out.println(mantenimiento.getMtto_transformador_capacidad());
        System.out.println(mantenimiento.getMtto_calibre_sumergible());
        System.out.println(mantenimiento.getMtto_longitud_sumergible());
        System.out.println(mantenimiento.getMtto_tuberia_diametro());
        System.out.println(mantenimiento.getMtto_tuberia_longitud());
        System.out.println(mantenimiento.getMtto_observaciones());
        System.out.println(mantenimiento.getMtto_panel_solar());
        System.out.println(mantenimiento.getMtto_banco_baterias());
        System.out.println(mantenimiento.getMtto_medidor_presion());
        System.out.println(mantenimiento.getMtto_medidor_flujo());
        System.out.println(mantenimiento.getMtto_medidor_NE());
        System.out.println(mantenimiento.getMtto_medidor_ND());
        System.out.println(mantenimiento.getMtto_sistema_tierras());
        System.out.println(mantenimiento.getMtto_cable_transformador());
        System.out.println(mantenimiento.getMtto_cable_bomba());
        System.out.println(mantenimiento.getMtto_longitud_arrancador());
        System.out.println(mantenimiento.getMtto_longitud_bomba());
        System.out.println(mantenimiento.getMtto_calibre_arrancador());
        System.out.println(mantenimiento.getMtto_calibre_bomba());
        System.out.println(mantenimiento.getMtto_poliducto());
        System.out.println(mantenimiento.getMtto_trans_seco());
        System.out.println(mantenimiento.getMtto_electrica_exterior());
        System.out.println(mantenimiento.getMtto_electrica_inferior());
        System.out.println(mantenimiento.getMtto_sanitaria());

        return new ResponseEntity<>(mantenimientoService.savemantenimiento(mantenimiento),HttpStatus.CREATED);
    }
    
    @PutMapping("/{id_mtto}")
    public ResponseEntity<Mantenimiento> updatemantenimiento(@RequestBody Mantenimiento mantenimiento, @PathVariable int id_mtto){
        return new ResponseEntity<Mantenimiento>(mantenimientoService.updatemantenimiento(id_mtto, mantenimiento),HttpStatus.OK);
    }
    
    @DeleteMapping("/{id_mtto}")
    public ResponseEntity<?> deletemantenimiento(@PathVariable int id_mtto){
        mantenimientoService.deletemantenimiento(id_mtto);
        return ResponseEntity.ok("Se elimino");
    }
}