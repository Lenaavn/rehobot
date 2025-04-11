package com.reho.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reho.persistence.entities.Cita;
import com.reho.persistence.repository.CitaRepository;
import com.reho.service.CitaService;
import com.reho.service.ServicioService;
import com.reho.service.VehiculoService;
import com.reho.service.dto.CitaDTO;
import com.reho.service.mapper.CitaMapper;

@RestController
@RequestMapping("/citas")
public class CitaController {

	@Autowired
	private CitaService citaService;

	@Autowired
	private CitaMapper citaMapper;

	@Autowired
	private VehiculoService vehiculoService;

	@Autowired
	private ServicioService servicioService;

	@Autowired
	private CitaRepository citaRepository;

	@GetMapping
	public ResponseEntity<List<CitaDTO>> list() {
		List<CitaDTO> citaDTOs = this.citaService.findAll().stream().map(citaMapper::toDTO)
				.collect(Collectors.toList());

		return ResponseEntity.ok(citaDTOs);
	}

	@GetMapping("/{idCita}")
	public ResponseEntity<CitaDTO> findById(@PathVariable int idCita) {
		if (this.citaService.existsCita(idCita)) {
			return ResponseEntity.ok(citaMapper.toDTO(this.citaService.findById(idCita).get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	// ResponseEntity<?> para permitir diferentes tipos de respuesta
	public ResponseEntity<?> create(@RequestBody CitaDTO citaDTO) {
		if (!vehiculoService.existsVehiculo(citaDTO.getIdVehiculo())) {
			return ResponseEntity.badRequest().body("El vehículo especificado no existe.");
		}

		if (!servicioService.existServicio(citaDTO.getIdServicio())) {
			return ResponseEntity.badRequest().body("El servicio especificado no existe.");
		}

		Cita cita = citaMapper.toEntity(citaDTO);

		// Usa el servicio para crear la cita con el pago asociado
		Cita createdCita = citaService.create(cita);

		// Convierte la entidad creada de vuelta a DTO
		CitaDTO responseDTO = citaMapper.toDTO(createdCita);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/{idCita}")
	// ResponseEntity<?> para permitir diferentes tipos de respuesta
	public ResponseEntity<?> update(@PathVariable int idCita, @RequestBody CitaDTO citaDTO) {

		if (idCita != citaDTO.getId()) {
			return ResponseEntity.badRequest().body("El ID de la URL no coincide con el ID del cuerpo del cita.");
		}

		if (!citaService.existsCita(idCita)) {
			return ResponseEntity.notFound().build();
		}

		Cita existingCita = citaRepository.findById(idCita).get();

		if (citaDTO.getIdPago() == 0) {
			citaDTO.setIdPago(existingCita.getIdPago());
		} else if (!existingCita.getIdPago().equals(citaDTO.getIdPago())) {
			return ResponseEntity.badRequest().body("El campo 'idPago' no se puede actualizar.");
		}

		if (!vehiculoService.existsVehiculo(citaDTO.getIdVehiculo())) {
			return ResponseEntity.badRequest().body("El vehículo especificado no existe.");
		}

		if (!servicioService.existServicio(citaDTO.getIdServicio())) {
			return ResponseEntity.badRequest().body("El servicio especificado no existe.");
		}

		if (citaDTO.getFecha() == null) {
			return ResponseEntity.badRequest().body("El campo 'Fecha' no puede ser nulo.");
		}

		if (citaDTO.getHora() == null) {
			return ResponseEntity.badRequest().body("El campo 'Hora' no puede ser nulo.");
		}

		if (citaDTO.getEstado() == null) {
			citaDTO.setEstado(existingCita.getEstado());
		}

		Cita cita = citaMapper.toEntity(citaDTO);

		cita.setIdPago(existingCita.getIdPago());

		Cita updatedCita = citaService.save(cita);

		return ResponseEntity.ok(citaMapper.toDTO(updatedCita));
	}

	@DeleteMapping("/{idCita}")
	public ResponseEntity<Cita> delete(@PathVariable int idCita) {
		if (this.citaService.delete(idCita)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}