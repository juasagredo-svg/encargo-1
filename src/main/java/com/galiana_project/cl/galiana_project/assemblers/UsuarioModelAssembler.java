package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.galiana_project.cl.galiana_project.controller.V2.UsuarioControllerV2;
import com.galiana_project.cl.galiana_project.model.Usuario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId().longValue())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(usuario.getId().longValue(), usuario))
                        .withRel("actualizar"),
                linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(usuario.getId().longValue()))
                        .withRel("eliminar"),
                linkTo(methodOn(UsuarioControllerV2.class).patchUsuario(usuario.getId().longValue(), usuario))
                        .withRel("actualizar-parcial"));
    }
}
