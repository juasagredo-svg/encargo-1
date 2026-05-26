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

import com.galiana_project.cl.galiana_project.model.Region;
import com.galiana_project.cl.galiana_project.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Regiones", description = "Operaciones relacionadas con las regiones")
@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping()
    @Operation(summary = "Listar regiones", description = "Obtiene una lista de todas las regiones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de regiones obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay regiones disponibles")
    })
    public ResponseEntity<List<Region>> listarRegiones() {
        List<Region> regiones = regionService.findAll();
        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar región por ID", description = "Obtiene una región específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región encontrada"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Region> buscarRegionPorId(@PathVariable Long id) {
        try {
            Region region = regionService.findById(id);
            return ResponseEntity.ok(region);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva región", description = "Crea una nueva región")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Región creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Region> guardar(@RequestBody Region region) {
        Region regionNuevo = regionService.save(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar región", description = "Actualiza una región existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Region> actualizar(@PathVariable Long id, @RequestBody Region region) {
        try {
            Region regionActualizada = regionService.updateRegion(id, region);
            return ResponseEntity.ok(regionActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente región", description = "Actualiza parcialmente una región existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Region> actualizarParcial(@PathVariable Long id, @RequestBody Region region) {
        try {
            Region regionActualizada = regionService.patchRegion(id, region);
            return ResponseEntity.ok(regionActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar región", description = "Elimina una región existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Región eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            regionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
