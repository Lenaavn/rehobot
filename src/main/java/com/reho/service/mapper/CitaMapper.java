package com.reho.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reho.persistence.entities.Cita;
import com.reho.persistence.entities.Pago;
import com.reho.persistence.entities.Servicio;
import com.reho.persistence.entities.Vehiculo;
import com.reho.persistence.repository.PagoRepository;
import com.reho.persistence.repository.ServicioRepository;
import com.reho.persistence.repository.VehiculoRepository;
import com.reho.service.dto.CitaDTO;

@Component
public class CitaMapper {
	
	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	private ServicioRepository servicioRepository;
	
	@Autowired
	private PagoRepository pagoRepository;
	
	public CitaDTO toDTO(Cita cita) {
	    CitaDTO dto = new CitaDTO();
	    dto.setId(cita.getId());
	    dto.setIdVehiculo(cita.getIdVehiculo());
	    dto.setVehiculo(cita.getVehiculo() != null ? cita.getVehiculo().getMatricula() : null);
	    dto.setNombreUsuario(cita.getVehiculo() != null && cita.getVehiculo().getUsuario() != null ? cita.getVehiculo().getUsuario().getNombre() : null);
	    dto.setIdServicio(cita.getIdServicio());
	    dto.setServicio(cita.getServicio() != null ? cita.getServicio().getNombre() : null);
	    dto.setIdPago(cita.getPago() != null ? cita.getPago().getId() : 0);
	    dto.setFecha(cita.getFecha());
	    dto.setHora(cita.getHora());
	    dto.setEstado(cita.getEstado());
	    
	    return dto;
	}

    
    public Cita toEntity(CitaDTO dto) {
    	Cita cita = new Cita();
    	
    	cita.setId(dto.getId());
        cita.setIdVehiculo(dto.getIdVehiculo());
        cita.setIdServicio(dto.getIdServicio());
        cita.setIdPago(dto.getIdPago());
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setEstado(cita.getEstado());
        
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getIdVehiculo()).get();
        cita.setVehiculo(vehiculo);

        Servicio servicio = servicioRepository.findById(dto.getIdServicio()).get();
        cita.setServicio(servicio);

     // Mantener la relación con el pago existente
        if (dto.getIdPago() != 0) {
            Pago pagoExistente = pagoRepository.findById(dto.getIdPago())
                    .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + dto.getIdPago()));
            cita.setPago(pagoExistente); // Mantener el pago actual
        }
    	
    	return cita;
    }

}
