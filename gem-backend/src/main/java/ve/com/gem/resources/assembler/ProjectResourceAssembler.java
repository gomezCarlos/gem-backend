package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import ve.com.gem.controllers.ProjectController;
import ve.com.gem.entities.Project;
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
		resource.setDepartment(object.getDepartment());
		resource.setAdvance(object.getAdvance());
		
		resource.setValue(object.getValue());
		//MOISES		
		if(resource.getIndicator()!=null){
			resource.setIndicatorName(resource.getIndicator().getName());
			resource.add(linkTo(ProjectController.class).slash(resource.getId()).withRel("indicator"));
		}
		//MOISES		
				if(resource.getDepartment()!=null){
					resource.setDepartmentName(resource.getDepartment().getName());
					resource.add(linkTo(ProjectController.class).slash(resource.getId()).withRel("department"));
				}
		resource.add(linkTo(ProjectController.class).slash(object.getId()).withSelfRel());
		resource.add(linkTo(ProjectController.class).slash(object.getId()).slash("phases").withRel("phases"));
		
		resource.add(linkTo(ProjectController.class).slash(object.getId()).slash("responsibles").withRel("responsibles"));
		return resource;
	}
	
	
}
