package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import ve.com.gem.controllers.AccountController;
import ve.com.gem.controllers.PhaseController;
import ve.com.gem.controllers.ProjectController;
import ve.com.gem.entities.Phase;
import ve.com.gem.resources.PhaseResource;
import ve.com.gem.services.IPhaseService;

@Component
public class PhaseResourceAssembler extends ResourceAssemblerSupport<Phase, PhaseResource> {
	
	public PhaseResourceAssembler() {
		super(PhaseController.class, PhaseResource.class);
	}

	@Override
	public PhaseResource toResource(Phase object) {
		PhaseResource resource = new PhaseResource();
		resource.setName(object.getName());
		resource.setDescription(object.getDescription());
		resource.setValue(object.getValue());
		resource.setDateEnd(object.getDateEnd());
		resource.setEstimatedDateEnd(object.getEstimatedDateEnd());
		resource.setStartDate(object.getStartDate());
		resource.setEstimatedStartDate(object.getEstimatedStartDate());
		resource.setIds(object.getId());
		//resource.setProject(phase.getProject());
		resource.setDepartment(object.getDepartment());
		resource.setAdvance(object.getAdvance());
		resource.add(linkTo(PhaseController.class).slash("").slash(object.getId()).withSelfRel());
		if(object.getProject()!=null){
			resource.setProjectName(object.getProject().getName());
			resource.add(linkTo(ProjectController.class).slash(object.getId()).withRel("project"));
		}
		if(object.getDepartment()!=null)
			resource.setDepartmentName(object.getDepartment().getName());
		resource.add(linkTo(PhaseController.class).slash(object.getId()).slash("tasks").withRel("tasks"));
		
		return resource;
		
	}
}
