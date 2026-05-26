package com.galiana_project.cl.galiana_project.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.repository.ObraRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    public List<Obra> findAll() {
        return obraRepository.findAll();
    }

    public Obra findById(Long id) {
        return obraRepository.findById(id).get();
    }

    public Obra save(Obra obra) {
        return obraRepository.save(obra);
    }

    public void deleteById(Long id) {
        obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra no encontrada"));
        obraRepository.deleteById(id);
    }

    public Obra updateObra(Long id, Obra obra) {
        Obra obraToUpdate = obraRepository.findById(id).get();
        if (obraToUpdate != null) {
            obraToUpdate.setNombre(obra.getNombre());
            obraToUpdate.setHorario(obra.getHorario());
            obraToUpdate.setFechaInicio(obra.getFechaInicio());
            obraToUpdate.setFechaTermino(obra.getFechaTermino());
            obraToUpdate.setPrecio(obra.getPrecio());
            obraToUpdate.setDescripcion(obra.getDescripcion());
            obraToUpdate.setDirector(obra.getDirector());
            return obraRepository.save(obraToUpdate);
        } else {
            return null;
        }
    }

    public Obra patchObra(Long id, Obra obraParcial) {
        Optional<Obra> optionalObra = obraRepository.findById(id);
        if (optionalObra.isPresent()) {

            Obra obraToUpdate = optionalObra.get();

            if (obraParcial.getNombre() != null) {
                obraToUpdate.setNombre(obraParcial.getNombre());
            }
            if (obraParcial.getHorario() != null) {
                obraToUpdate.setHorario(obraParcial.getHorario());
            }
            if (obraParcial.getFechaInicio() != null) {
                obraToUpdate.setFechaInicio(obraParcial.getFechaInicio());
            }
            if (obraParcial.getFechaTermino() != null) {
                obraToUpdate.setFechaTermino(obraParcial.getFechaTermino());
            }
            if (obraParcial.getPrecio() != null) {
                obraToUpdate.setPrecio(obraParcial.getPrecio());
            }
            if (obraParcial.getDescripcion() != null) {
                obraToUpdate.setDescripcion(obraParcial.getDescripcion());
            }
            if (obraParcial.getDirector() != null) {
                obraToUpdate.setDirector(obraParcial.getDirector());
            }

            return obraRepository.save(obraToUpdate);

        } else {
            return null;

        }
    }

    public List<Obra> findObrasDelDirectorId(Long id) {
        return obraRepository.findObrasDelDirectorId(id);
    }

    public List<Obra> findObrasDeTeatroDirector(Long teatroId, Long directorId) {
        return obraRepository.findObrasDeTeatroDirector(teatroId, directorId);
    }

    public List<Obra> findObrasPorTeatroYComuna(Long teatroId, Long comunaId) {
        return obraRepository.findObrasPorTeatroYComuna(teatroId, comunaId);
    }
}
