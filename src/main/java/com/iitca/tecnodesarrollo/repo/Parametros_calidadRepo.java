package com.iitca.tecnodesarrollo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iitca.tecnodesarrollo.dto.Parametros_calidad;

public interface Parametros_calidadRepo extends JpaRepository<Parametros_calidad, Integer> {

	List<Parametros_calidad> findByPozoId(Integer pozoId);

}