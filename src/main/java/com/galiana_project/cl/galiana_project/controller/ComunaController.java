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

import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.service.ComunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comunas", description = "Operaciones relacionadas con las comunas")
@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    @Operation(summary = "Listar comunas", description = "Obtiene una lista de todas las comunas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comunas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay comunas disponibles")
    })
    public ResponseEntity<List<Comuna>> listarComunas() {
        List<Comuna> comunas = comunaService.findAll();
        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comunas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comuna por ID", description = "Obtiene una comuna específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comuna encontrada"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Comuna> buscarComunaPorId(@PathVariable Long id) {
        try {
            Comuna comuna = comunaService.findById(id);
            return ResponseEntity.ok(comuna);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva comuna", description = "Crea una nueva comuna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comuna creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Comuna> guardar(@RequestBody Comuna comuna) {
        Comuna comunaNuevo = comunaService.save(comuna);
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar comuna", description = "Actualiza una comuna existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comuna actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Comuna> actualizar(@PathVariable Long id, @RequestBody Comuna comuna) {
        try {
            Comuna comunaActualizada = comunaService.updateComuna(id, comuna);
            return ResponseEntity.ok(comunaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente comuna", description = "Actualiza parcialmente una comuna existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comuna actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Comuna> actualizarParcial(@PathVariable Long id, @RequestBody Comuna comunaParcial) {
        try {
            Comuna comunaActualizada = comunaService.patchComuna(id, comunaParcial);
            return ResponseEntity.ok(comunaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comuna eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            comunaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
