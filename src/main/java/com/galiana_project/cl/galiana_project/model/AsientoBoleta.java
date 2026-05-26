package com.galiana_project.cl.galiana_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "asientoBoleta")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AsientoBoleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "asiento", nullable = false)
    private Asiento asiento;

    @ManyToOne
    @JoinColumn(name = "boleta", nullable = false)
    private Boleta boleta;
}
