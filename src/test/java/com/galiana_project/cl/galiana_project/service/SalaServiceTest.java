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
import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.repository.SalaRepository;

@SpringBootTest
public class SalaServiceTest {

    @Autowired
    private SalaService salaService;

    @MockBean
    private SalaRepository salaRepository;

    private Sala createSala() {
        return new Sala(1, 101, 120, new SalaTeatro());
    }

    @Test
    public void testFindAll() {
        when(salaService.findAll()).thenReturn(List.of(createSala()));
        List<Sala> salas = salaService.findAll();
        assertNotNull(salas);
        assertEquals(1, salas.size());
    }

    @Test
    public void testFindById() {
        when(salaRepository.findById(1L)).thenReturn(java.util.Optional.of(createSala()));
        Sala sala = salaService.findById(1L);
        assertNotNull(sala);
        assertEquals(101, sala.getNumSala());
    }

    @Test
    public void testSave() {
        Sala sala = createSala();
        when(salaRepository.save(sala)).thenReturn(sala);
        Sala savedSala = salaRepository.save(sala);
        assertNotNull(savedSala);
        assertEquals(101, savedSala.getNumSala());
    }

    @Test
    public void testPatchSala() {
        Sala existingSala = createSala();
        Sala patchData = new Sala();
        patchData.setNumSala(102);

        when(salaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingSala));
        when(salaRepository.save(any(Sala.class))).thenReturn(existingSala);

        Sala patchedSala = salaService.patchSala(1L, patchData);
        assertNotNull(patchedSala);
        assertEquals(102, patchedSala.getNumSala());
    }

    @Test
    public void testDeleteById() {
        Sala sala = createSala();
        when(salaRepository.findById(1L)).thenReturn(java.util.Optional.of(sala));
        doNothing().when(salaRepository).deleteById(1L);
        salaService.deleteById(1L);
        verify(salaRepository, times(1)).deleteById(1L);
    }
}
