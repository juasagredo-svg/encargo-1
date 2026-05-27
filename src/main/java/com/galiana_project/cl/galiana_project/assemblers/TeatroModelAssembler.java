package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.galiana_project.cl.galiana_project.controller.V2.TeatroControllerV2;
import com.galiana_project.cl.galiana_project.model.Teatro;

@Component
public class TeatroModelAssembler implements RepresentationModelAssembler<Teatro, EntityModel<Teatro>> {

    @Override
    public EntityModel<Teatro> toModel(Teatro teatro) {
        return EntityModel.of(teatro,
                linkTo(methodOn(TeatroControllerV2.class).getTeatroById(teatro.getId().longValue())).withSelfRel(),
                linkTo(methodOn(TeatroControllerV2.class).getAllTeatros()).withRel("teatros"),
                linkTo(methodOn(TeatroControllerV2.class).updateTeatro(teatro.getId().longValue(), teatro))
                        .withRel("actualizar"),
                linkTo(methodOn(TeatroControllerV2.class).deleteTeatro(teatro.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(TeatroControllerV2.class).patchTeatro(teatro.getId().longValue(), teatro))
                        .withRel("actualizar-parcial"));
    }
}
