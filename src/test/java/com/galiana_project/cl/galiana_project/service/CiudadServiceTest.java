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
import com.galiana_project.cl.galiana_project.model.Region;
import com.galiana_project.cl.galiana_project.repository.CiudadRepository;

@SpringBootTest
public class CiudadServiceTest {

    @Autowired
    private CiudadService ciudadService;

    @MockBean
    private CiudadRepository ciudadRepository;

    private Ciudad createCiudad() {
        return new Ciudad(1, "Santiago", new Region());
    }

    @Test
    public void testFindAll() {
        when(ciudadService.findAll()).thenReturn(List.of(createCiudad()));
        List<Ciudad> ciudades = ciudadService.findAll();
        assertNotNull(ciudades);
        assertEquals(1, ciudades.size());
    }

    @Test
    public void testFindById() {
        when(ciudadRepository.findById(1L)).thenReturn(java.util.Optional.of(createCiudad()));
        Ciudad ciudad = ciudadService.findById(1L);
        assertNotNull(ciudad);
        assertEquals("Santiago", ciudad.getNombre());
    }

    @Test
    public void testSave() {
        Ciudad ciudad = createCiudad();
        when(ciudadRepository.save(ciudad)).thenReturn(ciudad);
        Ciudad savedCiudad = ciudadRepository.save(ciudad);
        assertNotNull(savedCiudad);
        assertEquals("Santiago", savedCiudad.getNombre());
    }

    @Test
    public void testPatchCiudad() {
        Ciudad existingCiudad = createCiudad();
        Ciudad patchData = new Ciudad();
        patchData.setNombre("Santiago Actualizado");

        when(ciudadRepository.findById(1L)).thenReturn(java.util.Optional.of(existingCiudad));
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(existingCiudad);

        Ciudad patchedCiudad = ciudadService.patchCiudad(1L, patchData);
        assertNotNull(patchedCiudad);
        assertEquals("Santiago Actualizado", patchedCiudad.getNombre());
    }

    @Test
    public void testDeleteById() {
        Ciudad ciudad = createCiudad();
        when(ciudadRepository.findById(1L)).thenReturn(java.util.Optional.of(ciudad));
        doNothing().when(ciudadRepository).deleteById(1L);
        ciudadService.deleteById(1L);
        verify(ciudadRepository, times(1)).deleteById(1L);
    }
}
