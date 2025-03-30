package com.reho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.Usuario;
import com.reho.persistence.entities.Vehiculo;
import com.reho.persistence.repository.UsuarioRepository;
import com.reho.persistence.repository.VehiculoRepository;

@Service
public class VehiculoService {

	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Vehiculo> findAll(){
		return this.vehiculoRepository.findAll();
	}
	
	public boolean existsVehiculo(int idVehiculo){
		return this.vehiculoRepository.existsById(idVehiculo);
	}
	
	public Optional<Vehiculo> findById(int idVehiculo){
		return this.vehiculoRepository.findById(idVehiculo);
	}
	
	public Vehiculo create(Vehiculo vehiculo) {
		Usuario usuario = usuarioRepository.findById(vehiculo.getIdUsuario())
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + vehiculo.getIdUsuario()));

	    vehiculo.setUsuario(usuario);

	    // Guardar el vehículo
	    return vehiculoRepository.save(vehiculo);
	}
	
	public Vehiculo save(Vehiculo vehiculo) {
		return this.vehiculoRepository.save(vehiculo);
	}
	
	public boolean delete(int idVehiculo) {
		boolean result = false;
		
		if(this.vehiculoRepository.existsById(idVehiculo)) {
			this.vehiculoRepository.deleteById(idVehiculo);
			result = true;
		}
		
		return result;
	}

}
