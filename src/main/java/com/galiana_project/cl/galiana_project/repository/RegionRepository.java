package com.galiana_project.cl.galiana_project.repository;

import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

}
