package com.galiana_project.cl.galiana_project.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.galiana_project.cl.galiana_project.controller.V2.AsientoBoletaControllerV2;
import com.galiana_project.cl.galiana_project.model.AsientoBoleta;

@Component
public class AsientoBoletaModelAssembler
        implements RepresentationModelAssembler<AsientoBoleta, EntityModel<AsientoBoleta>> {

    @Override
    public EntityModel<AsientoBoleta> toModel(AsientoBoleta asientoBoleta) {
        return EntityModel.of(asientoBoleta,
                linkTo(methodOn(AsientoBoletaControllerV2.class)
                        .getAsientoBoletaById(asientoBoleta.getId().longValue())).withSelfRel(),
                linkTo(methodOn(AsientoBoletaControllerV2.class).getAllAsientosBoleta()).withRel("asientoBoleta"),
                linkTo(methodOn(AsientoBoletaControllerV2.class).updateAsientoBoleta(asientoBoleta.getId().longValue(),
                        asientoBoleta)).withRel("actualizar"),
                linkTo(methodOn(AsientoBoletaControllerV2.class).deleteAsientoBoleta(asientoBoleta.getId().longValue()))
                        .withRel("eliminar"),
                linkTo(methodOn(AsientoBoletaControllerV2.class).patchAsientoBoleta(asientoBoleta.getId().longValue(),
                        asientoBoleta)).withRel("actualizar-parcial"));
    }
}
