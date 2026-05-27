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

import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.model.Teatro;
import com.galiana_project.cl.galiana_project.repository.TeatroRepository;

@SpringBootTest
public class TeatroServiceTest {

    @Autowired
    private TeatroService teatroService;

    @MockBean
    private TeatroRepository teatroRepository;

    private Teatro createTeatro() {
        return new Teatro(1, "Teatro Municipal de Santiago", "Agustinas 794, Santiago", "+56 2 2463 5500",
                new Comuna());
    }

    @Test
    public void testFindAll() {
        when(teatroService.findAll()).thenReturn(List.of(createTeatro()));
        List<Teatro> teatros = teatroService.findAll();
        assertNotNull(teatros);
        assertEquals(1, teatros.size());
    }

    @Test
    public void testFindById() {
        when(teatroRepository.findById(1L)).thenReturn(java.util.Optional.of(createTeatro()));
        Teatro teatro = teatroService.findById(1L);
        assertNotNull(teatro);
        assertEquals("Teatro Municipal de Santiago", teatro.getNombre());
    }

    @Test
    public void testSave() {
        Teatro teatro = createTeatro();
        when(teatroRepository.save(teatro)).thenReturn(teatro);
        Teatro savedRegion = teatroRepository.save(teatro);
        assertNotNull(savedRegion);
        assertEquals("Teatro Municipal de Santiago", savedRegion.getNombre());
    }

    @Test
    public void testPatchTeatro() {
        Teatro existingTeatro = createTeatro();
        Teatro patchData = new Teatro();
        patchData.setNombre("Teatro Actualizado");

        when(teatroRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTeatro));
        when(teatroRepository.save(any(Teatro.class))).thenReturn(existingTeatro);

        Teatro patchedTeatro = teatroService.patchTeatro(1L, patchData);
        assertNotNull(patchedTeatro);
        assertEquals("Teatro Actualizado", patchedTeatro.getNombre());
    }

    @Test
    public void testDeleteById() {
        Teatro teatro = createTeatro();
        when(teatroRepository.findById(1L)).thenReturn(java.util.Optional.of(teatro));
        doNothing().when(teatroRepository).deleteById(1L);
        teatroService.deleteById(1L);
        verify(teatroRepository, times(1)).deleteById(1L);
    }
}
