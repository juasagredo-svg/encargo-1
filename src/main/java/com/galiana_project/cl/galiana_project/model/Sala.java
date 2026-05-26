package com.galiana_project.cl.galiana_project.model;

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
@Table(name = "sala")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 3)
    private Integer numSala;

    @Column(nullable = false, length = 5)
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "salaTeatro", nullable = false)
    private SalaTeatro salaTeatro;
}
