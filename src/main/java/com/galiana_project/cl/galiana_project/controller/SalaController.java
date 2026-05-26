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

import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Salas", description = "Operaciones relacionadas con las salas")
@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    @Operation(summary = "Listar salas", description = "Obtiene una lista de todas las salas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay salas disponibles")
    })
    public ResponseEntity<List<Sala>> listarSalas() {
        List<Sala> salas = salaService.findAll();
        if (salas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala por ID", description = "Obtiene una sala específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala encontrada"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    public ResponseEntity<Sala> buscarSalaPorId(@PathVariable Long id) {
        try {
            Sala sala = salaService.findById(id);
            return ResponseEntity.ok(sala);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nueva sala", description = "Guarda una nueva sala en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sala creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Sala> guardar(@RequestBody Sala sala) {
        Sala salaNueva = salaService.save(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(salaNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sala", description = "Actualiza los detalles de una sala existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    public ResponseEntity<Sala> actualizar(@PathVariable Long id, @RequestBody Sala sala) {
        try {
            Sala salaActualizada = salaService.updateSala(id, sala);
            return ResponseEntity.ok(salaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente sala", description = "Actualiza parcialmente los detalles de una sala existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    public ResponseEntity<Sala> patchSala(@PathVariable Long id, @RequestBody Sala salaParcial) {
        try {
            Sala salaActualizada = salaService.patchSala(id, salaParcial);
            return ResponseEntity.ok(salaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sala", description = "Elimina una sala del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            salaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
