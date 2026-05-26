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

import com.galiana_project.cl.galiana_project.assemblers.RegionModelAssembler;
import com.galiana_project.cl.galiana_project.model.Region;
import com.galiana_project.cl.galiana_project.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Regiones v2", description = "Operaciones relacionadas con las regiones")
@RestController
@RequestMapping("/api/v2/regiones")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar regiones", description = "Obtiene una lista de todas las regiones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de regiones obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay regiones disponibles")
    })
    public CollectionModel<EntityModel<Region>> getAllRegiones() {
        List<EntityModel<Region>> regiones = regionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(regiones,
                linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar región por ID", description = "Obtiene una región específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región encontrada"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public EntityModel<Region> getRegionById(@PathVariable Long id) {
        Region region = regionService.findById(id);
        return assembler.toModel(region);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva región", description = "Crea una nueva región")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Región creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<Region>> createRegion(@RequestBody Region region) {
        Region newRegion = regionService.save(region);
        return ResponseEntity
                .created(
                        linkTo(methodOn(RegionControllerV2.class).getRegionById(newRegion.getId().longValue())).toUri())
                // .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId())).toUri())
                .body(assembler.toModel(newRegion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar región", description = "Actualiza una región existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<Region>> updateRegion(@PathVariable Long id, @RequestBody Region region) {
        region.setId(id.intValue());
        // asiento.setId(id);
        Region updatedRegion = regionService.save(region);
        return ResponseEntity
                .ok(assembler.toModel(updatedRegion));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente región", description = "Actualiza parcialmente una región existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Región actualizada parcialmente exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<Region>> patchRegion(@PathVariable Long id, @RequestBody Region region) {
        Region updatedRegion = regionService.patchRegion(id, region);
        if (updatedRegion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedRegion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar región", description = "Elimina una región por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Región eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<?> deleteRegion(@PathVariable Long id) {
        regionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
