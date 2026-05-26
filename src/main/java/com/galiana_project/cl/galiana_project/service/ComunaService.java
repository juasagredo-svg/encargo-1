package com.galiana_project.cl.galiana_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.model.Teatro;
import com.galiana_project.cl.galiana_project.repository.ComunaRepository;
import com.galiana_project.cl.galiana_project.repository.TeatroRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private TeatroService teatroService;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private TeatroRepository teatroRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Comuna findById(Long id) {
        return comunaRepository.findById(id).get();
    }

    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public void deleteById(Long id) {
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
        List<Teatro> teatros = teatroRepository.findByComuna(comuna);

        for (Teatro teatro : teatros) {
            teatroService.deleteById(Long.valueOf(teatro.getId()));
        }
        comunaRepository.deleteById(id);
    }

    public Comuna updateComuna(Long id, Comuna comuna) {
        Comuna comunaToUpdate = comunaRepository.findById(id).get();
        if (comunaToUpdate != null) {
            comunaToUpdate.setNombre(comuna.getNombre());
            comunaToUpdate.setCiudad(comuna.getCiudad());
            return comunaRepository.save(comunaToUpdate);
        } else {
            return null;
        }
    }

    public Comuna patchComuna(Long id, Comuna comunaParcial) {
        Comuna comunaToUpdate = comunaRepository.findById(id).get();

        if (comunaParcial.getNombre() != null) {
            comunaToUpdate.setNombre(comunaParcial.getNombre());
        }
        if (comunaParcial.getCiudad() != null) {
            comunaToUpdate.setCiudad(comunaParcial.getCiudad());
        }

        return comunaRepository.save(comunaToUpdate);
    }

}
