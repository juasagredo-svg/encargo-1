package com.galiana_project.cl.galiana_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galiana_project.cl.galiana_project.model.TipoUsuario;
import com.galiana_project.cl.galiana_project.repository.TipoUsuarioRepository;
import com.galiana_project.cl.galiana_project.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<TipoUsuario> findAll() {
        return tipoUsuarioRepository.findAll();
    }

    public TipoUsuario findById(Long id) {
        return tipoUsuarioRepository.findById(id).get();
    }

    public TipoUsuario save(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void deleteById(Long id) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado"));

        usuarioRepository.deleteByTipoUsuario(tipoUsuario);
        tipoUsuarioRepository.deleteById(id);
    }

    public TipoUsuario updateTipoUsuario(Long id, TipoUsuario tipoUsuario) {
        TipoUsuario tipoUsuarioToUpdate = tipoUsuarioRepository.findById(id).get();
        if (tipoUsuarioToUpdate != null) {
            tipoUsuarioToUpdate.setId(tipoUsuario.getId());
            tipoUsuarioToUpdate.setTipoDeUsuario(tipoUsuario.getTipoDeUsuario());
            return tipoUsuarioRepository.save(tipoUsuarioToUpdate);
        } else {
            return null;
        }
    }

    public TipoUsuario patchTipoUsuario(Long id, TipoUsuario tipoUsuarioParcial) {
        TipoUsuario tipoUsuarioToUpdate = tipoUsuarioRepository.findById(id).get();

        if (tipoUsuarioToUpdate != null) {
            if (tipoUsuarioParcial.getId() != null) {
                tipoUsuarioToUpdate.setId(tipoUsuarioParcial.getId());
            }
            if (tipoUsuarioParcial.getTipoDeUsuario() != null) {
                tipoUsuarioToUpdate.setTipoDeUsuario(tipoUsuarioParcial.getTipoDeUsuario());
            }
            return tipoUsuarioRepository.save(tipoUsuarioToUpdate);
        } else {
            return null;
        }
    }
}
