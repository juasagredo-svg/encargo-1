package com.galiana_project.cl.galiana_project.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    List<Director> findByNombresContainingAndFechaNacimientoBetween(String nombres, Date fechaInicio, Date fechaFin);

    List<Director> findByFechaNacimientoBetween(Date fechaInicio, Date fechaFin);
}
