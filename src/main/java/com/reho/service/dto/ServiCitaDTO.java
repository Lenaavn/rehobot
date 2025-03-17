package com.reho.service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiCitaDTO {
	
	private Integer id;
    private Integer idServicio;
    private Integer idCita;
    private String nombre; 
    private Double precio;
    private List<CitaDTO> citas;

}
