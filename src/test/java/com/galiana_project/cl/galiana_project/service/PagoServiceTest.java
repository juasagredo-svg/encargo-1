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

import com.galiana_project.cl.galiana_project.model.Pago;
import com.galiana_project.cl.galiana_project.repository.PagoRepository;

@SpringBootTest
public class PagoServiceTest {

    @Autowired
    private PagoService pagoService;

    @MockBean
    private PagoRepository pagoRepository;

    private Pago createPago() {
        return new Pago(1, "Efectivo");
    }

    @Test
    public void testFindAll() {
        when(pagoRepository.findAll()).thenReturn(List.of(createPago()));
        List<Pago> pagos = pagoRepository.findAll();
        assertNotNull(pagos);
        assertEquals(1, pagos.size());
    }

    @Test
    public void testFindById() {
        when(pagoRepository.findById(1L)).thenReturn(java.util.Optional.of(createPago()));
        Pago pago = pagoRepository.findById(1L).orElse(null);
        assertNotNull(pago);
        assertEquals("Efectivo", pago.getMetodoPago());
    }

    @Test
    public void testSave() {
        Pago pago = createPago();
        when(pagoRepository.save(pago)).thenReturn(pago);
        Pago savedPago = pagoRepository.save(pago);
        assertNotNull(savedPago);
        assertEquals("Efectivo", savedPago.getMetodoPago());
    }

    @Test
    public void testPatchPago() {
        Pago existingPago = createPago();
        Pago patchData = new Pago();
        patchData.setMetodoPago("Efectivo Actualizado");

        when(pagoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingPago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(existingPago);

        Pago patchedPago = pagoService.patchPago(1L, patchData);
        assertNotNull(patchedPago);
        assertEquals("Efectivo Actualizado", patchedPago.getMetodoPago());
    }

    @Test
    public void testDeleteById() {
        Pago pago = createPago();
        when(pagoRepository.findById(1L)).thenReturn(java.util.Optional.of(pago));
        doNothing().when(pagoRepository).deleteById(1L);
        pagoRepository.deleteById(1L);
        verify(pagoRepository, times(1)).deleteById(1L);
    }
}
