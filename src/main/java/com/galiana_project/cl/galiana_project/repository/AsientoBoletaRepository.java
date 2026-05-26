package com.galiana_project.cl.galiana_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Asiento;
import com.galiana_project.cl.galiana_project.model.AsientoBoleta;
import com.galiana_project.cl.galiana_project.model.Boleta;

@Repository
public interface AsientoBoletaRepository extends JpaRepository<AsientoBoleta, Long> {
    void deleteByBoleta(Boleta boleta);

    void deleteByAsiento(Asiento asiento);

    List<AsientoBoleta> findByAsiento(Asiento asiento);
}
