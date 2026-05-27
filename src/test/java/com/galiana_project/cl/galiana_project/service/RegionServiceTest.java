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

import com.galiana_project.cl.galiana_project.model.Region;
import com.galiana_project.cl.galiana_project.repository.RegionRepository;

@SpringBootTest
public class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @MockBean
    private RegionRepository regionRepository;

    private Region createRegion() {
        return new Region(1, "Región de Valparaíso");
    }

    @Test
    public void testFindAll() {
        when(regionService.findAll()).thenReturn(List.of(createRegion()));
        List<Region> regiones = regionService.findAll();
        assertNotNull(regiones);
        assertEquals(1, regiones.size());
    }

    @Test
    public void testFindById() {
        when(regionRepository.findById(1L)).thenReturn(java.util.Optional.of(createRegion()));
        Region region = regionService.findById(1L);
        assertNotNull(region);
        assertEquals("Región de Valparaíso", region.getNombre());
    }

    @Test
    public void testSave() {
        Region region = createRegion();
        when(regionRepository.save(region)).thenReturn(region);
        Region savedRegion = regionRepository.save(region);
        assertNotNull(savedRegion);
        assertEquals("Región de Valparaíso", savedRegion.getNombre());
    }

    @Test
    public void testPatchRegion() {
        Region existingRegion = createRegion();
        Region patchData = new Region();
        patchData.setNombre("Región Actualizado");

        when(regionRepository.findById(1L)).thenReturn(java.util.Optional.of(existingRegion));
        when(regionRepository.save(any(Region.class))).thenReturn(existingRegion);

        Region patchedRegion = regionService.patchRegion(1L, patchData);
        assertNotNull(patchedRegion);
        assertEquals("Región Actualizado", patchedRegion.getNombre());
    }

    @Test
    public void testDeleteById() {
        Region region = createRegion();
        when(regionRepository.findById(1L)).thenReturn(java.util.Optional.of(region));
        doNothing().when(regionRepository).deleteById(1L);
        regionService.deleteById(1L);
        verify(regionRepository, times(1)).deleteById(1L);
    }
}
