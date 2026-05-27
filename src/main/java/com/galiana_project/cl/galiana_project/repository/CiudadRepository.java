package com.galiana_project.cl.galiana_project.repository;

import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Ciudad;
import com.galiana_project.cl.galiana_project.model.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findByRegion(Region region);
}
