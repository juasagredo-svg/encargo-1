package com.galiana_project.cl.galiana_project.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.galiana_project.cl.galiana_project.controller.V2.BoletaControllerV2;
import com.galiana_project.cl.galiana_project.model.Boleta;

@Component
public class BoletaModelAssembler implements RepresentationModelAssembler<Boleta, EntityModel<Boleta>> {

    @Override
    public EntityModel<Boleta> toModel(Boleta boleta) {
        return EntityModel.of(boleta,
                linkTo(methodOn(BoletaControllerV2.class).getBoletaById(boleta.getId().longValue())).withSelfRel(),
                linkTo(methodOn(BoletaControllerV2.class).getAllBoletas()).withRel("boletas"),
                linkTo(methodOn(BoletaControllerV2.class).updateBoleta(boleta.getId().longValue(), boleta))
                        .withRel("actualizar"),
                linkTo(methodOn(BoletaControllerV2.class).deleteBoleta(boleta.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(BoletaControllerV2.class).patchBoleta(boleta.getId().longValue(), boleta))
                        .withRel("actualizar-parcial"));
    }
}
