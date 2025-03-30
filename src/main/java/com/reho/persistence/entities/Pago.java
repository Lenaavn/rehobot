package com.reho.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reho.persistence.entities.enums.MetodoPago;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pago")
@Getter
@Setter
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_cita", insertable = false, updatable = false)
    private int idCita;

    @Column(columnDefinition = "DECIMAL(5,2)")
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", length = 20, nullable = false)
    private MetodoPago metodoPago;

    @OneToOne
    @JoinColumn(name = "id_cita", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Cita cita;

}

