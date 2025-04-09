package com.reho.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reho.persistence.entities.Servicio;
import com.reho.service.ServicioService;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

	@Autowired
	private ServicioService servicioService;

	@GetMapping
	public ResponseEntity<List<Servicio>> list() {
		return ResponseEntity.ok(this.servicioService.findAll());
	}

	@GetMapping("/{idServicio}")
	public ResponseEntity<Servicio> findById(@PathVariable int idServicio) {
		Optional<Servicio> servicio = this.servicioService.findById(idServicio);
		return servicio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	// ResponseEntity<?> para permitir diferentes tipos de respuesta
	public ResponseEntity<?> create(@RequestBody Servicio servicio) {
		if (servicio.getNombre() == null) {
			return ResponseEntity.badRequest().body("El campo 'nombre' no puede ser nulo o vacío.");
		}

		if (servicio.getDescripcion() == null || servicio.getDescripcion().isEmpty()) {
			return ResponseEntity.badRequest().body("El campo 'descripcion' no puede ser nulo o vacío.");
		}

		if (servicio.getPrecio() == null || servicio.getPrecio() <= 0) {
			return ResponseEntity.badRequest().body("El campo 'precio' no puede ser nulo y debe ser mayor a 0.");
		}

		Servicio createdServicio = this.servicioService.create(servicio);
		return ResponseEntity.ok(createdServicio);
	}

	@PutMapping("/{idServicio}")
	// ResponseEntity<?> para permitir diferentes tipos de respuesta
	public ResponseEntity<?> update(@PathVariable int idServicio, @RequestBody Servicio servicio) {
		if (idServicio != servicio.getId()) {
			return ResponseEntity.badRequest().body("El ID de la URL no coincide con el ID del cuerpo del servicio.");
		}

		if (servicio.getNombre() == null) {
			return ResponseEntity.badRequest().body("El campo 'nombre' no puede ser nulo.");
		}

		if (servicio.getPrecio() == null) {
			return ResponseEntity.badRequest().body("El campo 'precio' no puede ser nulo.");
		}

		if (!this.servicioService.existServicio(idServicio)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.servicioService.save(servicio));
	}

	@DeleteMapping("/{idServicio}")
	public ResponseEntity<Void> delete(@PathVariable int idServicio) {
		if (this.servicioService.delete(idServicio)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
