package com.galiana_project.cl.galiana_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.model.Teatro;
import com.galiana_project.cl.galiana_project.repository.SalaTeatroRepository;

@SpringBootTest
public class SalaTeatroServiceTest {
    @Autowired
    private SalaTeatroService salaTeatroService;

    @MockBean
    private SalaTeatroRepository salaTeatroRepository;

    private SalaTeatro createSalaTeatro() {
        return new SalaTeatro(1, new Teatro());
    }

    @Test
    public void testFindAll() {
        when(salaTeatroService.findAll()).thenReturn(List.of(createSalaTeatro()));
        List<SalaTeatro> salas = salaTeatroService.findAll();
        assertNotNull(salas);
        assertEquals(1, salas.size());
    }

    @Test
    public void testFindById() {
        when(salaTeatroRepository.findById(1L)).thenReturn(java.util.Optional.of(createSalaTeatro()));
        SalaTeatro salaTeatro = salaTeatroService.findById(1L);
        assertNotNull(salaTeatro);
        assertEquals(1, salaTeatro.getId());
    }

    @Test
    public void testSave() {
        SalaTeatro salaTeatro = createSalaTeatro();
        when(salaTeatroRepository.save(salaTeatro)).thenReturn(salaTeatro);
        SalaTeatro savedSalaTeatro = salaTeatroRepository.save(salaTeatro);
        assertNotNull(savedSalaTeatro);
        assertEquals(1, savedSalaTeatro.getId());
    }

    @Test
    public void testPatchSalaTeatro() {
        SalaTeatro salaTeatro = createSalaTeatro();
        when(salaTeatroRepository.findById(1L)).thenReturn(java.util.Optional.of(salaTeatro));
        when(salaTeatroRepository.save(salaTeatro)).thenReturn(salaTeatro);
        SalaTeatro patchedSalaTeatro = salaTeatroService.patchsalaTeatro(1L, salaTeatro);
        assertNotNull(patchedSalaTeatro);
        assertEquals(1, patchedSalaTeatro.getId());
    }

    @Test
    public void testDeleteById() {
        SalaTeatro salaTeatro = createSalaTeatro();
        when(salaTeatroRepository.findById(1L)).thenReturn(java.util.Optional.of(salaTeatro));
        doNothing().when(salaTeatroRepository).deleteById(1L);
        salaTeatroService.deleteById(1L);
        verify(salaTeatroRepository, times(1)).deleteById(1L);
    }
}
