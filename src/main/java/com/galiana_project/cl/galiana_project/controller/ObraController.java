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

import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.service.ObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Obras", description = "Operaciones relacionadas con las obras")
@RestController
@RequestMapping("/api/v1/obras")
public class ObraController {
    @Autowired
    private ObraService obraService;

    @GetMapping()
    @Operation(summary = "Listar obras", description = "Obtiene una lista de todas las obras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de obras obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay obras disponibles")
    })
    public ResponseEntity<List<Obra>> listarObras() {
        List<Obra> obras = obraService.findAll();
        if (obras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(obras);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar obra por ID", description = "Obtiene una obra específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra encontrada"),
            @ApiResponse(responseCode = "404", description = "Obra no encontrada")
    })
    public ResponseEntity<Obra> buscarObraPorId(@PathVariable Long id) {
        try {
            Obra obra = obraService.findById(id);
            return ResponseEntity.ok(obra);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva obra", description = "Crea una nueva obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Obra> guardar(@PathVariable Obra obra) {
        Obra obraNuevo = obraService.save(obra);
        return ResponseEntity.status(HttpStatus.CREATED).body(obraNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar obra", description = "Actualiza una obra existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra no encontrada")
    })
    public ResponseEntity<Obra> actualizar(@PathVariable Long id, @RequestBody Obra obra) {
        try {
            Obra obraActualizada = obraService.updateObra(id, obra);
            return ResponseEntity.ok(obraActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente obra", description = "Actualiza parcialmente una obra existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra no encontrada")
    })
    public ResponseEntity<Obra> actualizarParcial(@PathVariable Long id, @RequestBody Obra obraParcial) {
        try {
            Obra obraActualizada = obraService.updateObra(id, obraParcial);
            return ResponseEntity.ok(obraActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar obra", description = "Elimina una obra por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            obraService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/director/{id}")
    @Operation(summary = "Obras por director", description = "Obtiene las obras de un director específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras encontradas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay obras disponibles para el director")
    })
    public ResponseEntity<List<Obra>> listarObrasDeDirector(@PathVariable Long id) {
        List<Obra> obras = obraService.findObrasDelDirectorId(id);
        if (obras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(obras);
    }

    @GetMapping("/teatro/{teatroId}/director/{directorId}")
    @Operation(summary = "Obras por teatro y director", description = "Obtiene las obras de un teatro y director específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras encontradas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay obras disponibles para el teatro y director")
    })
    public ResponseEntity<List<Obra>> listarObrasDeTeatroDirector(
            @PathVariable Long teatroId,
            @PathVariable Long directorId) {
        List<Obra> obras = obraService.findObrasDeTeatroDirector(teatroId, directorId);
        if (obras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(obras);
    }

    @GetMapping("/teatro/{teatroId}/comuna/{comunaId}")
    @Operation(summary = "Obras por teatro y comuna", description = "Obtiene las obras de un teatro y comuna específicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras encontradas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay obras disponibles")
    })
    public ResponseEntity<List<Obra>> listarObrasPorTeatroYComuna(
            @PathVariable Long teatroId,
            @PathVariable Long comunaId) {
        List<Obra> obras = obraService.findObrasPorTeatroYComuna(teatroId, comunaId);
        if (obras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(obras);
    }
}
