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

import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.service.SalaTeatroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Salas teatro", description = "Operaciones relacionadas con la coneccion entre Salas y Teatro")
@RestController
@RequestMapping("/api/v1/salasTeatro")
public class SalaTeatroController {

    @Autowired
    private SalaTeatroService salaTeatroService;

    @GetMapping()
    @Operation(summary = "Listar salas teatro", description = "Obtiene una lista de todas las salas teatro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas teatro obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay salas teatro disponibles")
    })
    public ResponseEntity<List<SalaTeatro>> listarObrasTeatro() {
        List<SalaTeatro> obrasTeatro = salaTeatroService.findAll();
        if (obrasTeatro.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(obrasTeatro);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sala teatro por ID", description = "Obtiene una sala teatro específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala teatro encontrada"),
            @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
    })
    public ResponseEntity<SalaTeatro> buscarsalaTeatroPorId(@PathVariable Long id) {
        try {
            SalaTeatro salaTeatro = salaTeatroService.findById(id);
            return ResponseEntity.ok(salaTeatro);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar sala teatro", description = "Crea una nueva sala teatro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sala teatro creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<SalaTeatro> guardar(@RequestBody SalaTeatro salaTeatro) {
        SalaTeatro salaTeatroNuevo = salaTeatroService.save(salaTeatro);
        return ResponseEntity.status(HttpStatus.CREATED).body(salaTeatroNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sala teatro", description = "Actualiza una sala teatro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala teatro actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
    })
    public ResponseEntity<SalaTeatro> actualizar(@PathVariable Long id, @RequestBody SalaTeatro salaTeatro) {
        try {
            SalaTeatro salaTeatroActualizada = salaTeatroService.updatesalaTeatro(id, salaTeatro);
            return ResponseEntity.ok(salaTeatroActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente sala teatro", description = "Actualiza parcialmente una sala teatro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala teatro actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
    })
    public ResponseEntity<SalaTeatro> actualizarParcial(@PathVariable Long id, @RequestBody SalaTeatro obraParcial) {
        try {
            SalaTeatro obraActualizada = salaTeatroService.patchsalaTeatro(id, obraParcial);
            return ResponseEntity.ok(obraActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sala teatro", description = "Elimina una sala teatro existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala teatro eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            salaTeatroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
