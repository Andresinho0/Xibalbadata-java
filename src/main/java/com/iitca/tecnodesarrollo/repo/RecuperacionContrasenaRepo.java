package com.iitca.tecnodesarrollo.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iitca.tecnodesarrollo.dto.RecuperacionContrasena;

@Repository
public interface RecuperacionContrasenaRepo extends JpaRepository<RecuperacionContrasena, Long> {

    Optional<RecuperacionContrasena> findTopByUsuarioIdAndUsadoFalseOrderByCreadoEnDesc(Integer usuarioId);

    Optional<RecuperacionContrasena> findByTokenHashAndUsadoFalse(String tokenHash);

    long countByUsuarioIdAndCreadoEnAfter(Integer usuarioId, LocalDateTime desde);

    @Modifying
    @Query("UPDATE RecuperacionContrasena r SET r.usado = true WHERE r.usuarioId = :usuarioId AND r.usado = false")
    int invalidarActivas(@Param("usuarioId") Integer usuarioId);
}
