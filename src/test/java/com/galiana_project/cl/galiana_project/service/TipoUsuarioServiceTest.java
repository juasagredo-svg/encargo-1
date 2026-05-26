package com.galiana_project.cl.galiana_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galiana_project.cl.galiana_project.model.TipoUsuario;
import com.galiana_project.cl.galiana_project.repository.TipoUsuarioRepository;

@SpringBootTest
public class TipoUsuarioServiceTest {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @MockBean
    private TipoUsuarioRepository tipoUsuarioRepository;

    private TipoUsuario createTipoUsuario() {
        return new TipoUsuario(1, "Administrador");
    }

    @Test
    public void testFindAll() {
        when(tipoUsuarioService.findAll()).thenReturn(List.of(createTipoUsuario()));
        List<TipoUsuario> tipoUsuarios = tipoUsuarioService.findAll();
        assertNotNull(tipoUsuarios);
        assertEquals(1, tipoUsuarios.size());
    }

    @Test
    public void testFindById() {
        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createTipoUsuario()));
        TipoUsuario tipoUsuario = tipoUsuarioService.findById(1L);
        assertNotNull(tipoUsuario);
        assertEquals("Administrador", tipoUsuario.getTipoDeUsuario());
    }

    @Test
    public void testSave() {
        TipoUsuario tipoUsuario = createTipoUsuario();
        when(tipoUsuarioRepository.save(tipoUsuario)).thenReturn(tipoUsuario);
        TipoUsuario savedTipoUsuario = tipoUsuarioRepository.save(tipoUsuario);
        assertNotNull(savedTipoUsuario);
        assertEquals("Administrador", savedTipoUsuario.getTipoDeUsuario());
    }

    @Test
    public void testPatchTipoUsuario() {
        TipoUsuario existingTeatro = createTipoUsuario();
        TipoUsuario patchData = new TipoUsuario();
        patchData.setTipoDeUsuario("Administrador Actualizado");

        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTeatro));
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(existingTeatro);

        TipoUsuario patchedTipoUsuario = tipoUsuarioService.patchTipoUsuario(1L, patchData);
        assertNotNull(patchedTipoUsuario);
        assertEquals("Administrador Actualizado", patchedTipoUsuario.getTipoDeUsuario());
    }

    @Test
    public void testDeleteById() {
        TipoUsuario tipoUsuario = createTipoUsuario();
        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoUsuario));
        doNothing().when(tipoUsuarioRepository).deleteById(1L);
        tipoUsuarioService.deleteById(1L);
        verify(tipoUsuarioRepository, times(1)).deleteById(1L);
    }
}
