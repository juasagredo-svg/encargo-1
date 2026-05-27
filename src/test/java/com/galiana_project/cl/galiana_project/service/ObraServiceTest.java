package com.galiana_project.cl.galiana_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galiana_project.cl.galiana_project.model.Director;
import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.repository.ObraRepository;

@SpringBootTest
public class ObraServiceTest {

    @Autowired
    private ObraService obraService;

    @MockBean
    private ObraRepository obraRepository;

    private Obra createObra() {
        return new Obra(
                1,
                "La Casa de Bernarda Alba",
                Time.valueOf("19:30:00"),
                new Date(), // November 15, 2023 (months are 0-based)
                new Date(), // December 20, 2023
                35000,
                "Drama familiar escrito por Federico García Lorca que explora temas de represión y conflicto en una familia andaluza",
                new Director());
    }

    @Test
    public void testFindAll() {
        when(obraService.findAll()).thenReturn(List.of(createObra()));
        List<Obra> obras = obraService.findAll();
        assertNotNull(obras);
        assertEquals(1, obras.size());
    }

    @Test
    public void testFindById() {
        when(obraRepository.findById(1L)).thenReturn(java.util.Optional.of(createObra()));
        Obra obra = obraService.findById(1L);
        assertNotNull(obra);
        assertEquals("La Casa de Bernarda Alba", obra.getNombre());
    }

    @Test
    public void testSave() {
        Obra obra = createObra();
        when(obraRepository.save(obra)).thenReturn(obra);
        Obra savedObra = obraRepository.save(obra);
        assertNotNull(savedObra);
        assertEquals("La Casa de Bernarda Alba", savedObra.getNombre());
    }

    @Test
    public void testPatchObra() {
        Obra existingObra = createObra();
        Obra patchData = new Obra();
        patchData.setNombre("La Casa Actualizado");

        when(obraRepository.findById(1L)).thenReturn(java.util.Optional.of(existingObra));
        when(obraRepository.save(any(Obra.class))).thenReturn(existingObra);

        Obra patchedObra = obraService.patchObra(1L, patchData);
        assertNotNull(patchedObra);
        assertEquals("La Casa Actualizado", patchedObra.getNombre());
    }

    @Test
    public void testDeleteById() {
        Obra obra = createObra();
        when(obraRepository.findById(1L)).thenReturn(java.util.Optional.of(obra));
        doNothing().when(obraRepository).deleteById(1L);
        obraService.deleteById(1L);
        verify(obraRepository, times(1)).deleteById(1L);
    }
}
