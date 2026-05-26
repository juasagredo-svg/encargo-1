package com.galiana_project.cl.galiana_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.repository.SalaRepository;
import com.galiana_project.cl.galiana_project.repository.SalaTeatroRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalaTeatroService {

    @Autowired
    private SalaTeatroRepository salaTeatroRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaService salaService;

    public List<SalaTeatro> findAll() {
        return salaTeatroRepository.findAll();
    }

    public SalaTeatro findById(Long id) {
        return salaTeatroRepository.findById(id).get();
    }

    public SalaTeatro save(SalaTeatro salaTeatro) {
        return salaTeatroRepository.save(salaTeatro);
    }

    public void deleteById(Long id) {
        SalaTeatro salaTeatro = salaTeatroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teatro no encontrado"));
        List<Sala> salas = salaRepository.findBySalaTeatro(salaTeatro);
        for (Sala sala : salas) {
            salaService.deleteById(Long.valueOf(sala.getId()));
        }
        salaTeatroRepository.deleteById(id);
    }

    public SalaTeatro updatesalaTeatro(Long id, SalaTeatro salaTeatro) {
        SalaTeatro salaTeatroToUpdate = salaTeatroRepository.findById(id).get();
        if (salaTeatroToUpdate != null) {
            salaTeatroToUpdate.setId(salaTeatro.getId());
            salaTeatroToUpdate.setTeatro(salaTeatro.getTeatro());
            return salaTeatroRepository.save(salaTeatroToUpdate);
        } else {
            return null;
        }
    }

    public SalaTeatro patchsalaTeatro(Long id, SalaTeatro obraParcial) {
        SalaTeatro obraToUpdate = salaTeatroRepository.findById(id).get();

        if (obraToUpdate != null) {
            if (obraParcial.getId() != null) {
                obraToUpdate.setId(obraParcial.getId());
            }
            if (obraParcial.getTeatro() != null) {
                obraToUpdate.setTeatro(obraParcial.getTeatro());
            }
            return salaTeatroRepository.save(obraToUpdate);
        } else {
            return null;
        }
    }

}
