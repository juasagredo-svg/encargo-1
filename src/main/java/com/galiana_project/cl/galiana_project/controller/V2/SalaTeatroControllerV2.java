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

import com.galiana_project.cl.galiana_project.assemblers.SalaTeatroModelAssembler;
import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.service.SalaTeatroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Salas teatro v2", description = "Operaciones relacionadas con la conexion entre salas y teatro")
@RestController
@RequestMapping("/api/v2/salasTeatro")
public class SalaTeatroControllerV2 {

        @Autowired
        private SalaTeatroService salaTeatroService;

        @Autowired
        private SalaTeatroModelAssembler assembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Listar sala teatro", description = "Obtiene una lista de todas las sala teatro")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de sala teatro obtenida exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay sala teatro disponibles")
        })
        public CollectionModel<EntityModel<SalaTeatro>> getAllObrasTeatro() {
                List<EntityModel<SalaTeatro>> obrasTeatro = salaTeatroService.findAll().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(obrasTeatro,
                                linkTo(methodOn(SalaTeatroControllerV2.class).getAllObrasTeatro()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar sala teatro por ID", description = "Obtiene una sala teatro específica por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Sala teatro encontrada"),
                        @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
        })
        public EntityModel<SalaTeatro> getsalaTeatroById(@PathVariable Long id) {
                SalaTeatro salaTeatro = salaTeatroService.findById(id);
                return assembler.toModel(salaTeatro);
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear sala teatro", description = "Crea una nueva sala teatro")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Sala teatro creada exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public ResponseEntity<EntityModel<SalaTeatro>> createsalaTeatro(@RequestBody SalaTeatro salaTeatro) {
                SalaTeatro newsalaTeatro = salaTeatroService.save(salaTeatro);
                return ResponseEntity
                                .created(linkTo(
                                                methodOn(SalaTeatroControllerV2.class)
                                                                .getsalaTeatroById(newsalaTeatro.getId().longValue()))
                                                .toUri())
                                // .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId())).toUri())
                                .body(assembler.toModel(newsalaTeatro));
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar sala teatro", description = "Actualiza una sala teatro existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Sala teatro actualizada exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
        })
        public ResponseEntity<EntityModel<SalaTeatro>> updatesalaTeatro(@PathVariable Long id,
                        @RequestBody SalaTeatro salaTeatro) {
                salaTeatro.setId(id.intValue());
                // asiento.setId(id);
                SalaTeatro updatedsalaTeatro = salaTeatroService.save(salaTeatro);
                return ResponseEntity
                                .ok(assembler.toModel(updatedsalaTeatro));
        }

        @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar parcialmente sala teatro", description = "Actualiza parcialmente una sala teatro existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Sala teatro actualizada parcialmente exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
        })
        public ResponseEntity<EntityModel<SalaTeatro>> patchsalaTeatro(@PathVariable Long id,
                        @RequestBody SalaTeatro salaTeatro) {
                SalaTeatro updatedsalaTeatro = salaTeatroService.patchsalaTeatro(id, salaTeatro);
                if (updatedsalaTeatro == null) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(assembler.toModel(updatedsalaTeatro));
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar sala teatro", description = "Elimina una sala teatro por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Sala teatro eliminada exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Sala teatro no encontrada")
        })
        public ResponseEntity<?> deletesalaTeatro(@PathVariable Long id) {
                salaTeatroService.deleteById(id);
                return ResponseEntity.noContent().build();
        }
}
