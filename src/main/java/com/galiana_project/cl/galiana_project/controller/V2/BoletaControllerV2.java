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

import com.galiana_project.cl.galiana_project.assemblers.BoletaModelAssembler;
import com.galiana_project.cl.galiana_project.model.Boleta;
import com.galiana_project.cl.galiana_project.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Boletas v2", description = "Operaciones relacionadas con las boletas")
@RestController
@RequestMapping("/api/v2/boletas")
public class BoletaControllerV2 {

        @Autowired
        private BoletaService boletaService;

        @Autowired
        private BoletaModelAssembler assembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Listar boletas", description = "Obtiene una lista de todas las boletas")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de boletas obtenida exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay boletas disponibles")
        })
        public CollectionModel<EntityModel<Boleta>> getAllBoletas() {
                List<EntityModel<Boleta>> boletas = boletaService.findAll().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(boletas,
                                linkTo(methodOn(BoletaControllerV2.class).getAllBoletas()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar boleta por ID", description = "Obtiene una boleta específica por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Boleta encontrada"),
                        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
        })
        public EntityModel<Boleta> getBoletaById(@PathVariable Long id) {
                Boleta boleta = boletaService.findById(id);
                return assembler.toModel(boleta);
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear boleta", description = "Crea una nueva boleta")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Boleta creada exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public ResponseEntity<EntityModel<Boleta>> createBoleta(@RequestBody Boleta boleta) {
                Boleta newBoleta = boletaService.save(boleta);
                return ResponseEntity
                                .created(
                                                linkTo(methodOn(BoletaControllerV2.class)
                                                                .getBoletaById(newBoleta.getId().longValue())).toUri())
                                .body(assembler.toModel(newBoleta));
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar boleta", description = "Actualiza una boleta existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Boleta actualizada exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
        })
        public ResponseEntity<EntityModel<Boleta>> updateBoleta(@PathVariable Long id, @RequestBody Boleta boleta) {
                boleta.setId(id.intValue());
                Boleta updatedBoleta = boletaService.save(boleta);
                return ResponseEntity
                                .ok(assembler.toModel(updatedBoleta));
        }

        @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar parcialmente boleta", description = "Actualiza parcialmente una boleta existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Boleta actualizada parcialmente exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
        })
        public ResponseEntity<EntityModel<Boleta>> patchBoleta(@PathVariable Long id, @RequestBody Boleta boleta) {
                Boleta updatedBoleta = boletaService.patchBoleta(id, boleta);
                if (updatedBoleta == null) {
                        return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(assembler.toModel(updatedBoleta));
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar boleta", description = "Elimina una boleta por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Boleta eliminada exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Boleta no encontrada")
        })
        public ResponseEntity<?> deleteBoleta(@PathVariable Long id) {
                boletaService.deleteById(id);
                return ResponseEntity.noContent().build();
        }

        @GetMapping("/usuario/{usuarioId}/pago/{pagoId}")
        @Operation(summary = "Buscar boletas por usuario y pago", description = "Obtiene una lista de boletas filtradas por el ID del usuario y el ID del pago")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Boletas encontradas exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay boletas disponibles para los criterios especificados")
        })
        public CollectionModel<EntityModel<Boleta>> findByUsuario_IdAndPago_Id(@PathVariable Long usuarioId,
                        @PathVariable Long pagoId) {
                List<EntityModel<Boleta>> boletas = boletaService.findByUsuario_IdAndPago_Id(usuarioId, pagoId).stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(boletas,
                                linkTo(methodOn(BoletaControllerV2.class).findByUsuario_IdAndPago_Id(usuarioId, pagoId))
                                                .withSelfRel());
        }

        @GetMapping("/usuario/{usuarioId}/precio/{precioTotal}")
        @Operation(summary = "Buscar boletas por usuario y precio total", description = "Obtiene una lista de boletas filtradas por el ID del usuario y el precio total")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Boletas encontradas exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay boletas disponibles para los criterios especificados")
        })
        public CollectionModel<EntityModel<Boleta>> findByUsuario_IdAndPrecioTotal(@PathVariable Long usuarioId,
                        @PathVariable Integer precioTotal) {
                List<EntityModel<Boleta>> boletas = boletaService.findByUsuario_IdAndPrecioTotal(usuarioId, precioTotal)
                                .stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(boletas,
                                linkTo(methodOn(BoletaControllerV2.class).findByUsuario_IdAndPrecioTotal(usuarioId,
                                                precioTotal))
                                                .withSelfRel());
        }
}
