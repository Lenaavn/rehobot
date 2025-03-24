package com.reho.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reho.persistence.entities.enums.Nombre;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
public class Servicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "nombre", length = 50, nullable = false)
	private Nombre nombre;

	@Column(length = 100)
	private String descripcion;

	@Column(columnDefinition = "DECIMAL(5,2)")
	private Double precio;

	@OneToMany(mappedBy = "vehiculo", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Cita> citas;
}
