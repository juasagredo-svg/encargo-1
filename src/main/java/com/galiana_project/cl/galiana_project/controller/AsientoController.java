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

import com.galiana_project.cl.galiana_project.model.Asiento;
import com.galiana_project.cl.galiana_project.service.AsientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Asientos", description = "Operaciones relacionadas con los asientos")
@RestController
@RequestMapping("/api/v1/asientos")
public class AsientoController {
    @Autowired
    private AsientoService asientoService;

    @GetMapping()
    @Operation(summary = "Listar asientos", description = "Obtiene una lista de todos los asientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asientos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay asientos disponibles")
    })
    public ResponseEntity<List<Asiento>> listarAsientos() {
        List<Asiento> asientos = asientoService.findAll();
        if (asientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(asientos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asiento por ID", description = "Obtiene un asiento especÃ­fico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public ResponseEntity<Asiento> buscarAsientoPorId(@PathVariable Long id) {
        try {
            Asiento asiento = asientoService.findById(id);
            return ResponseEntity.ok(asiento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo asiento", description = "Crea un nuevo asiento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Asiento> guardar(@RequestBody Asiento asiento) {
        Asiento asientoNuevo = asientoService.save(asiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(asientoNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar asiento", description = "Actualiza un asiento existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public ResponseEntity<Asiento> actualizar(@PathVariable Long id, @RequestBody Asiento asiento) {
        try {
            Asiento asientoActualizado = asientoService.updateAsiento(id, asiento);
            return ResponseEntity.ok(asientoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente asiento", description = "Actualiza parcialmente un asiento existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento actualizado parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public ResponseEntity<Asiento> actualizarParcial(@PathVariable Long id, @RequestBody Asiento asientoParcial) {
        try {
            Asiento asientoActualizado = asientoService.patchAsiento(id, asientoParcial);
            return ResponseEntity.ok(asientoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar asiento", description = "Elimina un asiento existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asiento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            asientoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
