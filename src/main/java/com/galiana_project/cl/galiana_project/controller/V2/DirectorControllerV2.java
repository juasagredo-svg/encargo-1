package com.galiana_project.cl.galiana_project.controller.V2;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.galiana_project.cl.galiana_project.assemblers.DirectorModelAssembler;
import com.galiana_project.cl.galiana_project.model.Director;
import com.galiana_project.cl.galiana_project.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Directores v2", description = "Operaciones relacionadas con los directores")
@RestController
@RequestMapping("/api/v2/directores")
public class DirectorControllerV2 {

        @Autowired
        private DirectorService directorService;

        @Autowired
        private DirectorModelAssembler assembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Listar directores", description = "Obtiene una lista de todos los directores")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de directores obtenida exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay directores disponibles")
        })
        public CollectionModel<EntityModel<Director>> getAllDirectores() {
                List<EntityModel<Director>> directores = directorService.findAll().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(directores,
                                linkTo(methodOn(DirectorControllerV2.class).getAllDirectores()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar director por ID", description = "Obtiene un director específico por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Director encontrado"),
                        @ApiResponse(responseCode = "404", description = "Director no encontrado")
        })
        public EntityModel<Director> getDirectorById(@PathVariable Long id) {
                Director director = directorService.findById(id);
                return assembler.toModel(director);
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear director", description = "Crea un nuevo director")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Director creado exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public ResponseEntity<EntityModel<Director>> createDirector(@RequestBody Director director) {
                Director newDirector = directorService.save(director);
                return ResponseEntity
                                .created(linkTo(methodOn(DirectorControllerV2.class)
                                                .getDirectorById(newDirector.getId().longValue()))
                                                .toUri())
                                .body(assembler.toModel(newDirector));
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar director", description = "Actualiza un director existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Director actualizado exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                        @ApiResponse(responseCode = "404", description = "Director no encontrado")
        })
        public ResponseEntity<EntityModel<Director>> updateDirector(@PathVariable Long id,
                        @RequestBody Director director) {
                director.setId(id.intValue());
                // asiento.setId(id);
                Director updatedDirector = directorService.save(director);
                return ResponseEntity
                                .ok(assembler.toModel(updatedDirector));
        }

        @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar parcialmente director", description = "Actualiza parcialmente un director existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Director actualizado parcialmente exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Director no encontrado")
        })
        public ResponseEntity<EntityModel<Director>> patchDirector(@PathVariable Long id,
                        @RequestBody Director director) {
                Director updatedDirector = directorService.patchDirector(id, director);
                if (updatedDirector == null) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(assembler.toModel(updatedDirector));
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar director", description = "Elimina un director por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Director eliminado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Director no encontrado")
        })
        public EntityModel<Director> deleteDirector(@PathVariable Long id) {
                Director director = directorService.findById(id);
                directorService.deleteById(id);
                return assembler.toModel(director);
        }

        @GetMapping(value = "/nombres/contiene/{nombres}/fechaNacimiento/inicio/{fechaInicio}/fin/{fechaFin}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar directores por nombres y fecha de nacimiento", description = "Obtiene directores cuyos nombres contienen una cadena específica y cuya fecha de nacimiento está entre dos fechas")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Directores encontrados"),
                        @ApiResponse(responseCode = "204", description = "No se encontraron directores con los criterios especificados")
        })
        public CollectionModel<EntityModel<Director>> findByNombresContainingAndFechaNacimiento(
                        @PathVariable String nombres,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
                List<EntityModel<Director>> directores = directorService
                                .findByNombresContainingAndFechaNacimientoBetween(nombres,
                                                fechaInicio, fechaFin)
                                .stream().map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(directores,
                                linkTo(methodOn(DirectorControllerV2.class).findByNombresContainingAndFechaNacimiento(
                                                nombres, fechaInicio, fechaFin))
                                                .withSelfRel());
        }

        @GetMapping("/fechaNacimiento/inicio/{fechaInicio}/fin/{fechaFin}")
        @Operation(summary = "Buscar directores por fecha de nacimiento", description = "Obtiene directores cuya fecha de nacimiento está entre dos fechas")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Directores encontrados"),
                        @ApiResponse(responseCode = "204", description = "No se encontraron directores con los criterios especificados")
        })
        public CollectionModel<EntityModel<Director>> findByNombresAndFechaNacimientoBetween(
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
                List<EntityModel<Director>> directores = directorService
                                .findByFechaNacimientoBetween(
                                                fechaInicio, fechaFin)
                                .stream().map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(directores,
                                linkTo(methodOn(DirectorControllerV2.class).findByNombresAndFechaNacimientoBetween(
                                                fechaInicio, fechaFin))
                                                .withSelfRel());
        }
}
