package ve.com.gem.resources.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import ve.com.gem.controllers.JobController;
import ve.com.gem.controllers.ProjectController;
import ve.com.gem.controllers.TaskController;
import ve.com.gem.entities.Task;
import ve.com.gem.resources.TaskResource;
import ve.com.gem.services.ITaskService;

@Component
public class TaskResourceAssembler extends ResourceAssemblerSupport<Task, TaskResource>{

	@Autowired
	private ITaskService taskService;

	public TaskResourceAssembler () {
		super(TaskController.class, TaskResource.class);
	}
	
	@Override
	public TaskResource toResource(Task object) {
		TaskResource resource = new TaskResource();
		resource.setName(object.getName());
		resource.setDescription(object.getDescription());
		resource.setCreatedAt(object.getCreatedAt());
		resource.setUpdatedAt(object.getUpdatedAt());
		resource.setDeletedAt(object.getDeletedAt());
		resource.setIsActive(object.getIsActive());
		if(object.getDocumentState()!=null)
			resource.setDocumentState(taskService.findDocumentStateFromTaskId(object.getDocumentState().getId()));
		resource.setEstimatedStartDate(object.getEstimatedStartDate());
		resource.setStartDate(object.getStartDate());
		resource.setEstimatedDateEnd(object.getEstimatedDateEnd());
		resource.setDateEnd(object.getDateEnd());
		resource.setIds(object.getId());
		resource.setAdvance(object.getAdvance());
		//MOISES desde aqui 22-08-2016
		if(object.getPhase()!=null){
			resource.setPhaseName(object.getPhase().getName());
			resource.add(linkTo(TaskController.class).slash(object.getId()).withRel("phase"));
		}
		//MOISES hasta aqui 22-08-2016
		resource.add(linkTo(TaskController.class).slash("").slash(object.getId()).withSelfRel());
		resource.add(linkTo(ProjectController.class).slash(object.getId()).slash("projects").withRel("projects"));
		resource.add(linkTo(TaskController.class).slash(object.getId()).slash("jobs").withRel("jobs"));
		return resource;
	}

}
