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

import com.galiana_project.cl.galiana_project.model.Teatro;
import com.galiana_project.cl.galiana_project.service.TeatroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Teatros", description = "Operaciones relacionadas con los teatros")
@RestController
@RequestMapping("/api/v1/teatros")
public class TeatroController {
    @Autowired
    private TeatroService teatroService;

    @GetMapping()
    @Operation(summary = "Listar teatros", description = "Obtiene una lista de todos los teatros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de teatros obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay teatros disponibles")
    })
    public ResponseEntity<List<Teatro>> listarTeatros() {
        List<Teatro> teatros = teatroService.findAll();
        if (teatros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teatros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar teatro por ID", description = "Obtiene un teatro específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teatro encontrado"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public ResponseEntity<Teatro> buscarTeatroPorId(@PathVariable Long id) {
        try {
            Teatro teatro = teatroService.findById(id);
            return ResponseEntity.ok(teatro);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar teatro", description = "Crea un nuevo teatro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teatro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Teatro> guardar(@RequestBody Teatro teatro) {
        Teatro teatroNuevo = teatroService.save(teatro);
        return ResponseEntity.status(HttpStatus.CREATED).body(teatroNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar teatro", description = "Actualiza un teatro existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teatro actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public ResponseEntity<Teatro> actualizar(@PathVariable Long id, @RequestBody Teatro teatro) {
        try {
            Teatro teatroActualizado = teatroService.updateTeatro(id, teatro);
            return ResponseEntity.ok(teatroActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente teatro", description = "Actualiza parcialmente un teatro existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teatro actualizado parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public ResponseEntity<Teatro> actualizarParcial(@PathVariable Long id, @RequestBody Teatro teatroParcial) {
        try {
            Teatro teatroActualizado = teatroService.patchTeatro(id, teatroParcial);
            return ResponseEntity.ok(teatroActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar teatro", description = "Elimina un teatro existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Teatro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            teatroService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
