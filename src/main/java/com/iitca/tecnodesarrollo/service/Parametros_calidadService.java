package com.iitca.tecnodesarrollo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iitca.tecnodesarrollo.dto.Parametros_calidad;
import com.iitca.tecnodesarrollo.repo.Parametros_calidadRepo;

@Service
public class Parametros_calidadService {

	@Autowired
	private Parametros_calidadRepo parametros_calidadRepo;

	public List<Parametros_calidad> listAll() {
		return parametros_calidadRepo.findAll();
	}

	public Optional<Parametros_calidad> getparametros_calidadByid(int id) {
		return parametros_calidadRepo.findById(id);
	}

	public List<Parametros_calidad> getparametros_calidadByPozo(int pozoId) {
		return parametros_calidadRepo.findByPozoId(pozoId);
	}

	public Parametros_calidad saveparametros_calidad(Parametros_calidad parametros_calidad) {
		return parametros_calidadRepo.save(parametros_calidad);
	}

	public Parametros_calidad updateparametros_calidad(int id, Parametros_calidad toUpdate) {
		Optional<Parametros_calidad> found = parametros_calidadRepo.findById(id);
		if (found.isPresent()) {
			Parametros_calidad p = found.get();
			p.setPozoId(toUpdate.getPozoId());
			p.setTemperaturaC(toUpdate.getTemperaturaC());
			p.setPh(toUpdate.getPh());
			p.setCeUsCm(toUpdate.getCeUsCm());
			p.setSdtPpm(toUpdate.getSdtPpm());
			p.setEhMv(toUpdate.getEhMv());
			p.setOdPorcentaje(toUpdate.getOdPorcentaje());
			p.setOdMgL(toUpdate.getOdMgL());
			p.setClorurosMgL(toUpdate.getClorurosMgL());
			p.setNitratosMgL(toUpdate.getNitratosMgL());
			p.setSulfatosMgL(toUpdate.getSulfatosMgL());
			p.setAlcalinidadMgCaco3L(toUpdate.getAlcalinidadMgCaco3L());
			p.setBicarbonatosMgL(toUpdate.getBicarbonatosMgL());
			p.setColiformesTotales(toUpdate.getColiformesTotales());
			p.seteColi(toUpdate.geteColi());
			return parametros_calidadRepo.save(p);
		} else {
			return null;
		}
	}

	public void deleteparametros_calidad(int id) {
		parametros_calidadRepo.deleteById(id);
	}
}