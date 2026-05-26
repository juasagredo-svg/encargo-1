package com.galiana_project.cl.galiana_project.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.galiana_project.cl.galiana_project.controller.V2.ObraSalaControllerV2;
import com.galiana_project.cl.galiana_project.model.ObraSala;

@Component
public class ObraSalaModelAssembler implements RepresentationModelAssembler<ObraSala, EntityModel<ObraSala>> {

    @Override
    public EntityModel<ObraSala> toModel(ObraSala obraSala) {
        return EntityModel.of(obraSala,
                linkTo(methodOn(ObraSalaControllerV2.class).getObraSalaById(obraSala.getId().longValue()))
                        .withSelfRel(),
                linkTo(methodOn(ObraSalaControllerV2.class).getAllObrasSala()).withRel("obraSala"),
                linkTo(methodOn(ObraSalaControllerV2.class).updateObraSala(obraSala.getId().longValue(), obraSala))
                        .withRel("actualizar"),
                linkTo(methodOn(ObraSalaControllerV2.class).deleteObraSala(obraSala.getId().longValue()))
                        .withRel("eliminar"),
                linkTo(methodOn(ObraSalaControllerV2.class).patchObraSala(obraSala.getId().longValue(), obraSala))
                        .withRel("actualizar-parcial"));
    }
}
