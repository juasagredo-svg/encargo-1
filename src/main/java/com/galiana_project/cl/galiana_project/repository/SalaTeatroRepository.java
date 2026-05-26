package com.galiana_project.cl.galiana_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.model.Teatro;

@Repository
public interface SalaTeatroRepository extends JpaRepository<SalaTeatro, Long> {
    List<SalaTeatro> findByTeatro(Teatro teatro);

    void deleteByTeatro(Teatro teatro);
}
