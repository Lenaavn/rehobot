package com.reho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.Cita;
import com.reho.persistence.entities.Pago;
import com.reho.persistence.entities.enums.Estado;
import com.reho.persistence.entities.enums.MetodoPago;
import com.reho.persistence.repository.CitaRepository;
import com.reho.persistence.repository.PagoRepository;

@Service
public class CitaService {

	@Autowired
	private CitaRepository citaRepository;
	
	@Autowired
	private PagoRepository pagoRepository;

	public List<Cita> findAll() {
		return this.citaRepository.findAll();
	}

	public boolean existsCita(int idCita) {
		return this.citaRepository.existsById(idCita);
	}

	public Optional<Cita> findById(int idCita) {
		return this.citaRepository.findById(idCita);
	}

	public Cita create(Cita cita) {
	    // Configurar el estado inicial de la cita
	    if (cita.getEstado() == null) {
	        cita.setEstado(Estado.NO_PAGADA);
	    }

	    // Crear el pago asociado automáticamente
	    Pago pago = new Pago();
	    pago.setMonto(0.0); // Definir valores predeterminados
	    pago.setMetodoPago(MetodoPago.SIN_DETERMINAR); // Definir método de pago predeterminado
	    pago = pagoRepository.save(pago); // Persistir el pago en la base de datos

	    // Asociar el pago a la cita
	    cita.setPago(pago);

	    // Guardar la cita con el pago asignado
	    return citaRepository.save(cita);
	}


	public Cita save(Cita cita) {
		// Si el estado es nulo, asignar un valor predeterminado
		if (cita.getEstado() == null) {
			cita.setEstado(Estado.NO_PAGADA); // Valor predeterminado
		}

		// Verificar que el pago esté asociado
		if (cita.getPago() == null) {
			throw new IllegalArgumentException("La cita debe tener un pago asociado");
		}

		return citaRepository.save(cita);
	}

	public boolean delete(int idCita) {
		boolean result = false;

		if (this.citaRepository.existsById(idCita)) {
			this.citaRepository.deleteById(idCita);
			result = true;
		}

		return result;
	}

}
