package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.galiana_project.cl.galiana_project.controller.V2.ComunaControllerV2;
import com.galiana_project.cl.galiana_project.model.Comuna;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ComunaModelAssembler implements RepresentationModelAssembler<Comuna, EntityModel<Comuna>> {

    @Override
    public EntityModel<Comuna> toModel(Comuna comuna) {
        return EntityModel.of(comuna,
                linkTo(methodOn(ComunaControllerV2.class).getComunaById(comuna.getId().longValue())).withSelfRel(),
                linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withRel("comunas"),
                linkTo(methodOn(ComunaControllerV2.class).updateComuna(comuna.getId().longValue(), comuna))
                        .withRel("actualizar"),
                linkTo(methodOn(ComunaControllerV2.class).deleteComuna(comuna.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(ComunaControllerV2.class).patchComuna(comuna.getId().longValue(), comuna))
                        .withRel("actualizar-parcial"));
    }
}
