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

import com.reho.persistence.entities.Vehiculo;
import com.reho.service.VehiculoService;


@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@GetMapping
	public ResponseEntity<List<Vehiculo>> list(){
		return ResponseEntity.ok(this.vehiculoService.findAll());
	}
	
	@GetMapping("/{idVehiculo}")
	public ResponseEntity<Vehiculo> findById(@PathVariable int idVehiculo) {
		if(this.vehiculoService.existsVehiculo(idVehiculo)) {
			return ResponseEntity.ok(this.vehiculoService.findById(idVehiculo).get());
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<Vehiculo> create(@RequestBody Vehiculo vehiculo){
		return ResponseEntity.ok(this.vehiculoService.create(vehiculo));
	}
	
	@PutMapping("/{idVehiculo}")
	public ResponseEntity<Vehiculo> update(@PathVariable int idVehiculo, @RequestBody Vehiculo vehiculo){
		if(this.vehiculoService.existsVehiculo(idVehiculo)) {
			return ResponseEntity.ok(this.vehiculoService.save(vehiculo));
		}
		
		if(idVehiculo != vehiculo.getId()) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{idVehiculo}")
	public ResponseEntity<Vehiculo> delete(@PathVariable int idVehiculo){
		if(this.vehiculoService.delete(idVehiculo)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}	
}
