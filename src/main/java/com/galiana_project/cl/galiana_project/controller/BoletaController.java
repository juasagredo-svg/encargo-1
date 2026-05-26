package com.galiana_project.cl.galiana_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galiana_project.cl.galiana_project.model.Boleta;
import com.galiana_project.cl.galiana_project.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Boletas", description = "Operaciones relacionadas con las boletas")
@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {
    @Autowired
    private BoletaService boletaService;

    @GetMapping
    @Operation(summary = "Listar boletas", description = "Obtiene una lista de todas las boletas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de boletas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay boletas disponibles")
    })
    public ResponseEntity<List<Boleta>> listar() {
        List<Boleta> boletas = boletaService.findAll();
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar boleta por ID", description = "Obtiene una boleta especÃ­fica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleta encontrada"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    public ResponseEntity<Boleta> buscarPorId(@PathVariable Long id) {
        try {
            Boleta boleta = boletaService.findById(id);
            return ResponseEntity.ok(boleta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear boleta", description = "Crea una nueva boleta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boleta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Boleta> guardar(@RequestBody Boleta boleta) {
        Boleta boletaNueva = boletaService.save(boleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(boletaNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar boleta", description = "Actualiza una boleta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleta actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    public ResponseEntity<Boleta> actualizar(@PathVariable Long id, @RequestBody Boleta boleta) {
        try {
            Boleta boletaActualizada = boletaService.updateBoleta(id, boleta);
            return ResponseEntity.ok(boletaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar boleta parcialmente", description = "Actualiza parcialmente una boleta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleta actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    public ResponseEntity<Boleta> actualizarParcial(@PathVariable Long id, @RequestBody Boleta boletaParcial) {
        try {
            Boleta boletaActualizada = boletaService.patchBoleta(id, boletaParcial);
            return ResponseEntity.ok(boletaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar boleta", description = "Elimina una boleta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Boleta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            boletaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/pago/{pagoId}")
    @Operation(summary = "Buscar boletas por usuario y pago", description = "Obtiene una lista de boletas filtradas por el ID del usuario y el ID del pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boletas encontradas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay boletas disponibles para los criterios especificados")
    })
    public ResponseEntity<List<Boleta>> findByUsuario_IdAndPago_Id(@PathVariable Long usuarioId,
            @PathVariable Long pagoId) {
        List<Boleta> boletas = boletaService.findByUsuario_IdAndPago_Id(usuarioId, pagoId);
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    @GetMapping("/usuario/{usuarioId}/precio/{precioTotal}")
    @Operation(summary = "Buscar boletas por usuario y precio total", description = "Obtiene una lista de boletas filtradas por el ID del usuario y el precio total")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boletas encontradas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay boletas disponibles para los criterios especificados")
    })
    public ResponseEntity<List<Boleta>> findByUsuario_IdAndPrecioTotal(@PathVariable Long usuarioId,
            @PathVariable Integer precioTotal) {
        List<Boleta> boletas = boletaService.findByUsuario_IdAndPrecioTotal(usuarioId, precioTotal);
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

}
