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

import com.galiana_project.cl.galiana_project.assemblers.TeatroModelAssembler;
import com.galiana_project.cl.galiana_project.model.Teatro;
import com.galiana_project.cl.galiana_project.service.TeatroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Teatros v2", description = "Operaciones relacionadas con los teatros")
@RestController
@RequestMapping("/api/v2/teatros")
public class TeatroControllerV2 {

    @Autowired
    private TeatroService teatroService;

    @Autowired
    private TeatroModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar teatros", description = "Obtiene una lista de todos los teatros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de teatros obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay teatros disponibles")
    })
    public CollectionModel<EntityModel<Teatro>> getAllTeatros() {
        List<EntityModel<Teatro>> teatros = teatroService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(teatros,
                linkTo(methodOn(TeatroControllerV2.class).getAllTeatros()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar teatro por ID", description = "Obtiene un teatro específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teatro encontrado"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public EntityModel<Teatro> getTeatroById(@PathVariable Long id) {
        Teatro teatro = teatroService.findById(id);
        return assembler.toModel(teatro);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear teatro", description = "Crea un nuevo teatro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teatro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<Teatro>> createTeatro(@RequestBody Teatro teatro) {
        Teatro newTeatro = teatroService.save(teatro);
        return ResponseEntity
                .created(
                        linkTo(methodOn(TeatroControllerV2.class).getTeatroById(newTeatro.getId().longValue())).toUri())
                // .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId())).toUri())
                .body(assembler.toModel(newTeatro));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar teatro", description = "Actualiza un teatro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teatro actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public ResponseEntity<EntityModel<Teatro>> updateTeatro(@PathVariable Long id, @RequestBody Teatro teatro) {
        teatro.setId(id.intValue());
        // asiento.setId(id);
        Teatro updatedTeatro = teatroService.save(teatro);
        return ResponseEntity
                .ok(assembler.toModel(updatedTeatro));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente teatro", description = "Actualiza parcialmente un teatro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teatro actualizado parcialmente exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public ResponseEntity<EntityModel<Teatro>> patchTeatro(@PathVariable Long id, @RequestBody Teatro teatro) {
        Teatro updatedTeatro = teatroService.patchTeatro(id, teatro);
        if (updatedTeatro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedTeatro));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar teatro", description = "Elimina un teatro por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Teatro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Teatro no encontrado")
    })
    public ResponseEntity<?> deleteTeatro(@PathVariable Long id) {
        teatroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
