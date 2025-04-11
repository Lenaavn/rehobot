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
		// Establecer estado predeterminado de la cita
		if (cita.getEstado() == null) {
			cita.setEstado(Estado.NO_PAGADA);
		}

		// Guardar la cita inicialmente para obtener su ID
		Cita citaGuardada = citaRepository.save(cita);

		// Crear el pago y asociarlo a la cita
		Pago pago = new Pago();
		pago.setMonto(0.0);
		pago.setMetodoPago(MetodoPago.SIN_DETERMINAR);

		// Relación bidireccional: asociar la cita al pago
		pago.setCita(citaGuardada);
		Pago pagoGuardado = pagoRepository.save(pago);

		// Actualizar la cita con el ID del pago
		citaGuardada.setPago(pagoGuardado);
		citaGuardada.setIdPago(pagoGuardado.getId()); // Actualizar idPago manualmente

		// Guardar nuevamente la cita para reflejar los cambios en la base de datos
		return citaRepository.save(citaGuardada);
	}

	public Cita save(Cita cita) {
		// Verificar que el pago esté asociado
		if (cita.getPago() == null) {
			throw new IllegalArgumentException("La cita debe tener un pago asociado.");
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
