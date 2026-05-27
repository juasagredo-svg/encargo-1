package com.galiana_project.cl.galiana_project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.model.ObraSala;
import com.galiana_project.cl.galiana_project.model.Sala;

@Repository
public interface ObraSalaRepository extends JpaRepository<ObraSala, Long> {
    List<ObraSala> findBySala(Sala sala);

    List<ObraSala> findByObra(Obra obra);
}
