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

import com.galiana_project.cl.galiana_project.model.Asiento;
import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.repository.AsientoRepository;

@SpringBootTest
public class AsientoServiceTest {

    @Autowired
    private AsientoService asientoService;

    @MockBean
    private AsientoRepository asientoRepository;

    private Asiento createAsiento() {
        return new Asiento(1, 15, 'B', true, new Sala());
    }

    @Test
    public void testFindAll() {
        when(asientoService.findAll()).thenReturn(List.of(createAsiento()));
        List<Asiento> asientos = asientoService.findAll();
        assertNotNull(asientos);
        assertEquals(1, asientos.size());
    }

    @Test
    public void testFindById() {
        when(asientoRepository.findById(1L)).thenReturn(java.util.Optional.of(createAsiento()));
        Asiento asiento = asientoService.findById(1L);
        assertNotNull(asiento);
        assertEquals(15, asiento.getNumAsiento());
    }

    @Test
    public void testSave() {
        Asiento asiento = createAsiento();
        when(asientoRepository.save(asiento)).thenReturn(asiento);
        Asiento savedAsiento = asientoRepository.save(asiento);
        assertNotNull(savedAsiento);
        assertEquals(15, savedAsiento.getNumAsiento());
    }

    @Test
    public void testPatchAsiento() {
        Asiento existingAsiento = createAsiento();
        Asiento patchData = new Asiento();
        patchData.setNumAsiento(13);

        when(asientoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingAsiento));
        when(asientoRepository.save(any(Asiento.class))).thenReturn(existingAsiento);

        Asiento patchedAsiento = asientoService.patchAsiento(1L, patchData);
        assertNotNull(patchedAsiento);
        assertEquals(13, patchedAsiento.getNumAsiento());
    }

    @Test
    public void testDeleteById() {
        Asiento asiento = createAsiento();
        when(asientoRepository.findById(1L)).thenReturn(java.util.Optional.of(asiento));
        doNothing().when(asientoRepository).deleteById(1L);
        asientoService.deleteById(1L);
        verify(asientoRepository, times(1)).deleteById(1L);
    }

}
