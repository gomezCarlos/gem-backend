package ve.com.gem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.com.gem.entities.Task;
import ve.com.gem.resources.TaskResource;
import ve.com.gem.resources.assembler.TaskResourceAssembler;
import ve.com.gem.services.IJobService;
import ve.com.gem.services.ITaskService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/tasks")
public class TaskController {

	@Autowired
	private ITaskService service;
	@Autowired
	IJobService jobService;

	@Autowired
	private TaskResourceAssembler assembler;

	@Autowired
	private PagedResourcesAssembler<Task> pageAssembler;
	
	
	public TaskController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * List all gems.
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.GET,produces="application/hal+json")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"task.list\")")
	@ResponseBody
	public PagedResources<TaskResource> loadAll(Pageable pageable){
		
		Page<Task> objects = service.findAll(pageable);
	
		return pageAssembler.toResource(objects, assembler);
	}
	
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"task.view\")")
	public ResponseEntity<TaskResource> load(@PathVariable Long id)
	{
		Task object = service.findById(id);
		if(null == object)
		{
			return new ResponseEntity<TaskResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<TaskResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/advance",method=RequestMethod.GET)
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"task.advance\")")
	public ResponseEntity<TaskResource> advance(@PathVariable Long id)
	{
		Task object = service.findById(id);
		
		if(null == object)
		{
			return new ResponseEntity<TaskResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			object.setJobs(jobService.findByTaskId(id));
			return new ResponseEntity<TaskResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="",method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"task.save\")")
	public ResponseEntity<TaskResource> save(@RequestBody Task object)
	{
		if(service.save(object)!=null)
		{
			return new ResponseEntity<TaskResource>(assembler.toResource(object),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<TaskResource>(assembler.toResource(object),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT, produces = "application/json; charset=UTF-8")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"task.update\")")
	public ResponseEntity<TaskResource> update(@PathVariable Long id,@RequestBody Task object)
	{
		Task search = service.findById(id);
		if(null == search)
		{
			return new ResponseEntity<TaskResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
		}else
		
		if(null != object )
		{
			object.setId(id);
			service.save(object);
			return new ResponseEntity<TaskResource>(assembler.toResource(search),HttpStatus.OK);
		}
		else
			return new ResponseEntity<TaskResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE,produces = "application/json; charset=UTF-8")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"task.delete\")")
	public ResponseEntity<Task> delete(@PathVariable Long id){
		Task search = service.findById(id);
		System.out.println(search);
		if(null != search)
		{
			service.delete(search);
			return new ResponseEntity<Task>(HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
