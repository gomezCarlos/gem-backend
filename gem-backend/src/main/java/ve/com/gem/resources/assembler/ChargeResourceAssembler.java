package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import ve.com.gem.controllers.ChargeController;
import ve.com.gem.entities.Charge;
import ve.com.gem.resources.ChargeResource;

@Component
public class ChargeResourceAssembler extends ResourceAssemblerSupport<Charge, ChargeResource> {

	public ChargeResourceAssembler() {
		super(ChargeController.class, ChargeResource.class);
	}

	@Override
	public ChargeResource toResource(Charge object) {
		ChargeResource resource = createResourceWithId(object.getId(), object);
		resource.setName(object.getName());
		resource.setDescription(object.getDescription());
		resource.setIsActive(object.getIsActive());
		resource.setIds(object.getId());
		//the resource link is being added automatically, so now it's commented.
		//resource.add(linkTo(ChargeController.class).slash(object.getId()).withSelfRel());
		return resource;
	}

	
}
