package com.galiana_project.cl.galiana_project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.TipoUsuario;
import com.galiana_project.cl.galiana_project.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    void deleteByTipoUsuario(TipoUsuario tipoUsuario);

    @Query("""
                SELECT DISTINCT u FROM Usuario u
                JOIN Boleta b ON b.usuario = u
                JOIN AsientoBoleta ab ON ab.boleta = b
                JOIN Asiento a ON ab.asiento = a
                JOIN Sala s ON a.sala = s
                JOIN SalaTeatro st ON s.salaTeatro = st
                JOIN Teatro t ON st.teatro = t
                JOIN ObraSala os ON os.sala = s
                JOIN Obra o ON os.obra = o
                WHERE o.id = :obraId AND t.id = :teatroId
            """)
    List<Usuario> findUsuariosByObraAndTeatro(@Param("obraId") Long obraId, @Param("teatroId") Long teatroId);

    @Query("""
                SELECT DISTINCT u FROM Usuario u
                JOIN Boleta b ON b.usuario = u
                JOIN Pago p ON b.pago = p
                JOIN AsientoBoleta ab ON ab.boleta = b
                JOIN Asiento a ON ab.asiento = a
                JOIN Sala s ON a.sala = s
                JOIN ObraSala os ON os.sala = s
                JOIN Obra o ON os.obra = o
                WHERE p.metodoPago = :metodoPago AND o.id = :obraId
            """)
    List<Usuario> findUsuariosByMetodoPagoAndObra(
            @Param("metodoPago") String metodoPago,
            @Param("obraId") Long obraId);
}
