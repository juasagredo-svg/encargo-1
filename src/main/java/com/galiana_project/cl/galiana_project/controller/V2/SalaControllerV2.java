package com.galiana_project.cl.galiana_project.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

import com.galiana_project.cl.galiana_project.assemblers.SalaModelAssembler;
import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Salas v2", description = "Operaciones relacionadas con las salas")
@RestController
@RequestMapping("/api/v2/salas")
public class SalaControllerV2 {

    @Autowired
    private SalaService salaService;

    @Autowired
    private SalaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar salas", description = "Obtiene una lista de todas las salas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay salas disponibles")
    })
    public CollectionModel<EntityModel<Sala>> getAllSalas() {
        List<EntityModel<Sala>> salas = salaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(salas,
                linkTo(methodOn(SalaControllerV2.class).getAllSalas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar sala por ID", description = "Obtiene una sala específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala encontrada"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    public EntityModel<Sala> getSalaById(@PathVariable Long id) {
        Sala sala = salaService.findById(id);
        return assembler.toModel(sala);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva sala", description = "Crea una nueva sala")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sala creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<Sala>> createSala(@RequestBody Sala sala) {
        Sala newSala = salaService.save(sala);
        return ResponseEntity
                .created(linkTo(methodOn(SalaControllerV2.class).getSalaById(newSala.getId().longValue())).toUri())
                // .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId())).toUri())
                .body(assembler.toModel(newSala));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar sala", description = "Actualiza una sala existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<Sala>> updateSala(@PathVariable Long id, @RequestBody Sala sala) {
        sala.setId(id.intValue());
        // asiento.setId(id);
        Sala updatedSala = salaService.save(sala);
        return ResponseEntity
                .ok(assembler.toModel(updatedSala));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente sala", description = "Actualiza parcialmente una sala existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    public ResponseEntity<EntityModel<Sala>> patchSala(@PathVariable Long id, @RequestBody Sala sala) {
        Sala updatedSala = salaService.patchSala(id, sala);
        if (updatedSala == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedSala));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar sala", description = "Elimina una sala por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Sala no encontrada")
    })
    public ResponseEntity<?> deleteSala(@PathVariable Long id) {
        salaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
