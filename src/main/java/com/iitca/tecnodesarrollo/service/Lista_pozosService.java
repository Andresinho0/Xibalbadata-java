package com.iitca.tecnodesarrollo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.iitca.tecnodesarrollo.dto.ErrorMsg;
import com.iitca.tecnodesarrollo.dto.Lista_pozos;
import com.iitca.tecnodesarrollo.repo.Lista_pozosRepo;

@Service
public class Lista_pozosService {

	@Autowired
	private Lista_pozosRepo lista_pozosRepo;
	
	public List<Lista_pozos> listAll(){
		return lista_pozosRepo.findAll();
	}
	
	public Object getlista_pozosByid(int id_lp) {
            return lista_pozosRepo.findById(id_lp);
	}
	
	public Lista_pozos savelista_pozos(Lista_pozos lista_pozos) {
		return lista_pozosRepo.save(lista_pozos);
	}
	
	public Lista_pozos updatelista_pozos(int id_lp, Lista_pozos lista_pozosToUpate) {
		Optional<Lista_pozos> lista_pozosFound = lista_pozosRepo.findById(id_lp);
		if(lista_pozosFound.isPresent()) {
			Lista_pozos lista_pozosFoundToUpdate = lista_pozosFound.get();
			lista_pozosFoundToUpdate.setL_poz_clave(lista_pozosToUpate.getL_poz_clave(	));
			lista_pozosFoundToUpdate.setLp_Nombre(lista_pozosToUpate.getLp_Nombre());
			//lista_pozosFoundToUpdate.setLp_Ubicación(lista_pozosToUpate.getLp_Ubicación());
			lista_pozosFoundToUpdate.setLp_Profundidad_perforación(lista_pozosToUpate.getLp_Profundidad_perforación());
			lista_pozosFoundToUpdate.setLp_Diámetro_perforación(lista_pozosToUpate.getLp_Diámetro_perforación());
			lista_pozosFoundToUpdate.setLp_Diámetro_ADEME(lista_pozosToUpate.getLp_Diámetro_ADEME());
			lista_pozosFoundToUpdate.setLp_subestacion(lista_pozosToUpate.getLp_subestacion());
			lista_pozosFoundToUpdate.setLp_circuito_electrico(lista_pozosToUpate.getLp_circuito_electrico());
			lista_pozosFoundToUpdate.setLp_aporta_acueducto(lista_pozosToUpate.getLp_aporta_acueducto());
			lista_pozosFoundToUpdate.setLp_aporta_comunidad(lista_pozosToUpate.getLp_aporta_comunidad());
			lista_pozosFoundToUpdate.setLp_aporta_riego(lista_pozosToUpate.getLp_aporta_riego());
			lista_pozosFoundToUpdate.setLp_pa_cdmx(lista_pozosToUpate.getLp_pa_cdmx());
			lista_pozosFoundToUpdate.setLp_telemetria(lista_pozosToUpate.getLp_telemetria());
			lista_pozosFoundToUpdate.setLp_utr(lista_pozosToUpate.getLp_utr());
			lista_pozosFoundToUpdate.setLp_altura(lista_pozosToUpate.getLp_altura());
			return lista_pozosRepo.save(lista_pozosFoundToUpdate);
		}else {
			return null;
		}
	}
	
	public void deletelista_pozos(int id_lp) {
		 lista_pozosRepo.deleteById(id_lp);
	}
}