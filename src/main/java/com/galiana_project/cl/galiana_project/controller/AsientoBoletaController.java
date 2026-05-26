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

import com.galiana_project.cl.galiana_project.model.AsientoBoleta;
import com.galiana_project.cl.galiana_project.service.AsientoBoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/asientoBoleta")
@Tag(name = "AsientoBoleta", description = "Controlador para gestionar los asientos de boletas")
public class AsientoBoletaController {

    @Autowired
    private AsientoBoletaService asientoBoletaService;

    @GetMapping()
    @Operation(summary = "Listar asientos de boletas", description = "Obtiene una lista de todos los asientos de boletas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asientos de boletas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay asientos de boletas disponibles")
    })
    public ResponseEntity<List<AsientoBoleta>> listarAsientosBoleta() {
        List<AsientoBoleta> asientoBoletas = asientoBoletaService.findAll();
        if (asientoBoletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asientoBoletas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asiento de boleta por ID", description = "Obtiene un asiento de boleta específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento de boleta encontrado"),
            @ApiResponse(responseCode = "404", description = "Asiento de boleta no encontrado")
    })
    public ResponseEntity<AsientoBoleta> buscarAsientoBoletaPorId(@PathVariable Long id) {
        try {
            AsientoBoleta asientoBoleta = asientoBoletaService.findById(id);
            return ResponseEntity.ok(asientoBoleta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo asiento de boleta", description = "Crea un nuevo asiento de boleta en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asiento de boleta creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<AsientoBoleta> guardar(@RequestBody AsientoBoleta asientoBoleta) {
        AsientoBoleta asientoBoletaNuevo = asientoBoletaService.save(asientoBoleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(asientoBoletaNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar asiento de boleta por ID", description = "Actualiza un asiento de boleta existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento de boleta actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento de boleta no encontrado")
    })
    public ResponseEntity<AsientoBoleta> actualizar(@PathVariable Long id, @RequestBody AsientoBoleta asientoBoleta) {
        try {
            AsientoBoleta asientoBoletaActualizada = asientoBoletaService.updateAsientoBoleta(id, asientoBoleta);
            return ResponseEntity.ok(asientoBoletaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente asiento de boleta por ID", description = "Actualiza parcialmente un asiento de boleta existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento de boleta actualizado parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento de boleta no encontrado")
    })
    public ResponseEntity<AsientoBoleta> actualizarParcial(@PathVariable Long id,
            @RequestBody AsientoBoleta asientoBoletaParcial) {
        try {
            AsientoBoleta asientoBoletaActualizada = asientoBoletaService.patchAsientoBoleta(id, asientoBoletaParcial);
            return ResponseEntity.ok(asientoBoletaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar asiento de boleta por ID", description = "Elimina un asiento de boleta existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asiento de boleta eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento de boleta no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            asientoBoletaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
