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

import com.galiana_project.cl.galiana_project.assemblers.ComunaModelAssembler;
import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.service.ComunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comunas v2", description = "Operaciones relacionadas con las comunas")
@RestController
@RequestMapping("/api/v2/comunas")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar comunas", description = "Obtiene una lista de todas las comunas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comunas obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay comunas disponibles")
    })
    public CollectionModel<EntityModel<Comuna>> getAllComunas() {
        List<EntityModel<Comuna>> comunas = comunaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(comunas,
                linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar comuna por ID", description = "Obtiene una comuna específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comuna encontrada"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public EntityModel<Comuna> getComunaById(@PathVariable Long id) {
        Comuna comuna = comunaService.findById(id);
        return assembler.toModel(comuna);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva comuna", description = "Crea una nueva comuna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comuna creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<Comuna>> createComuna(@RequestBody Comuna comuna) {
        Comuna newComuna = comunaService.save(comuna);
        return ResponseEntity
                .created(
                        linkTo(methodOn(ComunaControllerV2.class).getComunaById(newComuna.getId().longValue())).toUri())
                // .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId())).toUri())
                .body(assembler.toModel(newComuna));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar comuna", description = "Actualiza una comuna existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comuna actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<EntityModel<Comuna>> updateComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        comuna.setId(id.intValue());
        // asiento.setId(id);
        Comuna updatedComuna = comunaService.save(comuna);
        return ResponseEntity
                .ok(assembler.toModel(updatedComuna));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente comuna", description = "Actualiza parcialmente una comuna existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comuna actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<EntityModel<Comuna>> patchComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna updatedComuna = comunaService.patchComuna(id, comuna);
        if (updatedComuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedComuna));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comuna eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<?> deleteComuna(@PathVariable Long id) {
        comunaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
