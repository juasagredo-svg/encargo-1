package com.galiana_project.cl.galiana_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "obraSala")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ObraSala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sala", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "obra", nullable = false)
    private Obra obra;
}
