package com.reho.persistence.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reho.persistence.entities.enums.Estado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
public class Cita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "id_vehiculo")
	private int idVehiculo;

	@Column(name = "id_servicio")
	private int idServicio;

	@Column(columnDefinition = "DATE")
	private LocalDate fecha;

	@Column(columnDefinition = "TIME")
	private LocalTime hora;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Estado estado;

	@ManyToOne
	@JoinColumn(name = "id_vehiculo", referencedColumnName = "id", insertable = false, updatable = false)
	private Vehiculo vehiculo;

	@ManyToOne
	@JoinColumn(name = "id_seervicio", referencedColumnName = "id", insertable = false, updatable = false)
	private Servicio servicio;

	@OneToMany(mappedBy = "cita", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Pago> pagos;
}
