package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import ve.com.gem.controllers.JobController;
import ve.com.gem.controllers.ProjectController;
import ve.com.gem.entities.Project;
import ve.com.gem.resources.JobResource;
import ve.com.gem.resources.ProjectResource;

@Component
public class ProjectResourceAssembler extends ResourceAssemblerSupport<Project, ProjectResource> {

		
	public ProjectResourceAssembler() {
		super(ProjectController.class, ProjectResource.class);
	}

	@Override
	public ProjectResource toResource(Project object) {
		//ProjectResource resource = createResourceWithId(projects.getId(), projects);
		ProjectResource resource = new ProjectResource();
		resource.setName(object.getName());
		resource.setDescription(object.getDescription());
		resource.setCreatedAt(object.getCreatedAt());
		resource.setUpdatedAt(object.getUpdatedAt());
		resource.setDeletedAt(object.getDeletedAt());
		resource.setEstimatedStartDate(object.getEstimatedStartDate());
		resource.setStartDate(object.getStartDate());
		resource.setEstimatedDateEnd(object.getEstimatedDateEnd());
		resource.setIsActive(object.getIsActive());
		resource.setIds(object.getId());
		resource.setIndicator(object.getIndicator());
		resource.setAdvance(object.getAdvance());
		//MOISES		
		if(resource.getIndicator()!=null){
			resource.setIndicatorName(resource.getIndicator().getName());
			resource.add(linkTo(ProjectController.class).slash(resource.getId()).withRel("indicator"));
		}
		resource.add(linkTo(ProjectController.class).slash(object.getId()).withSelfRel());
		resource.add(linkTo(ProjectController.class).slash(object.getId()).slash("phases").withRel("phases"));
		return resource;
	}
	
	
}
