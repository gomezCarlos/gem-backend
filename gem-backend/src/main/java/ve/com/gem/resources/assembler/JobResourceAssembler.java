package ve.com.gem.resources.assembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import ve.com.gem.controllers.JobController;
import ve.com.gem.controllers.TaskController;
import ve.com.gem.entities.Job;
import ve.com.gem.resources.JobResource;

@Component
public class JobResourceAssembler extends ResourceAssemblerSupport<Job, JobResource> {

	public JobResourceAssembler() {
		super(JobController.class, JobResource.class);
	}
	
	@Override
	public JobResource toResource(Job object) {
		JobResource resource = createResourceWithId(object.getId(), object);
		resource.setName(object.getName());
		resource.setDescription(object.getDescription());
		resource.setCreatedAt(object.getCreatedAt());
		resource.setUpdatedAt(object.getUpdatedAt());
		resource.setDeletedAt(object.getDeletedAt());
		resource.setIsActive(object.getIsActive());
		//jobResource.setTask(job.getTask());
		resource.setIds(object.getId());
		resource.setAdvance(object.getAdvance());
		if(object.getTask()!=null){
			resource.setTaskName(object.getTask().getName());
			resource.add(linkTo(TaskController.class).slash(object.getTask().getId()).withRel("task"));
		}
		
		resource.add(linkTo(JobController.class).slash(object.getId()).withSelfRel());
		return resource;
	}

}
