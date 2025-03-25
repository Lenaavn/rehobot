package com.reho.persistence.entities;

import java.time.LocalDate;
import java.time.LocalTime;

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
import jakarta.persistence.OneToOne;
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

    @Column(name = "id_vehiculo", nullable = false)
    private int idVehiculo;

    @Column(name = "id_servicio", nullable = false)
    private int idServicio;

    @Column(name = "id_pago", nullable = false)
    private int idPago;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate fecha;

    @Column(columnDefinition = "TIME", nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id", insertable = false, updatable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id", insertable = false, updatable = false)
    private Servicio servicio;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_pago", referencedColumnName = "id", insertable = false, updatable = false)
    private Pago pago;
}

