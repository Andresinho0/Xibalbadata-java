package com.iitca.tecnodesarrollo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iitca.tecnodesarrollo.dto.Alertas_pozos;

public interface Alertas_pozosRepo extends JpaRepository<Alertas_pozos, Integer> {
        public List<Alertas_pozos> findByIdLp(int idLp);

        @Query("SELECT a FROM Alertas_pozos a WHERE a.estado = :estado ORDER BY a.al_fechacap DESC")
        List<Alertas_pozos> buscarPorEstado(@Param("estado") String estado);

        @Query("SELECT a FROM Alertas_pozos a WHERE a.estado = 'completada' ORDER BY a.completada_en DESC")
        List<Alertas_pozos> buscarHistorial();

        @Query("SELECT a FROM Alertas_pozos a WHERE a.estado = 'completada' AND a.idLp = :idLp ORDER BY a.completada_en DESC")
        List<Alertas_pozos> buscarHistorialPorPozo(@Param("idLp") int idLp);

}
