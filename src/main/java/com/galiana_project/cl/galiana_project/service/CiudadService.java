package com.galiana_project.cl.galiana_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galiana_project.cl.galiana_project.model.Ciudad;
import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.repository.CiudadRepository;
import com.galiana_project.cl.galiana_project.repository.ComunaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CiudadService {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Ciudad> findAll() {
        return ciudadRepository.findAll();
    }

    public Ciudad findById(Long id) {
        return ciudadRepository.findById(id).get();
    }

    public Ciudad save(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    public void deleteById(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        List<Comuna> comunas = comunaRepository.findByCiudad(ciudad);

        for (Comuna comuna : comunas) {
            comunaService.deleteById(Long.valueOf(comuna.getId()));
        }
        ciudadRepository.deleteById(id);
    }

    public Ciudad updateCiudad(Long id, Ciudad ciudad) {
        Ciudad ciudadToUpdate = ciudadRepository.findById(id).get();
        if (ciudadToUpdate != null) {
            ciudadToUpdate.setNombre(ciudad.getNombre());
            ciudadToUpdate.setRegion(ciudad.getRegion());
            return ciudadRepository.save(ciudadToUpdate);
        } else {
            return null;
        }
    }

    public Ciudad patchCiudad(Long id, Ciudad ciudadParcial) {
        Ciudad ciudadToUpdate = ciudadRepository.findById(id).get();

        if (ciudadParcial.getNombre() != null) {
            ciudadToUpdate.setNombre(ciudadParcial.getNombre());
        }
        if (ciudadParcial.getRegion() != null) {
            ciudadToUpdate.setRegion(ciudadParcial.getRegion());
        }

        return ciudadRepository.save(ciudadToUpdate);
    }
}
