package com.reho.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "vehiculo")
@Getter
@Setter
@NoArgsConstructor
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "id_usuario")
	private int idUsuario;

	@Column(length = 50, nullable = false)
	private String marca;

	@Column(length = 50, nullable = false)
	private String modelo;

	@Column(length = 7, nullable = false, unique = true)
	private String matricula;

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
	private Usuario usuario;

	@OneToMany(mappedBy = "vehiculo", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Cita> citas;

}
