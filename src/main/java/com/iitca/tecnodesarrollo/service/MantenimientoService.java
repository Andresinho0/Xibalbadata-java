package com.iitca.tecnodesarrollo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iitca.tecnodesarrollo.dto.Mantenimiento;
import com.iitca.tecnodesarrollo.repo.MantenimientoRepo;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepo mantenimientoRepo;
    
    public List<Mantenimiento> listAll(){
        return mantenimientoRepo.findAll();
    }
    
    public Object getmantenimientoByid(int id_mtto) {
        return mantenimientoRepo.findById(id_mtto);
    }

    public Object getmantenimientoByIdPozo(int id_lp) {
        return mantenimientoRepo.findByIdLp(id_lp);
    }

    public Mantenimiento savemantenimiento(Mantenimiento mantenimiento) {
        return mantenimientoRepo.save(mantenimiento);
    }
    
    public Mantenimiento updatemantenimiento(int id_mtto, Mantenimiento mantenimientoToUpate) {
        Optional<Mantenimiento> mantenimientoFound = mantenimientoRepo.findById(id_mtto);
        if(mantenimientoFound.isPresent()) {
            Mantenimiento mantenimientoFoundToUpdate = mantenimientoFound.get();
            mantenimientoFoundToUpdate.setMtto_operador(mantenimientoToUpate.getMtto_operador());
            mantenimientoFoundToUpdate.setMtto_clave_pozo(mantenimientoToUpate.getMtto_clave_pozo());
            mantenimientoFoundToUpdate.setMtto_fecha_captura(mantenimientoToUpate.getMtto_fecha_captura());
            mantenimientoFoundToUpdate.setMtto_motor_tipo(mantenimientoToUpate.getMtto_motor_tipo());
            mantenimientoFoundToUpdate.setMtto_motor_hp(mantenimientoToUpate.getMtto_motor_hp());
            mantenimientoFoundToUpdate.setMtto_motor_kw(mantenimientoToUpate.getMtto_motor_kw());
            mantenimientoFoundToUpdate.setMtto_motor_eficiencia(mantenimientoToUpate.getMtto_motor_eficiencia());
            mantenimientoFoundToUpdate.setMtto_tablero_tipo(mantenimientoToUpate.getMtto_tablero_tipo());
            mantenimientoFoundToUpdate.setMtto_tablero_capacidad(mantenimientoToUpate.getMtto_tablero_capacidad());
            mantenimientoFoundToUpdate.setMtto_transformador_tipo(mantenimientoToUpate.getMtto_transformador_tipo());
            mantenimientoFoundToUpdate.setMtto_transformador_capacidad(mantenimientoToUpate.getMtto_transformador_capacidad());
            mantenimientoFoundToUpdate.setMtto_calibre_sumergible(mantenimientoToUpate.getMtto_calibre_sumergible());
            mantenimientoFoundToUpdate.setMtto_longitud_sumergible(mantenimientoToUpate.getMtto_longitud_sumergible());
            mantenimientoFoundToUpdate.setMtto_tuberia_diametro(mantenimientoToUpate.getMtto_tuberia_diametro());
            mantenimientoFoundToUpdate.setMtto_tuberia_longitud(mantenimientoToUpate.getMtto_tuberia_longitud());
            mantenimientoFoundToUpdate.setMtto_observaciones(mantenimientoToUpate.getMtto_observaciones());
            mantenimientoFoundToUpdate.setMtto_panel_solar(mantenimientoToUpate.getMtto_panel_solar());
            mantenimientoFoundToUpdate.setMtto_banco_baterias(mantenimientoToUpate.getMtto_banco_baterias());
            mantenimientoFoundToUpdate.setMtto_medidor_presion(mantenimientoToUpate.getMtto_medidor_presion());
            mantenimientoFoundToUpdate.setMtto_medidor_flujo(mantenimientoToUpate.getMtto_medidor_flujo());
            mantenimientoFoundToUpdate.setMtto_medidor_NE(mantenimientoToUpate.getMtto_medidor_NE());
            mantenimientoFoundToUpdate.setMtto_medidor_ND(mantenimientoToUpate.getMtto_medidor_ND());
            mantenimientoFoundToUpdate.setMtto_sistema_tierras(mantenimientoToUpate.getMtto_sistema_tierras());
            mantenimientoFoundToUpdate.setMtto_cable_transformador(mantenimientoToUpate.getMtto_cable_transformador());
            mantenimientoFoundToUpdate.setMtto_cable_bomba(mantenimientoToUpate.getMtto_cable_bomba());
            mantenimientoFoundToUpdate.setMtto_longitud_arrancador(mantenimientoToUpate.getMtto_longitud_arrancador());
            mantenimientoFoundToUpdate.setMtto_longitud_bomba(mantenimientoToUpate.getMtto_longitud_bomba());
            mantenimientoFoundToUpdate.setMtto_calibre_arrancador(mantenimientoToUpate.getMtto_calibre_arrancador());
            mantenimientoFoundToUpdate.setMtto_calibre_bomba(mantenimientoToUpate.getMtto_calibre_bomba());
            mantenimientoFoundToUpdate.setMtto_poliducto(mantenimientoToUpate.getMtto_poliducto());
            mantenimientoFoundToUpdate.setMtto_trans_seco(mantenimientoToUpate.getMtto_trans_seco());
            mantenimientoFoundToUpdate.setMtto_electrica_exterior(mantenimientoToUpate.getMtto_electrica_exterior());
            mantenimientoFoundToUpdate.setMtto_electrica_inferior(mantenimientoToUpate.getMtto_electrica_inferior());
            mantenimientoFoundToUpdate.setMtto_sanitaria(mantenimientoToUpate.getMtto_sanitaria());
            mantenimientoFoundToUpdate.setMtto_bomba_marca(mantenimientoToUpate.getMtto_bomba_marca());
            mantenimientoFoundToUpdate.setMtto_bomba_lps(mantenimientoToUpate.getMtto_bomba_lps());
            mantenimientoFoundToUpdate.setMtto_bomba_rpm(mantenimientoToUpate.getMtto_bomba_rpm());
            mantenimientoFoundToUpdate.setMtto_bomba_hp(mantenimientoToUpate.getMtto_bomba_hp());
            mantenimientoFoundToUpdate.setMtto_tablero_marca(mantenimientoToUpate.getMtto_tablero_marca());
            mantenimientoFoundToUpdate.setMtto_tablero_hp(mantenimientoToUpate.getMtto_tablero_hp());
            mantenimientoFoundToUpdate.setMtto_tablero_voltaje(mantenimientoToUpate.getMtto_tablero_voltaje());
            mantenimientoFoundToUpdate.setMtto_transformador_marca(mantenimientoToUpate.getMtto_transformador_marca());

            return mantenimientoRepo.save(mantenimientoFoundToUpdate);
        } else {
            return null;
        }
    }
    
    public void deletemantenimiento(int id_mtto) {
         mantenimientoRepo.deleteById(id_mtto);
    }
}