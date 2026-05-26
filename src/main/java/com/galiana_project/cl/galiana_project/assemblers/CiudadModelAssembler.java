package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.galiana_project.cl.galiana_project.controller.V2.CiudadControllerV2;
import com.galiana_project.cl.galiana_project.model.Ciudad;

@Component
public class CiudadModelAssembler implements RepresentationModelAssembler<Ciudad, EntityModel<Ciudad>> {

    @Override
    public EntityModel<Ciudad> toModel(Ciudad ciudad) {
        return EntityModel.of(ciudad,
                linkTo(methodOn(CiudadControllerV2.class).getCiudadById(ciudad.getId().longValue())).withSelfRel(),
                linkTo(methodOn(CiudadControllerV2.class).getAllCiudades()).withRel("ciudades"),
                linkTo(methodOn(CiudadControllerV2.class).updateCiudad(ciudad.getId().longValue(), ciudad))
                        .withRel("actualizar"),
                linkTo(methodOn(CiudadControllerV2.class).deleteCiudad(ciudad.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(CiudadControllerV2.class).patchCiudad(ciudad.getId().longValue(), ciudad))
                        .withRel("actualizar-parcial"));
    }
}
