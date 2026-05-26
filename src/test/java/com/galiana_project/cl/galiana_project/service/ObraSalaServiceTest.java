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

import com.galiana_project.cl.galiana_project.model.ObraSala;
import com.galiana_project.cl.galiana_project.repository.ObraRepository;
import com.galiana_project.cl.galiana_project.repository.ObraSalaRepository;
import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.model.Sala;

@SpringBootTest
public class ObraSalaServiceTest {
    @Autowired
    private ObraSalaService obraSalaService;

    @MockBean
    private ObraSalaRepository obraSalaRepository;

    @MockBean
    private ObraRepository obraRepository;

    private ObraSala createObraSala() {
        Obra obra = new Obra();
        obra.setId(1);
        return new ObraSala(1, new Sala(), obra);
    }

    @Test
    public void testFindAll() {
        when(obraSalaService.findAll()).thenReturn(List.of(createObraSala()));
        List<ObraSala> obras = obraSalaService.findAll();
        assertNotNull(obras);
        assertEquals(1, obras.size());
    }

    @Test
    public void testFindById() {
        when(obraSalaRepository.findById(1L)).thenReturn(java.util.Optional.of(createObraSala()));
        ObraSala obraSala = obraSalaService.findById(1L);
        assertNotNull(obraSala);
        assertEquals(1, obraSala.getId());
    }

    @Test
    public void testSave() {
        ObraSala obraSala = createObraSala();
        when(obraSalaRepository.save(obraSala)).thenReturn(obraSala);
        ObraSala savedObraSala = obraSalaRepository.save(obraSala);
        assertNotNull(savedObraSala);
        assertEquals(1, savedObraSala.getId());
    }

    @Test
    public void testPatchObraSala() {
        ObraSala obraSala = createObraSala();
        when(obraSalaRepository.findById(1L)).thenReturn(java.util.Optional.of(obraSala));
        when(obraSalaRepository.save(obraSala)).thenReturn(obraSala);
        ObraSala patchedObraSala = obraSalaService.patchObraSala(1L, obraSala);
        assertNotNull(patchedObraSala);
        assertEquals(1, patchedObraSala.getId());
    }

    @Test
    public void testDeleteById() {
        ObraSala obraSala = createObraSala();
        when(obraSalaRepository.findById(1L)).thenReturn(java.util.Optional.of(obraSala));
        when(obraSalaRepository.findByObra(obraSala.getObra())).thenReturn(List.of(obraSala));
        doNothing().when(obraSalaRepository).deleteById(1L);
        obraSalaService.deleteById(1L);
        verify(obraSalaRepository, times(1)).deleteById(1L);
    }
}
