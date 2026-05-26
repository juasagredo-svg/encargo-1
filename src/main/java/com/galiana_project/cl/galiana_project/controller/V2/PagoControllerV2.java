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

import com.galiana_project.cl.galiana_project.assemblers.PagoModelAssembler;
import com.galiana_project.cl.galiana_project.model.Pago;
import com.galiana_project.cl.galiana_project.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pagos v2", description = "Operaciones relacionadas con los pagos")
@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar pagos", description = "Obtiene una lista de todos los pagos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay pagos disponibles")
    })
    public CollectionModel<EntityModel<Pago>> getAllPagos() {
        List<EntityModel<Pago>> pagos = pagoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar pago por ID", description = "Obtiene un pago específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public EntityModel<Pago> getPagoById(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        return assembler.toModel(pago);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear pago", description = "Crea un nuevo pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<Pago>> createPago(@RequestBody Pago pago) {
        Pago newPago = pagoService.save(pago);
        return ResponseEntity
                .created(linkTo(methodOn(PagoControllerV2.class).getPagoById(newPago.getId().longValue())).toUri())
                // .created(linkTo(methodOn(AsientoControllerV2.class).getAsientoById(newAsiento.getId())).toUri())
                .body(assembler.toModel(newPago));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar pago", description = "Actualiza un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<EntityModel<Pago>> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        pago.setId(id.intValue());
        // asiento.setId(id);
        Pago updatedPago = pagoService.save(pago);
        return ResponseEntity
                .ok(assembler.toModel(updatedPago));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente un pago", description = "Actualiza parcialmente un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<EntityModel<Pago>> patchPago(@PathVariable Long id, @RequestBody Pago pago) {
        Pago updatedPago = pagoService.patchPago(id, pago);
        if (updatedPago == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedPago));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar pago", description = "Elimina un pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<?> deletePago(@PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
