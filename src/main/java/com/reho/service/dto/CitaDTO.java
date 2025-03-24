package com.reho.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.reho.persistence.entities.enums.Estado;
import com.reho.persistence.entities.enums.Nombre;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CitaDTO {

	private Integer id;
	private int idVehiculo;
	private String vehiculo;
	private int idServicio;
	private Nombre servicio;
	private String nombreUsuario;
	private int idPago;
	private LocalDate fecha;
	private LocalTime hora;
	private Estado estado;
}
