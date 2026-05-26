package com.galiana_project.cl.galiana_project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Director;
import com.galiana_project.cl.galiana_project.model.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {

        void deleteByDirector(Director director);

        @Query("""
                        SELECT obra.nombre, obra.horario, obra.precio FROM Obra obra
                        """)
        List<Obra> findObrasConNombreHorarioPrecio();

        @Query("""
                        SELECT obra FROM Obra obra
                        Where obra.precio <= 10000
                        """)
        List<Obra> findObrasConPrecioMenorIgualA10Mil();

        @Query("""
                        SELECT o FROM Obra o
                        WHERE o.director.id = :id
                        """)
        List<Obra> findObrasDelDirectorId(Long id);

        @Query("""
                            SELECT DISTINCT o FROM Obra o
                            JOIN ObraSala os ON os.obra = o
                            JOIN Sala s ON os.sala = s
                            JOIN SalaTeatro st ON s.salaTeatro = st
                            JOIN Teatro t ON st.teatro = t
                            JOIN o.director d
                            WHERE t.id = :teatroId AND d.id = :directorId
                        """)
        List<Obra> findObrasDeTeatroDirector(@Param("teatroId") Long teatroId, @Param("directorId") Long directorId);

        @Query("""
                            SELECT DISTINCT o FROM Obra o
                            JOIN ObraSala os ON os.obra = o
                            JOIN Sala s ON os.sala = s
                            JOIN SalaTeatro st ON s.salaTeatro = st
                            JOIN Teatro t ON st.teatro = t
                            JOIN Comuna c ON t.comuna = c
                            WHERE t.id = :teatroId AND c.id = :comunaId
                        """)
        List<Obra> findObrasPorTeatroYComuna(
                        @Param("teatroId") Long teatroId,
                        @Param("comunaId") Long comunaId);
}
