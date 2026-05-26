package com.galiana_project.cl.galiana_project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.model.SalaTeatro;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findBySalaTeatro(SalaTeatro salaTeatro);
}
