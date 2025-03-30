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

@Service
public class CitaService {

	@Autowired
	private CitaRepository citaRepository;

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
		pago.setMonto(0.0); // Monto inicial
		pago.setMetodoPago(MetodoPago.SIN_DETERMINAR); // Método de pago predeterminado
		pago.setCita(cita); // Asociar el pago con la cita

		cita.setPago(pago); // Asociar la cita con el pago

		// Guardar ambos en la base de datos
		return citaRepository.save(cita); // CascadeType.ALL guardará el pago automáticamente
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
