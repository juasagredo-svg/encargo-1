package com.galiana_project.cl.galiana_project.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galiana_project.cl.galiana_project.assemblers.ObraModelAssembler;
import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.service.ObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Obras v2", description = "Operaciones relacionadas con las obras")
@RestController
@RequestMapping("/api/v2/obras")
public class ObraControllerV2 {

        @Autowired
        private ObraService obraService;

        @Autowired
        private ObraModelAssembler assembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Listar obras", description = "Obtiene una lista de todas las obras")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de obras obtenida exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay obras disponibles")
        })
        public CollectionModel<EntityModel<Obra>> getAllObras() {
                List<EntityModel<Obra>> obras = obraService.findAll().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(obras,
                                linkTo(methodOn(ObraControllerV2.class).getAllObras()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar obra por ID", description = "Obtiene una obra específica por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Obra encontrada"),
                        @ApiResponse(responseCode = "404", description = "Obra no encontrada")
        })
        public EntityModel<Obra> getObraById(@PathVariable Long id) {
                Obra obra = obraService.findById(id);
                return assembler.toModel(obra);
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear obra", description = "Crea una nueva obra")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Obra creada exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public EntityModel<Obra> createObra(@RequestBody Obra obra) {
                Obra newObra = obraService.save(obra);
                return assembler.toModel(newObra);
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar obra", description = "Actualiza una obra existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Obra actualizada exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Obra no encontrada")
        })
        public EntityModel<Obra> updateObra(@PathVariable Long id, @RequestBody Obra obra) {
                obra.setId(id.intValue());
                Obra updatedObra = obraService.save(obra);
                return assembler.toModel(updatedObra);
        }

        @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar parcialmente obra", description = "Actualiza parcialmente una obra existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Obra actualizada parcialmente exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Obra no encontrada")
        })
        public EntityModel<Obra> patchObra(@PathVariable Long id, @RequestBody Obra obra) {
                Obra updatedObra = obraService.patchObra(id, obra);
                return assembler.toModel(updatedObra);
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar obra", description = "Elimina una obra por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Obra eliminada exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Obra no encontrada")
        })
        public CollectionModel<EntityModel<Obra>> deleteObra(@PathVariable Long id) {
                obraService.deleteById(id);
                List<EntityModel<Obra>> obras = obraService.findAll().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(obras,
                                linkTo(methodOn(ObraControllerV2.class).getAllObras()).withSelfRel());
        }

        @GetMapping("/teatro/{teatroId}/director/{directorId}")
        @Operation(summary = "Obras por teatro y director", description = "Obtiene las obras de un teatro y director específicos")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Obras encontradas exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay obras disponibles para el teatro y director")
        })
        public CollectionModel<EntityModel<Obra>> listarObrasDeTeatroDirector(
                        @PathVariable Long teatroId,
                        @PathVariable Long directorId) {
                List<EntityModel<Obra>> obras = obraService.findObrasDeTeatroDirector(teatroId, directorId).stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(obras,
                                linkTo(methodOn(ObraControllerV2.class).listarObrasDeTeatroDirector(teatroId,
                                                directorId)).withSelfRel());
        }

        @GetMapping("/teatro/{teatroId}/comuna/{comunaId}")
        @Operation(summary = "Obras por teatro y comuna", description = "Obtiene las obras de un teatro y comuna específicos")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Obras encontradas exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay obras disponibles para el teatro y comuna")
        })
        public CollectionModel<EntityModel<Obra>> listarObrasDeTeatroComuna(
                        @PathVariable Long teatroId,
                        @PathVariable Long comunaId) {
                List<EntityModel<Obra>> obras = obraService.findObrasPorTeatroYComuna(teatroId, comunaId).stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(obras,
                                linkTo(methodOn(ObraControllerV2.class).listarObrasDeTeatroComuna(teatroId,
                                                comunaId)).withSelfRel());
        }
}
