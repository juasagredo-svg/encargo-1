package com.galiana_project.cl.galiana_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.model.Teatro;
import com.galiana_project.cl.galiana_project.repository.SalaTeatroRepository;
import com.galiana_project.cl.galiana_project.repository.TeatroRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TeatroService {

    @Autowired
    private TeatroRepository teatroRepository;

    @Autowired
    private SalaTeatroService salaTeatroService;

    @Autowired
    private SalaTeatroRepository salaTeatroRepository;

    public List<Teatro> findAll() {
        return teatroRepository.findAll();
    }

    public Teatro findById(Long id) {
        return teatroRepository.findById(id).get();
    }

    public Teatro save(Teatro teatro) {
        return teatroRepository.save(teatro);
    }

    public void deleteById(Long id) {
        Teatro teatro = teatroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teatro no encontrado"));
        List<SalaTeatro> salaTeatros = salaTeatroRepository.findByTeatro(teatro);
        for (SalaTeatro salateatro : salaTeatros) {
            salaTeatroService.deleteById(Long.valueOf(salateatro.getId()));
        }
        teatroRepository.deleteById(id);
    }

    public Teatro updateTeatro(Long id, Teatro teatro) {
        Teatro teatroToUpdate = teatroRepository.findById(id).get();
        if (teatroToUpdate != null) {
            teatroToUpdate.setNombre(teatro.getNombre());
            teatroToUpdate.setDireccion(teatro.getDireccion());
            teatroToUpdate.setContacto(teatro.getContacto());
            teatroToUpdate.setComuna(teatro.getComuna());
            return teatroRepository.save(teatroToUpdate);
        } else {
            return null;
        }
    }

    public Teatro patchTeatro(Long id, Teatro teatroParcial) {
        Teatro teatroToUpdate = teatroRepository.findById(id).get();

        if (teatroParcial.getNombre() != null) {
            teatroToUpdate.setNombre(teatroParcial.getNombre());
        }
        if (teatroParcial.getDireccion() != null) {
            teatroToUpdate.setDireccion(teatroParcial.getDireccion());
        }
        if (teatroParcial.getContacto() != null) {
            teatroToUpdate.setContacto(teatroParcial.getContacto());
        }
        if (teatroParcial.getComuna() != null) {
            teatroToUpdate.setComuna(teatroParcial.getComuna());
        }

        return teatroRepository.save(teatroToUpdate);
    }

    public List<Teatro> findTeatrosFromComuna(Long id) {
        return teatroRepository.findTeatrosFromComuna(id);
    }
}
