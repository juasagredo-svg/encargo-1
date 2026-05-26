package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.galiana_project.cl.galiana_project.controller.V2.DirectorControllerV2;
import com.galiana_project.cl.galiana_project.model.Director;

@Component
public class DirectorModelAssembler implements RepresentationModelAssembler<Director, EntityModel<Director>> {

    @Override
    public EntityModel<Director> toModel(Director director) {
        return EntityModel.of(director,
                linkTo(methodOn(DirectorControllerV2.class).getDirectorById(director.getId().longValue()))
                        .withSelfRel(),
                linkTo(methodOn(DirectorControllerV2.class).getAllDirectores()).withRel("director"),
                linkTo(methodOn(DirectorControllerV2.class).updateDirector(director.getId().longValue(), director))
                        .withRel("actualizar"),
                linkTo(methodOn(DirectorControllerV2.class).deleteDirector(director.getId().longValue()))
                        .withRel("eliminar"),
                linkTo(methodOn(DirectorControllerV2.class).patchDirector(director.getId().longValue(), director))
                        .withRel("actualizar-parcial"));
    }
}
