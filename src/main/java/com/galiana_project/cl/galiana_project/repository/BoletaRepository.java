package com.galiana_project.cl.galiana_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.galiana_project.cl.galiana_project.model.Boleta;
import com.galiana_project.cl.galiana_project.model.Pago;
import com.galiana_project.cl.galiana_project.model.Usuario;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {

    void deleteByPago(Pago pago);

    void deleteByUsuario(Usuario usuario);

    List<Boleta> findByUsuario(Usuario usuarioId);

    List<Boleta> findByUsuario_IdAndPago_Id(Long usuarioId, Long pagoId);

    List<Boleta> findByUsuario_IdAndPrecioTotal(Long usuarioId, Integer precioTotal);
}
