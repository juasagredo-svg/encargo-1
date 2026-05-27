package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.galiana_project.cl.galiana_project.controller.V2.AsientoControllerV2;
import com.galiana_project.cl.galiana_project.model.Asiento;

@Component
public class AsientoModelAssembler implements RepresentationModelAssembler<Asiento, EntityModel<Asiento>> {

    @Override
    public EntityModel<Asiento> toModel(Asiento asiento) {
        return EntityModel.of(asiento,
                linkTo(methodOn(AsientoControllerV2.class).getAsientoById(asiento.getId().longValue())).withSelfRel(),
                linkTo(methodOn(AsientoControllerV2.class).getAllAsientos()).withRel("asientos"),
                linkTo(methodOn(AsientoControllerV2.class).updateAsiento(asiento.getId().longValue(), asiento))
                        .withRel("actualizar"),
                linkTo(methodOn(AsientoControllerV2.class).deleteAsiento(asiento.getId().longValue()))
                        .withRel("eliminar"),
                linkTo(methodOn(AsientoControllerV2.class).patchAsiento(asiento.getId().longValue(), asiento))
                        .withRel("actualizar-parcial"));
    }
}
