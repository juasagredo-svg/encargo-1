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

import com.galiana_project.cl.galiana_project.assemblers.AsientoModelAssembler;
import com.galiana_project.cl.galiana_project.model.Asiento;
import com.galiana_project.cl.galiana_project.service.AsientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Asientos v2", description = "Operaciones relacionadas con los asientos")
@RestController
@RequestMapping("/api/v2/asientos")
public class AsientoControllerV2 {

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private AsientoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar asientos", description = "Obtiene una lista de todos los asientos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asientos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay asientos disponibles")
    })
    public CollectionModel<EntityModel<Asiento>> getAllAsientos() {
        List<EntityModel<Asiento>> asientos = asientoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(asientos,
                linkTo(methodOn(AsientoControllerV2.class).getAllAsientos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar asiento por ID", description = "Obtiene un asiento específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public EntityModel<Asiento> getAsientoById(@PathVariable Long id) {
        Asiento asiento = asientoService.findById(id);
        return assembler.toModel(asiento);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear asiento", description = "Crea un nuevo asiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<Asiento>> createAsiento(@RequestBody Asiento asiento) {
        Asiento newAsiento = asientoService.save(asiento);
        return ResponseEntity
                .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId().longValue()))
                        .toUri())
                // .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId())).toUri())
                .body(assembler.toModel(newAsiento));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar asiento", description = "Actualiza un asiento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public ResponseEntity<EntityModel<Asiento>> updateAsiento(@PathVariable Long id, @RequestBody Asiento asiento) {
        asiento.setId(id.intValue());
        // asiento.setId(id);
        Asiento updatedAsiento = asientoService.save(asiento);
        return ResponseEntity
                .ok(assembler.toModel(updatedAsiento));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente un asiento", description = "Actualiza parcialmente un asiento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asiento actualizado parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public ResponseEntity<EntityModel<Asiento>> patchAsiento(@PathVariable Long id, @RequestBody Asiento asiento) {
        Asiento updatedAsiento = asientoService.patchAsiento(id, asiento);
        if (updatedAsiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedAsiento));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar asiento", description = "Elimina un asiento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asiento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Asiento no encontrado")
    })
    public ResponseEntity<?> deleteAsiento(@PathVariable Long id) {
        asientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
