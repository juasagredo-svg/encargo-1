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

import com.galiana_project.cl.galiana_project.model.ObraSala;
import com.galiana_project.cl.galiana_project.service.ObraSalaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ObraSala", description = "Controlador para gestionar las obras de sala")
@RestController
@RequestMapping("/api/v1/obraSala")
public class ObraSalaController {

    @Autowired
    private ObraSalaService obraSalaService;

    @GetMapping()
    @Operation(summary = "Listar todas las obras de sala", description = "Obtiene una lista de todas las obras de sala disponibles en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de obras de sala"),
            @ApiResponse(responseCode = "204", description = "No hay obras de sala disponibles")
    })
    public ResponseEntity<List<ObraSala>> listarObrasSala() {
        List<ObraSala> obraSalas = obraSalaService.findAll();
        if (obraSalas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(obraSalas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar obra de sala por ID", description = "Obtiene una obra de sala específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra de sala encontrada"),
            @ApiResponse(responseCode = "404", description = "Obra de sala no encontrada")
    })
    public ResponseEntity<ObraSala> buscarObraSalaPorId(@PathVariable Long id) {
        try {
            ObraSala obraSala = obraSalaService.findById(id);
            return ResponseEntity.ok(obraSala);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva obra de sala", description = "Crea una nueva obra de sala en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra de sala creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<ObraSala> guardar(@RequestBody ObraSala obraSala) {
        ObraSala obraSalaNuevo = obraSalaService.save(obraSala);
        return ResponseEntity.status(HttpStatus.CREATED).body(obraSalaNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar obra de sala por ID", description = "Actualiza una obra de sala existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra de sala actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra de sala no encontrada")
    })
    public ResponseEntity<ObraSala> actualizar(@PathVariable Long id, @RequestBody ObraSala obraSala) {
        try {
            ObraSala obraSalaActualizada = obraSalaService.updateObraSala(id, obraSala);
            return ResponseEntity.ok(obraSalaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente obra de sala por ID", description = "Actualiza parcialmente una obra de sala existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra de sala actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra de sala no encontrada")
    })
    public ResponseEntity<ObraSala> actualizarParcial(@PathVariable Long id, @RequestBody ObraSala obraSalaParcial) {
        try {
            ObraSala obraSalaActualizada = obraSalaService.patchObraSala(id, obraSalaParcial);
            return ResponseEntity.ok(obraSalaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar obra de sala por ID", description = "Elimina una obra de sala existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra de sala eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra de sala no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            obraSalaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
