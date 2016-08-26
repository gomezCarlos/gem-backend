package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import ve.com.gem.controllers.DepartmentController;
import ve.com.gem.entities.Department;
import ve.com.gem.resources.DepartmentResource;

@Component
public class DepartmentResourceAssembler extends ResourceAssemblerSupport<Department, DepartmentResource>{

	public DepartmentResourceAssembler() {
		super(DepartmentController.class, DepartmentResource.class);
	}
	
	@Override
	public DepartmentResource toResource(Department object) {
		DepartmentResource resource = createResourceWithId(object.getId(), object);
		resource.setName(object.getName());
		resource.setDescription(object.getDescription());
		resource.setIds(object.getId());
		//the resource link is being added automatically, so now it's commented.
		//resource.add(linkTo(DepartmentController.class).slash(object.getId()).withSelfRel());
		return resource;
	}	
}
