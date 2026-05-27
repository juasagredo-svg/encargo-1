package com.galiana_project.cl.galiana_project.repository;

import org.springframework.stereotype.Repository;

import com.galiana_project.cl.galiana_project.model.Ciudad;
import com.galiana_project.cl.galiana_project.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    List<Comuna> findByCiudad(Ciudad ciudad);
}
