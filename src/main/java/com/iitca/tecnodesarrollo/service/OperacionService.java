package com.iitca.tecnodesarrollo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iitca.tecnodesarrollo.dto.CierreRequest;
import com.iitca.tecnodesarrollo.dto.EstadoRegistro;
import com.iitca.tecnodesarrollo.dto.Operacion;
import com.iitca.tecnodesarrollo.repo.OperacionRepo;

@Service
public class OperacionService {

	@Autowired
	private OperacionRepo operacionRepo;
	
	public List<Operacion> listAll(){
		return operacionRepo.findAll();
	}
	
	public Object getoperacionByid(int id_op) {
            return operacionRepo.findById(id_op);
	}
	
	public Object getoperacionByIdPozo(int id_lp) {
            return operacionRepo.findByIdLp(id_lp);
	}

	public Operacion saveoperacion(Operacion operacion) {
		// Todo registro nuevo nace pendiente; solo se cierra con completaroperacion()
		operacion.setEstado(EstadoRegistro.PENDIENTE);
		operacion.setCompletada_en(null);
		operacion.setCompletada_por(null);
		operacion.setNotas_cierre(null);
		return operacionRepo.save(operacion);
	}

	public List<Operacion> listPendientes() {
		return operacionRepo.buscarPorEstado(EstadoRegistro.PENDIENTE);
	}

	public List<Operacion> listHistorial() {
		return operacionRepo.buscarHistorial();
	}

	public List<Operacion> listHistorialPorPozo(int id_lp) {
		return operacionRepo.buscarHistorialPorPozo(id_lp);
	}

	/**
	 * Marca la operación como completada.
	 * Devuelve Optional.empty() si no existe; lanza IllegalStateException si ya estaba completada.
	 */
	public Optional<Operacion> completaroperacion(int id_op, CierreRequest cierre) {
		Optional<Operacion> operacionFound = operacionRepo.findById(id_op);
		if (operacionFound.isEmpty()) {
			return Optional.empty();
		}
		Operacion operacion = operacionFound.get();
		if (EstadoRegistro.COMPLETADA.equals(operacion.getEstado())) {
			throw new IllegalStateException("La operación " + id_op + " ya está completada");
		}
		operacion.setEstado(EstadoRegistro.COMPLETADA);
		operacion.setCompletada_en(LocalDateTime.now());
		operacion.setCompletada_por(cierre != null ? cierre.getCompletada_por() : null);
		operacion.setNotas_cierre(cierre != null ? cierre.getNotas_cierre() : null);
		return Optional.of(operacionRepo.save(operacion));
	}
	
	public Operacion updateoperacion(int id_op, Operacion operacionToUpate) {
		Optional<Operacion> operacionFound = operacionRepo.findById(id_op);
		if(operacionFound.isPresent()) {
			Operacion operacionFoundToUpdate = operacionFound.get();
			operacionFoundToUpdate.setOp_cpozo(operacionToUpate.getOp_cpozo(	));
            operacionFoundToUpdate.setOp_fecha_captura(operacionToUpate.getOp_fecha_captura(	));
			operacionFoundToUpdate.setOp_operador(operacionToUpate.getOp_operador(	));
            operacionFoundToUpdate.setOp_nestatico(operacionToUpate.getOp_nestatico(	));
            operacionFoundToUpdate.setOp_ndinamico(operacionToUpate.getOp_ndinamico(	));
            operacionFoundToUpdate.setOp_gasto(operacionToUpate.getOp_gasto(	));
            operacionFoundToUpdate.setOp_presion(operacionToUpate.getOp_presion(	));
            operacionFoundToUpdate.setOp_tiempo_op(operacionToUpate.getOp_tiempo_op(	));
            operacionFoundToUpdate.setOp_observaciones(operacionToUpate.getOp_observaciones(	));
            operacionFoundToUpdate.setOp_operando(operacionToUpate.getOp_operando(	));
			// Los campos de cierre (estado, completada_*, notas_cierre) no se tocan aquí
			return operacionRepo.save(operacionFoundToUpdate);
		}else {
			return null;
		}
	}
	
	public void deleteoperacion(int id_op) {
        operacionRepo.deleteById(id_op);
	}
}