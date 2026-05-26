package com.galiana_project.cl.galiana_project.model;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "boleta")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date fechaBoleta;

    @Column(nullable = false, length = 6)
    private Integer precioTotal;

    @ManyToOne
    @JoinColumn(name = "pago", nullable = false)
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;
}
