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

import com.galiana_project.cl.galiana_project.model.Asiento;
import com.galiana_project.cl.galiana_project.model.AsientoBoleta;
import com.galiana_project.cl.galiana_project.model.Boleta;
import com.galiana_project.cl.galiana_project.repository.AsientoBoletaRepository;
import com.galiana_project.cl.galiana_project.repository.AsientoRepository;

@SpringBootTest
public class AsientoBoletaServiceTest {
    @Autowired
    private AsientoBoletaService asientoBoletaService;

    @MockBean
    private AsientoBoletaRepository asientoBoletaRepository;

    @MockBean
    private AsientoRepository asientoRepository;

    private AsientoBoleta createAsientoBoleta() {
        Asiento asiento = new Asiento();
        asiento.setId(1);
        return new AsientoBoleta(1, asiento, new Boleta());
    }

    @Test
    public void testFindAll() {
        when(asientoBoletaService.findAll()).thenReturn(List.of(createAsientoBoleta()));
        List<AsientoBoleta> asientoBoletas = asientoBoletaService.findAll();
        assertNotNull(asientoBoletas);
        assertEquals(1, asientoBoletas.size());
    }

    @Test
    public void testFindById() {
        when(asientoBoletaRepository.findById(1L)).thenReturn(java.util.Optional.of(createAsientoBoleta()));
        AsientoBoleta asientoBoleta = asientoBoletaService.findById(1L);
        assertNotNull(asientoBoleta);
        assertEquals(1, asientoBoleta.getId());
    }

    @Test
    public void testSave() {
        AsientoBoleta asientoBoleta = createAsientoBoleta();
        when(asientoBoletaRepository.save(asientoBoleta)).thenReturn(asientoBoleta);
        AsientoBoleta savedAsientoBoleta = asientoBoletaRepository.save(asientoBoleta);
        assertNotNull(savedAsientoBoleta);
        assertEquals(1, savedAsientoBoleta.getId());
    }

    @Test
    public void testPatchAsientoBoleta() {
        AsientoBoleta asientoBoleta = createAsientoBoleta();
        when(asientoBoletaRepository.findById(1L)).thenReturn(java.util.Optional.of(asientoBoleta));
        when(asientoBoletaRepository.save(asientoBoleta)).thenReturn(asientoBoleta);
        AsientoBoleta patchedAsientoBoleta = asientoBoletaService.patchAsientoBoleta(1L, asientoBoleta);
        assertNotNull(patchedAsientoBoleta);
        assertEquals(1, patchedAsientoBoleta.getId());
    }

    @Test
    public void testDeleteById() {
        AsientoBoleta asientoBoleta = createAsientoBoleta();
        when(asientoBoletaRepository.findById(1L)).thenReturn(java.util.Optional.of(asientoBoleta));
        when(asientoBoletaRepository.findByAsiento(asientoBoleta.getAsiento())).thenReturn(List.of(asientoBoleta));
        doNothing().when(asientoBoletaRepository).deleteById(1L);
        asientoBoletaService.deleteById(1L);
        verify(asientoBoletaRepository, times(1)).deleteById(1L);
    }
}
