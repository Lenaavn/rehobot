package com.reho.web;

import java.util.List;

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
import com.reho.service.CitaService;

@RestController
@RequestMapping("/citas")
public class CitaController {

	
	@Autowired
	private CitaService citaService;
	
	@GetMapping
	public ResponseEntity<List<Cita>> list(){
		return ResponseEntity.ok(this.citaService.findAll());
	}
	
	@GetMapping("/{idCita}")
	public ResponseEntity<Cita> findById(@PathVariable int idCita) {
		if(this.citaService.existsCita(idCita)) {
			return ResponseEntity.ok(this.citaService.findById(idCita).get());
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<Cita> create(@RequestBody Cita cita){
		return ResponseEntity.ok(this.citaService.create(cita));
	}
	
	@PutMapping("/{idCita}")
	public ResponseEntity<Cita> update(@PathVariable int idCita, @RequestBody Cita cita){
		if(this.citaService.existsCita(idCita)) {
			return ResponseEntity.ok(this.citaService.save(cita));
		}
		
		if(idCita != cita.getId()) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{idCita}")
	public ResponseEntity<Cita> delete(@PathVariable int idCita){
		if(this.citaService.delete(idCita)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}	
	

	
}
