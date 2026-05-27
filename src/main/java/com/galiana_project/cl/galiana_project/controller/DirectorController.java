package com.galiana_project.cl.galiana_project.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.galiana_project.cl.galiana_project.model.Director;
import com.galiana_project.cl.galiana_project.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Directores", description = "Operaciones relacionadas con los directores")
@RestController
@RequestMapping("/api/v1/directores")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @GetMapping()
    @Operation(summary = "Listar directores", description = "Obtiene una lista de todos los directores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de directores obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay directores disponibles")
    })
    public ResponseEntity<List<Director>> listarDirectores() {
        List<Director> directores = directorService.findAll();
        if (directores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(directores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar director por ID", description = "Obtiene un director específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Director encontrado"),
            @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<Director> buscarDirectorPorId(@RequestParam Long id) {
        try {
            Director director = directorService.findById(id);
            return ResponseEntity.ok(director);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar director", description = "Crea un nuevo director")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Director creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Director> guardar(@RequestBody Director director) {
        Director directorNuevo = directorService.save(director);
        return ResponseEntity.status(HttpStatus.CREATED).body(directorNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar director", description = "Actualiza un director existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Director actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<Director> actualizar(@PathVariable Long id, @RequestBody Director director) {
        try {
            Director directorActualizado = directorService.updateDirector(id, director);
            return ResponseEntity.ok(directorActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente director", description = "Actualiza parcialmente un director existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Director actualizado parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<Director> actualizarParcial(@PathVariable Long id, @RequestBody Director directorParcial) {
        try {
            Director directorActualizado = directorService.patchDirector(id, directorParcial);
            return ResponseEntity.ok(directorActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar director", description = "Elimina un director por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Director eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Director no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            directorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombres/contiene/{nombres}/fechaNacimiento/inicio/{fechaInicio}/fin/{fechaFin}")
    @Operation(summary = "Buscar directores por nombres y fecha de nacimiento", description = "Obtiene una lista de directores que contienen los nombres especificados y cuya fecha de nacimiento está entre las fechas proporcionadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Directores encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron directores")
    })
    public ResponseEntity<List<Director>> findByNombresContainingAndFechaNacimiento(@PathVariable String nombres,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        List<Director> directores = directorService.findByNombresContainingAndFechaNacimientoBetween(nombres,
                fechaInicio, fechaFin);
        if (directores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(directores);
    }

    @GetMapping("/fechaNacimiento/inicio/{fechaInicio}/fin/{fechaFin}")
    @Operation(summary = "Buscar directores por fecha de nacimiento", description = "Obtiene una lista de directores cuya fecha de nacimiento está entre las fechas proporcionadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Directores encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron directores")
    })
    public ResponseEntity<List<Director>> findByNombresAndFechaNacimientoBetween(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        List<Director> directores = directorService.findByFechaNacimientoBetween(fechaInicio,
                fechaFin);
        if (directores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(directores);
    }

}
