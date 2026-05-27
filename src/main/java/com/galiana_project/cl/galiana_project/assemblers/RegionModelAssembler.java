package com.galiana_project.cl.galiana_project.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.galiana_project.cl.galiana_project.controller.V2.RegionControllerV2;
import com.galiana_project.cl.galiana_project.model.Region;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<Region, EntityModel<Region>> {
    @Override
    public EntityModel<Region> toModel(Region region) {
        return EntityModel.of(region,
                linkTo(methodOn(RegionControllerV2.class).getRegionById(region.getId().longValue())).withSelfRel(),
                linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withRel("regiones"),
                linkTo(methodOn(RegionControllerV2.class).updateRegion(region.getId().longValue(), region))
                        .withRel("actualizar"),
                linkTo(methodOn(RegionControllerV2.class).deleteRegion(region.getId().longValue())).withRel("eliminar"),
                linkTo(methodOn(RegionControllerV2.class).patchRegion(region.getId().longValue(), region))
                        .withRel("actualizar-parcial"));
    }
}
