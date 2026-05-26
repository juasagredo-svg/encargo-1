package com.galiana_project.cl.galiana_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galiana_project.cl.galiana_project.model.Asiento;
import com.galiana_project.cl.galiana_project.model.ObraSala;
import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.repository.AsientoRepository;
import com.galiana_project.cl.galiana_project.repository.ObraSalaRepository;
import com.galiana_project.cl.galiana_project.repository.SalaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private AsientoRepository asientoRepository;

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private ObraSalaRepository obraSalaRepository;

    @Autowired
    private ObraSalaService obraSalaService;

    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala findById(Long id) {
        return salaRepository.findById(id).get();
    }

    public Sala save(Sala sala) {
        return salaRepository.save(sala);
    }

    public void deleteById(Long id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        List<ObraSala> obrasSala = obraSalaRepository.findBySala(sala);
        List<Asiento> asientos = asientoRepository.findBySala(sala);
        for (Asiento asiento : asientos) {
            asientoService.deleteById(Long.valueOf(asiento.getId()));
        }
        for (ObraSala obraSala : obrasSala) {
            obraSalaService.deleteById(Long.valueOf(obraSala.getId()));
        }
        salaRepository.deleteById(id);
    }

    public Sala updateSala(Long id, Sala sala) {
        Sala salaToUpdate = salaRepository.findById(id).get();
        if (salaToUpdate != null) {
            salaToUpdate.setId(sala.getId());
            salaToUpdate.setNumSala(sala.getNumSala());
            salaToUpdate.setCapacidad(sala.getCapacidad());
            salaToUpdate.setSalaTeatro(sala.getSalaTeatro());
            return salaRepository.save(salaToUpdate);
        } else {
            return null;
        }
    }

    public Sala patchSala(Long id, Sala salaParcial) {
        Sala salaToUpdate = salaRepository.findById(id).get();

        if (salaToUpdate != null) {
            if (salaParcial.getId() != null) {
                salaToUpdate.setId(salaParcial.getId());
            }
            if (salaParcial.getNumSala() != null) {
                salaToUpdate.setNumSala(salaParcial.getNumSala());
            }
            if (salaParcial.getCapacidad() != null) {
                salaToUpdate.setCapacidad(salaParcial.getCapacidad());
            }
            if (salaParcial.getSalaTeatro() != null) {
                salaToUpdate.setSalaTeatro(salaParcial.getSalaTeatro());
            }
            return salaRepository.save(salaToUpdate);
        } else {
            return null;
        }
    }
}
