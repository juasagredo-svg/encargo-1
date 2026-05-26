package com.galiana_project.cl.galiana_project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.model.Teatro;

@Repository
public interface TeatroRepository extends JpaRepository<Teatro, Long> {
    List<Teatro> findByComuna(Comuna comuna);

    @Query("""
            SELECT t FROM Teatro t
            WHERE t.comuna.id = :id
            """)
    List<Teatro> findTeatrosFromComuna(Long id);
}
