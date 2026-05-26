package com.galiana_project.cl.galiana_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galiana_project.cl.galiana_project.model.Director;
import com.galiana_project.cl.galiana_project.repository.DirectorRepository;

@SpringBootTest
public class DirectorServiceTest {
    @Autowired
    private DirectorService directorService;

    @MockBean
    private DirectorRepository directorRepository;

    private Director createDirector() {
        return new Director(1, "Pedro Almodóvar", new Date());
    }

    @Test
    public void testFindAll() {
        when(directorService.findAll()).thenReturn(List.of(createDirector()));
        List<Director> directores = directorService.findAll();
        assertNotNull(directores);
        assertEquals(1, directores.size());
    }

    @Test
    public void testFindById() {
        when(directorRepository.findById(1L)).thenReturn(java.util.Optional.of(createDirector()));
        Director director = directorService.findById(1L);
        assertNotNull(director);
        assertEquals("Pedro Almodóvar", director.getNombres());
    }

    @Test
    public void testSave() {
        Director director = createDirector();
        when(directorRepository.save(director)).thenReturn(director);
        Director savedDirector = directorRepository.save(director);
        assertNotNull(savedDirector);
        assertEquals("Pedro Almodóvar", savedDirector.getNombres());
    }

    @Test
    public void testPatchDirector() {
        Director existingDirector = createDirector();
        Director patchData = new Director();
        patchData.setNombres("Pedro Actualizado");

        when(directorRepository.findById(1L)).thenReturn(java.util.Optional.of(existingDirector));
        when(directorRepository.save(any(Director.class))).thenReturn(existingDirector);

        Director patchedDirector = directorService.patchDirector(1L, patchData);
        assertNotNull(patchedDirector);
        assertEquals("Pedro Actualizado", patchedDirector.getNombres());
    }

    @Test
    public void testDeleteById() {
        Director director = createDirector();
        when(directorRepository.findById(1L)).thenReturn(java.util.Optional.of(director));
        doNothing().when(directorRepository).deleteById(1L);
        directorService.deleteById(1L);
        verify(directorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByFechaNacimientoBetween() {
        Date fechaInicio = new Date(0);
        Date fechaFin = new Date();
        List<Director> expected = List.of(createDirector());
        when(directorRepository.findByFechaNacimientoBetween(fechaInicio, fechaFin)).thenReturn(expected);

        List<Director> directores = directorService.findByFechaNacimientoBetween(fechaInicio, fechaFin);

        assertNotNull(directores);
        assertFalse(directores.isEmpty());
        assertEquals("Pedro Almodóvar", directores.get(0).getNombres());
    }

    @Test
    public void testFindByNombresContainingAndFechaNacimientoBetween() {
        Date fechaInicio = new Date(0);
        Date fechaFin = new Date();
        List<Director> expected = List.of(createDirector());
        when(directorRepository.findByNombresContainingAndFechaNacimientoBetween("Pedro", fechaInicio, fechaFin))
                .thenReturn(expected);

        List<Director> directores = directorService.findByNombresContainingAndFechaNacimientoBetween("Pedro",
                fechaInicio,
                fechaFin);

        assertNotNull(directores);
        assertFalse(directores.isEmpty());
        assertEquals("Pedro Almodóvar", directores.get(0).getNombres());
    }
}
