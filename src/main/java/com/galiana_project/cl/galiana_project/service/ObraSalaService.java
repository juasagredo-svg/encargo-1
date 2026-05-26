package com.galiana_project.cl.galiana_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.model.ObraSala;
import com.galiana_project.cl.galiana_project.repository.ObraRepository;
import com.galiana_project.cl.galiana_project.repository.ObraSalaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ObraSalaService {

    @Autowired
    private ObraSalaRepository obraSalaRepository;

    @Autowired
    private ObraRepository obraRepository;

    public List<ObraSala> findAll() {
        return obraSalaRepository.findAll();
    }

    public ObraSala findById(Long id) {
        return obraSalaRepository.findById(id).get();
    }

    public ObraSala save(ObraSala obraSala) {
        return obraSalaRepository.save(obraSala);
    }

    public void deleteById(Long id) {
        ObraSala obraSala = obraSalaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ObraSala no encontrada"));
        Obra obra = obraSala.getObra();
        List<ObraSala> relaciones = obraSalaRepository.findByObra(obra);
        for (ObraSala obraSalas : relaciones) {
            obraSalaRepository.deleteById(Long.valueOf(obraSalas.getId()));
        }
        obraRepository.deleteById(Long.valueOf(obra.getId()));
    }

    public ObraSala updateObraSala(Long id, ObraSala obraSala) {
        ObraSala obraSalaToUpdate = obraSalaRepository.findById(id).get();
        if (obraSalaToUpdate != null) {
            obraSalaToUpdate.setId(obraSala.getId());
            obraSalaToUpdate.setObra(obraSala.getObra());
            obraSalaToUpdate.setSala(obraSala.getSala());
            return obraSalaRepository.save(obraSalaToUpdate);
        } else {
            return null;
        }
    }

    public ObraSala patchObraSala(Long id, ObraSala obraSalaParcial) {
        ObraSala obraSalaToUpdate = obraSalaRepository.findById(id).get();

        if (obraSalaToUpdate != null) {
            if (obraSalaParcial.getId() != null) {
                obraSalaToUpdate.setId(obraSalaParcial.getId());
            }
            if (obraSalaParcial.getObra() != null) {
                obraSalaToUpdate.setObra(obraSalaParcial.getObra());
            }
            if (obraSalaParcial.getSala() != null) {
                obraSalaToUpdate.setSala(obraSalaParcial.getSala());
            }
            return obraSalaRepository.save(obraSalaToUpdate);
        } else {
            return null;
        }
    }

}
