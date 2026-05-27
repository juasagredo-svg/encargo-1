package com.galiana_project.cl.galiana_project.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galiana_project.cl.galiana_project.model.Director;
import com.galiana_project.cl.galiana_project.repository.DirectorRepository;
import com.galiana_project.cl.galiana_project.repository.ObraRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ObraRepository obraRepository;

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Director findById(Long id) {
        return directorRepository.findById(id).get();
    }

    public Director save(Director director) {
        return directorRepository.save(director);
    }

    public void deleteById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Director no encontrado"));

        obraRepository.deleteByDirector(director);
        directorRepository.deleteById(id);
    }

    public Director updateDirector(Long id, Director director) {
        Director directorToUpdate = directorRepository.findById(id).get();
        if (directorToUpdate != null) {
            directorToUpdate.setNombres(director.getNombres());
            directorToUpdate.setFechaNacimiento(director.getFechaNacimiento());
            return directorRepository.save(directorToUpdate);
        } else {
            return null;
        }
    }

    public Director patchDirector(Long id, Director directorParcial) {
        Director directorToUpdate = directorRepository.findById(id).get();

        if (directorParcial.getNombres() != null) {
            directorToUpdate.setNombres(directorParcial.getNombres());
        }
        if (directorParcial.getFechaNacimiento() != null) {
            directorToUpdate.setFechaNacimiento(directorParcial.getFechaNacimiento());
        }

        return directorRepository.save(directorToUpdate);
    }

    public List<Director> findByNombresContainingAndFechaNacimientoBetween(String nombres, Date fechaInicio,
            Date fechaFin) {
        return directorRepository.findByNombresContainingAndFechaNacimientoBetween(nombres, fechaInicio, fechaFin);
    }

    public List<Director> findByFechaNacimientoBetween(Date fechaInicio, Date fechaFin) {
        return directorRepository.findByFechaNacimientoBetween(fechaInicio, fechaFin);
    }

}
