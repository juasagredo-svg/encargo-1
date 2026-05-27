package com.galiana_project.cl.galiana_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galiana_project.cl.galiana_project.model.Pago;
import com.galiana_project.cl.galiana_project.repository.BoletaRepository;
import com.galiana_project.cl.galiana_project.repository.PagoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private BoletaRepository boletaRepository;

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public Pago findById(Long id) {
        return pagoRepository.findById(id).get();
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void deleteById(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        boletaRepository.deleteByPago(pago);
        pagoRepository.deleteById(id);
    }

    public Pago updatePago(Long id, Pago pago) {
        Pago pagoToUpdate = pagoRepository.findById(id).get();
        if (pagoToUpdate != null) {
            pagoToUpdate.setId(pago.getId());
            pagoToUpdate.setMetodoPago(pago.getMetodoPago());
            return pagoRepository.save(pagoToUpdate);
        } else {
            return null;
        }
    }

    public Pago patchPago(Long id, Pago pagoParcial) {
        Pago pagoToUpdate = pagoRepository.findById(id).get();

        if (pagoToUpdate != null) {
            if (pagoParcial.getId() != null) {
                pagoToUpdate.setId(pagoParcial.getId());
            }
            if (pagoParcial.getMetodoPago() != null) {
                pagoToUpdate.setMetodoPago(pagoParcial.getMetodoPago());
            }
            return pagoRepository.save(pagoToUpdate);
        } else {
            return null;
        }
    }

}
