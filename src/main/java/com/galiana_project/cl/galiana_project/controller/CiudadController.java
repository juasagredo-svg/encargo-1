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

import com.galiana_project.cl.galiana_project.model.Ciudad;
import com.galiana_project.cl.galiana_project.service.CiudadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ciudades", description = "Operaciones relacionadas con las ciudades")
@RestController
@RequestMapping("/api/v1/ciudades")
public class CiudadController {
    @Autowired
    private CiudadService ciudadService;

    @GetMapping()
    @Operation(summary = "Listar ciudades", description = "Obtiene una lista de todas las ciudades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay ciudades disponibles")
    })
    public ResponseEntity<List<Ciudad>> listarCiudades() {
        List<Ciudad> ciudades = ciudadService.findAll();
        if (ciudades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ciudades);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ciudad por ID", description = "Obtiene una ciudad específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad encontrada"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<Ciudad> buscarCiudadPorId(@PathVariable Long id) {
        try {
            Ciudad ciudad = ciudadService.findById(id);
            return ResponseEntity.ok(ciudad);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar ciudad", description = "Crea una nueva ciudad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ciudad creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Ciudad> guardar(@RequestBody Ciudad ciudad) {
        Ciudad ciudadNuevo = ciudadService.save(ciudad);
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ciudad", description = "Actualiza una ciudad existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<Ciudad> actualizar(@PathVariable Long id, @RequestBody Ciudad ciudad) {
        try {
            Ciudad ciudadActualizada = ciudadService.updateCiudad(id, ciudad);
            return ResponseEntity.ok(ciudadActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente ciudad", description = "Actualiza parcialmente una ciudad existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ciudad actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<Ciudad> actualizarParcial(@PathVariable Long id, @RequestBody Ciudad ciudadParcial) {
        try {
            Ciudad ciudadActualizada = ciudadService.patchCiudad(id, ciudadParcial);
            return ResponseEntity.ok(ciudadActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ciudad", description = "Elimina una ciudad existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ciudad eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            ciudadService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
