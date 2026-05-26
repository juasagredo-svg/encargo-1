package com.galiana_project.cl.galiana_project.controller.V2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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

import com.galiana_project.cl.galiana_project.assemblers.ObraSalaModelAssembler;
import com.galiana_project.cl.galiana_project.model.ObraSala;
import com.galiana_project.cl.galiana_project.service.ObraSalaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/obrasSala")
@Tag(name = "Obras Sala v2", description = "Operaciones relacionadas con las obras en sala")
public class ObraSalaControllerV2 {

    @Autowired
    private ObraSalaService obraSalaService;

    @Autowired
    private ObraSalaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar obras en sala", description = "Obtiene una lista de todas las obras en sala")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de obras en sala obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay obras en sala disponibles")
    })
    public CollectionModel<EntityModel<ObraSala>> getAllObrasSala() {
        List<EntityModel<ObraSala>> obrasSala = obraSalaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(obrasSala,
                linkTo(methodOn(ObraSalaControllerV2.class).getAllObrasSala()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener obra en sala por ID", description = "Devuelve una obra en sala específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra en sala encontrada"),
            @ApiResponse(responseCode = "404", description = "Obra en sala no encontrada")
    })
    public EntityModel<ObraSala> getObraSalaById(@PathVariable Long id) {
        ObraSala ObraSala = obraSalaService.findById(id);
        return assembler.toModel(ObraSala);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva obra en sala", description = "Crea una nueva obra en sala y devuelve su representación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra en sala creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<ObraSala>> createObraSala(@RequestBody ObraSala obraSala) {
        ObraSala newObraSala = obraSalaService.save(obraSala);
        return ResponseEntity
                .created(linkTo(methodOn(ObraSalaControllerV2.class).getObraSalaById(newObraSala.getId().longValue()))
                        .toUri())
                .body(assembler.toModel(newObraSala));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar obra en sala", description = "Actualiza una obra en sala existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra en sala actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra en sala no encontrada")
    })
    public ResponseEntity<EntityModel<ObraSala>> updateObraSala(@PathVariable Long id, @RequestBody ObraSala obraSala) {
        obraSala.setId(id.intValue());
        ObraSala updatedObraSala = obraSalaService.save(obraSala);
        return ResponseEntity
                .ok(assembler.toModel(updatedObraSala));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente obra en sala", description = "Actualiza parcialmente una obra en sala existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra en sala actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra en sala no encontrada")
    })
    public ResponseEntity<EntityModel<ObraSala>> patchObraSala(@PathVariable Long id, @RequestBody ObraSala obraSala) {
        ObraSala updatedObraSala = obraSalaService.patchObraSala(id, obraSala);
        if (updatedObraSala == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedObraSala));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar obra en sala", description = "Elimina una obra en sala por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra en sala eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Obra en sala no encontrada")
    })
    public ResponseEntity<?> deleteObraSala(@PathVariable Long id) {
        obraSalaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
