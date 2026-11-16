package com.iitca.tecnodesarrollo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iitca.tecnodesarrollo.dto.Operacion;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;



public interface OperacionRepo extends JpaRepository<Operacion, Integer> {

    List<Operacion> findByIdLp(int idLp);

    @Query("SELECT o FROM Operacion o WHERE o.idLp = :idLp ORDER BY o.op_fecha_captura DESC")
    List<Operacion> buscarPorPozo(@Param("idLp") int idLp, Pageable pageable);

    @Query("SELECT o FROM Operacion o WHERE o.estado = :estado ORDER BY o.op_fecha_captura DESC")
    List<Operacion> buscarPorEstado(@Param("estado") String estado);

    @Query("SELECT o FROM Operacion o WHERE o.estado = 'completada' ORDER BY o.completada_en DESC")
    List<Operacion> buscarHistorial();

    @Query("SELECT o FROM Operacion o WHERE o.estado = 'completada' AND o.idLp = :idLp ORDER BY o.completada_en DESC")
    List<Operacion> buscarHistorialPorPozo(@Param("idLp") int idLp);
}