package com.reho.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reho.persistence.entities.enums.Rol;

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
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Rol rol;

    @Column(length = 9, nullable = true)
    private String telefono;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Vehiculo> vehiculos;
}

