package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.galiana_project.cl.galiana_project.controller.V2.PagoControllerV2;
import com.galiana_project.cl.galiana_project.model.Pago;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).getPagoById(Long.valueOf(pago.getId()))).withSelfRel(), // modo
                                                                                                                // del
                                                                                                                // profe
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withRel("pagos"),
                linkTo(methodOn(PagoControllerV2.class).updatePago(pago.getId().longValue(), pago))
                        .withRel("actualizar"),
                linkTo(methodOn(PagoControllerV2.class).deletePago(pago.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(PagoControllerV2.class).patchPago(pago.getId().longValue(), pago))
                        .withRel("actualizar-parcial"));
    }
}
