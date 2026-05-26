package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.galiana_project.cl.galiana_project.controller.V2.SalaControllerV2;
import com.galiana_project.cl.galiana_project.model.Sala;

@Component
public class SalaModelAssembler implements RepresentationModelAssembler<Sala, EntityModel<Sala>> {

    @Override
    public EntityModel<Sala> toModel(Sala sala) {
        return EntityModel.of(sala,
                linkTo(methodOn(SalaControllerV2.class).getSalaById(sala.getId().longValue())).withSelfRel(),
                linkTo(methodOn(SalaControllerV2.class).getAllSalas()).withRel("salas"),
                linkTo(methodOn(SalaControllerV2.class).updateSala(sala.getId().longValue(), sala))
                        .withRel("actualizar"),
                linkTo(methodOn(SalaControllerV2.class).deleteSala(sala.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(SalaControllerV2.class).patchSala(sala.getId().longValue(), sala))
                        .withRel("actualizar-parcial"));

    }
}
