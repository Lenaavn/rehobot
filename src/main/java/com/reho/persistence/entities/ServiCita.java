package com.reho.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicita")
@Getter
@Setter
@NoArgsConstructor
public class ServiCita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_servicio")
	private int idServicio;
	
	@Column(name = "id_cita")
	private int idCita;
	
	@ManyToOne
    @JoinColumn(name = "id_cita", referencedColumnName = "id", insertable = false, updatable = false)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id", insertable = false, updatable = false)
    private Servicio servicio;

}
