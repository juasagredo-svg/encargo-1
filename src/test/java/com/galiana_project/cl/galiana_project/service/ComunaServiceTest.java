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

import com.galiana_project.cl.galiana_project.model.Ciudad;
import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.repository.ComunaRepository;

@SpringBootTest
public class ComunaServiceTest {

    @Autowired
    private ComunaService comunaService;

    @MockBean
    private ComunaRepository comunaRepository;

    private Comuna createComuna() {
        return new Comuna(1, "Providencia", new Ciudad());
    }

    @Test
    public void testFindAll() {
        when(comunaService.findAll()).thenReturn(List.of(createComuna()));
        List<Comuna> comunas = comunaService.findAll();
        assertNotNull(comunas);
        assertEquals(1, comunas.size());
    }

    @Test
    public void testFindById() {
        when(comunaRepository.findById(1L)).thenReturn(java.util.Optional.of(createComuna()));
        Comuna comuna = comunaService.findById(1L);
        assertNotNull(comuna);
        assertEquals("Providencia", comuna.getNombre());
    }

    @Test
    public void testSave() {
        Comuna comuna = createComuna();
        when(comunaRepository.save(comuna)).thenReturn(comuna);
        Comuna savedComuna = comunaRepository.save(comuna);
        assertNotNull(savedComuna);
        assertEquals("Providencia", savedComuna.getNombre());
    }

    @Test
    public void testPatchComuna() {
        Comuna existingComuna = createComuna();
        Comuna patchData = new Comuna();
        patchData.setNombre("Providencia Actualizado");

        when(comunaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingComuna));
        when(comunaRepository.save(any(Comuna.class))).thenReturn(existingComuna);

        Comuna patchedComuna = comunaService.patchComuna(1L, patchData);
        assertNotNull(patchedComuna);
        assertEquals("Providencia Actualizado", patchedComuna.getNombre());
    }

    @Test
    public void testDeleteById() {
        Comuna comuna = createComuna();
        when(comunaRepository.findById(1L)).thenReturn(java.util.Optional.of(comuna));
        doNothing().when(comunaRepository).deleteById(1L);
        comunaService.deleteById(1L);
        verify(comunaRepository, times(1)).deleteById(1L);
    }
}
