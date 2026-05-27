package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.galiana_project.cl.galiana_project.controller.V2.TipoUsuarioControllerV2;
import com.galiana_project.cl.galiana_project.model.TipoUsuario;

@Component
public class TipoUsuarioModelAssembler implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>> {

    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario tipoUsuario) {
        return EntityModel.of(tipoUsuario,
                linkTo(methodOn(TipoUsuarioControllerV2.class).getTipoUsuarioById(tipoUsuario.getId().longValue()))
                        .withSelfRel(),
                linkTo(methodOn(TipoUsuarioControllerV2.class).getAllTiposUsuario()).withRel("tiposUsuario"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).updateTipoUsuario(tipoUsuario.getId().longValue(),
                        tipoUsuario)).withRel("actualizar"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).deleteTipoUsuario(tipoUsuario.getId().longValue()))
                        .withRel("eliminar"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).patchTipoUsuario(tipoUsuario.getId().longValue(),
                        tipoUsuario)).withRel("actualizar-parcial"));
    }
}
