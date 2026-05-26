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
@Table(name = "teatro")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Teatro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String direccion;

    @Column(nullable = false, length = 20)
    private String contacto;

    @ManyToOne
    @JoinColumn(name = "comuna", nullable = false)
    private Comuna comuna;
}
