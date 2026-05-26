package com.galiana_project.cl.galiana_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galiana_project.cl.galiana_project.model.TipoUsuario;
import com.galiana_project.cl.galiana_project.model.Usuario;
import com.galiana_project.cl.galiana_project.repository.UsuarioRepository;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario() {
        return new Usuario(
                1,
                "12345678-9",
                "Juan Pérez López",
                "juan.perez@mail.com",
                "SecurePass123",
                new Date(),
                new TipoUsuario());
    }

    @Test
    public void testFindAll() {
        when(usuarioService.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioService.findById(1L);
        assertNotNull(usuario);
        assertEquals("Juan Pérez López", usuario.getNombres());
    }

    @Test
    public void testSave() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        assertNotNull(savedUsuario);
        assertEquals("Juan Pérez López", savedUsuario.getNombres());
    }

    @Test
    public void testPatchUsuario() {
        Usuario existingUsuario = createUsuario();
        Usuario patchData = new Usuario();
        patchData.setNombres("Juan Actualizado");

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUsuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existingUsuario);

        Usuario patchedUsuario = usuarioService.patchUsuario(1L, patchData);
        assertNotNull(patchedUsuario);
        assertEquals("Juan Actualizado", patchedUsuario.getNombres());
    }

    @Test
    public void testDeleteById() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.deleteById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindUsuariosByObraAndTeatro() {
        Long obraId = 1L;
        Long teatroId = 2L;
        List<Usuario> expected = List.of(createUsuario());

        when(usuarioRepository.findUsuariosByObraAndTeatro(obraId, teatroId)).thenReturn(expected);

        List<Usuario> usuarios = usuarioService.findUsuariosByObraAndTeatro(obraId, teatroId);

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Juan Pérez López", usuarios.get(0).getNombres());
    }

    @Test
    public void testFindUsuariosByMetodoPagoAndObra() {
        String metodoPago = "EFECTIVO";
        Long obraId = 1L;
        List<Usuario> expected = List.of(createUsuario());

        when(usuarioRepository.findUsuariosByMetodoPagoAndObra(metodoPago, obraId)).thenReturn(expected);

        List<Usuario> usuarios = usuarioService.findUsuariosByMetodoPagoAndObra(metodoPago, obraId);

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Juan Pérez López", usuarios.get(0).getNombres());
    }
}
