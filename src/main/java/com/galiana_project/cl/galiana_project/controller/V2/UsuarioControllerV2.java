package com.galiana_project.cl.galiana_project.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galiana_project.cl.galiana_project.assemblers.UsuarioModelAssembler;
import com.galiana_project.cl.galiana_project.model.Usuario;
import com.galiana_project.cl.galiana_project.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios v2", description = "Operaciones relacionadas con los usuarios")
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private UsuarioModelAssembler assembler;

        @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Listar usuarios", description = "Obtiene una lista de todos los usuarios")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
                        @ApiResponse(responseCode = "204", description = "No hay usuarios disponibles")
        })
        public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
                List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(usuarios,
                                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
        }

        @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario específico por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        public EntityModel<Usuario> getUsuarioById(@PathVariable Long id) {
                Usuario usuario = usuarioService.findById(id);
                return assembler.toModel(usuario);
        }

        @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public EntityModel<Usuario> createUsuario(@RequestBody Usuario usuario) {
                Usuario newUsuario = usuarioService.save(usuario);
                return assembler.toModel(newUsuario);
        }

        @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public EntityModel<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
                usuario.setId(id.intValue());
                Usuario updatedUsuario = usuarioService.save(usuario);
                return assembler.toModel(updatedUsuario);
        }

        @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Actualizar parcialmente usuario", description = "Actualiza parcialmente un usuario existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        })
        public EntityModel<Usuario> patchUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
                Usuario updatedUsuario = usuarioService.patchUsuario(id, usuario);
                return assembler.toModel(updatedUsuario);
        }

        @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
        @Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        public CollectionModel<EntityModel<Usuario>> deleteUsuario(@PathVariable Long id) {
                usuarioService.deleteById(id);
                List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(usuarios,
                                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
        }

        @GetMapping("/obra/{obraId}/teatro/{teatroId}")
        @Operation(summary = "Buscar usuarios por obra y teatro", description = "Obtiene una lista de usuarios asociados a una obra y teatro específicos")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
                        @ApiResponse(responseCode = "404", description = "No se encontraron usuarios para la obra y teatro especificados")
        })
        public CollectionModel<EntityModel<Usuario>> findUsuariosByObraAndTeatro(@PathVariable Long obraId,
                        @PathVariable Long teatroId) {
                List<EntityModel<Usuario>> usuarios = usuarioService.findUsuariosByObraAndTeatro(obraId, teatroId)
                                .stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(usuarios,
                                linkTo(methodOn(UsuarioControllerV2.class).findUsuariosByObraAndTeatro(obraId,
                                                teatroId)).withSelfRel());
        }

        @GetMapping("/metodoPago/{metodoPago}/obra/{obraId}")
        @Operation(summary = "Buscar usuarios por método de pago y obra", description = "Obtiene una lista de usuarios asociados a un método de pago y obra específicos")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
                        @ApiResponse(responseCode = "404", description = "No se encontraron usuarios para el método de pago y obra especificados")
        })
        public CollectionModel<EntityModel<Usuario>> findUsuariosByMetodoPagoAndObra(@PathVariable String metodoPago,
                        @PathVariable Long obraId) {
                List<EntityModel<Usuario>> usuarios = usuarioService.findUsuariosByMetodoPagoAndObra(metodoPago, obraId)
                                .stream()
                                .map(assembler::toModel)
                                .collect(Collectors.toList());
                return CollectionModel.of(usuarios,
                                linkTo(methodOn(UsuarioControllerV2.class).findUsuariosByMetodoPagoAndObra(metodoPago,
                                                obraId)).withSelfRel());
        }
}
