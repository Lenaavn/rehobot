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

import com.reho.persistence.entities.Vehiculo;
import com.reho.service.VehiculoService;
import com.reho.service.dto.VehiculoDTO;
import com.reho.service.mapper.VehiculoMapper;


@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    
    @Autowired
    private VehiculoService vehiculoService;
    
    @Autowired
	private VehiculoMapper vehiculoMapper;
    
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> list() {
        List<VehiculoDTO> vehiculos = this.vehiculoService.findAll().stream().map(vehiculoMapper::toDTO).collect(Collectors.toList());;
       
        return ResponseEntity.ok(vehiculos);
    }
    
    @GetMapping("/{idVehiculo}")
    public ResponseEntity<VehiculoDTO> findById(@PathVariable int idVehiculo) {
        if (this.vehiculoService.existsVehiculo(idVehiculo)) {
        	return ResponseEntity.ok(vehiculoMapper.toDTO(this.vehiculoService.findById(idVehiculo).get()));
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<VehiculoDTO> create(@RequestBody VehiculoDTO vehiculoDTO) {
        // Llamar al servicio para crear el vehículo
        VehiculoDTO savedVehiculo = this.vehiculoService.create(vehiculoMapper.toEntity(vehiculoDTO));
        return ResponseEntity.ok(savedVehiculo); 
    }

    
    @PutMapping("/{idVehiculo}")
    public ResponseEntity<VehiculoDTO> update(@PathVariable int idVehiculo, @RequestBody Vehiculo vehiculo) {
        if (!vehiculoService.existsVehiculo(idVehiculo)) {
            return ResponseEntity.notFound().build();
        }

        if (idVehiculo != vehiculo.getId()) {
            return ResponseEntity.badRequest().build();
        }

        VehiculoDTO updatedVehiculo = vehiculoService.save(vehiculo);
        return ResponseEntity.ok(updatedVehiculo);
    }
    
    @DeleteMapping("/{idVehiculo}")
    public ResponseEntity<Vehiculo> delete(@PathVariable int idVehiculo) {
        if (this.vehiculoService.delete(idVehiculo)) {
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}

