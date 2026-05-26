package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.galiana_project.cl.galiana_project.controller.V2.ObraControllerV2;
import com.galiana_project.cl.galiana_project.model.Obra;

@Component
public class ObraModelAssembler implements RepresentationModelAssembler<Obra, EntityModel<Obra>> {

    @Override
    public EntityModel<Obra> toModel(Obra obra) {
        return EntityModel.of(obra,
                linkTo(methodOn(ObraControllerV2.class).getObraById(obra.getId().longValue())).withSelfRel(),
                linkTo(methodOn(ObraControllerV2.class).getAllObras()).withRel("obras"),
                linkTo(methodOn(ObraControllerV2.class).updateObra(obra.getId().longValue(), obra))
                        .withRel("actualizar"),
                linkTo(methodOn(ObraControllerV2.class).deleteObra(obra.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(ObraControllerV2.class).patchObra(obra.getId().longValue(), obra))
                        .withRel("actualizar-parcial"));
    }
}
