package com.iitca.tecnodesarrollo.repo;

import com.iitca.tecnodesarrollo.dto.Mantenimiento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MantenimientoRepo extends JpaRepository<Mantenimiento, Integer> {

    List<Mantenimiento> findByIdLp(int idLp);

    @Query("SELECT m FROM Mantenimiento m WHERE m.mtto_clave_pozo = :clave ORDER BY m.mtto_fecha_captura DESC")
    List<Mantenimiento> buscarPorClave(@Param("clave") String clave, Pageable pageable);

    @Query("SELECT m FROM Mantenimiento m WHERE m.estado = :estado ORDER BY m.mtto_fecha_captura DESC")
    List<Mantenimiento> buscarPorEstado(@Param("estado") String estado);

    @Query("SELECT m FROM Mantenimiento m WHERE m.estado = 'completada' ORDER BY m.completada_en DESC")
    List<Mantenimiento> buscarHistorial();

    @Query("SELECT m FROM Mantenimiento m WHERE m.estado = 'completada' AND m.mtto_clave_pozo = :clave ORDER BY m.completada_en DESC")
    List<Mantenimiento> buscarHistorialPorClave(@Param("clave") String clave);
}