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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galiana_project.cl.galiana_project.model.TipoUsuario;
import com.galiana_project.cl.galiana_project.service.TipoUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tipos de Usuario", description = "Operaciones relacionadas con los tipos de usuario")
@RestController
@RequestMapping("/api/v1/tiposUsuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @GetMapping
    @Operation(summary = "Listar tipos de usuario", description = "Obtiene una lista de todos los tipos de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de usuario obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay tipos de usuario disponibles")
    })
    public ResponseEntity<List<TipoUsuario>> listarTiposUsuario(@PathVariable Long id) {
        List<TipoUsuario> tiposUsuario = tipoUsuarioService.findAll();
        if (tiposUsuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tiposUsuario);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuario por ID", description = "Obtiene un tipo de usuario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<TipoUsuario> buscarTipoUsuarioPorId(@RequestParam Long id) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioService.findById(id);
            return ResponseEntity.ok(tipoUsuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo tipo de usuario", description = "Guarda un nuevo tipo de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<TipoUsuario> guardar(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario tipoUsuarioNuevo = tipoUsuarioService.save(tipoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuarioNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de usuario", description = "Actualiza un tipo de usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<TipoUsuario> actualizar(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario) {
        try {
            TipoUsuario tipoUsuarioActualizado = tipoUsuarioService.updateTipoUsuario(id, tipoUsuario);
            return ResponseEntity.ok(tipoUsuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente tipo de usuario", description = "Actualiza parcialmente un tipo de usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuario actualizado parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<TipoUsuario> patchTipoUsuario(@PathVariable Long id,
            @RequestBody TipoUsuario tipoUsuarioParcial) {
        try {
            TipoUsuario tipoUsuarioActualizado = tipoUsuarioService.patchTipoUsuario(id, tipoUsuarioParcial);
            return ResponseEntity.ok(tipoUsuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de usuario", description = "Elimina un tipo de usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            tipoUsuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
