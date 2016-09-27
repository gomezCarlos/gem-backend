package ve.com.gem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.com.gem.entities.Phase;
import ve.com.gem.entities.Task;
import ve.com.gem.resources.PhaseResource;
import ve.com.gem.resources.TaskResource;
import ve.com.gem.resources.assembler.PhaseResourceAssembler;
import ve.com.gem.resources.assembler.TaskResourceAssembler;
import ve.com.gem.services.IJobService;
import ve.com.gem.services.IPhaseService;
import ve.com.gem.services.ITaskService;

@RestController
@RequestMapping(value = "/api/v1/phases")
public class PhaseController {

	@Autowired
	IPhaseService service;
	
	@Autowired
	ITaskService taskService;
	
	@Autowired
	IJobService jobService;
	
	@Autowired
	private PhaseResourceAssembler assembler;

	@Autowired
	private PagedResourcesAssembler<Phase> pageAssembler;
	
	@Autowired
	private TaskResourceAssembler taskAssembler;

	@Autowired
	private PagedResourcesAssembler<Task> taskPageAssembler;
	
	public PhaseController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * List all gems.
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.GET,produces="application/hal+json")
	@ResponseBody
	public PagedResources<PhaseResource> loadAll(Pageable pageable){
		
		Page<Phase> objects = service.findAll(pageable);
	
		return pageAssembler.toResource(objects, assembler);
	}
	
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<PhaseResource> load(@PathVariable Long id)
	{
		Phase object = service.findById(id);
		if(null == object)
		{
			return new ResponseEntity<PhaseResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<PhaseResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/advance",method=RequestMethod.GET)
	public ResponseEntity<PhaseResource> advance(@PathVariable Long id)
	{
		Phase object = service.findById(id);
		if(null == object)
		{
			return new ResponseEntity<PhaseResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			object.setTasks(taskService.findByPhaseId(id));
			for (Task task : object.getTasks()) {
				task.setJobs(jobService.findByTaskId(task.getId()));
			}
			object.setValue(object.getAdvance());
			service.save(object);
			return new ResponseEntity<PhaseResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="",method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<PhaseResource> save(@RequestBody Phase object)
	{
		if(service.save(object)!=null)
		{
			return new ResponseEntity<PhaseResource>(assembler.toResource(object),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<PhaseResource>(assembler.toResource(object),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT, produces = "application/json; charset=UTF-8")
	public ResponseEntity<PhaseResource> update(@PathVariable Long id,@RequestBody Phase object)
	{
		Phase search = service.findById(id);
		if(null == search)
		{
			return new ResponseEntity<PhaseResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
		}else
		
		if(null != object )
		{
			object.setId(id);
			service.save(object);
			return new ResponseEntity<PhaseResource>(assembler.toResource(search),HttpStatus.OK);
		}
		else
			return new ResponseEntity<PhaseResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE,produces = "application/json; charset=UTF-8")
	public ResponseEntity<Phase> delete(@PathVariable Long id){
		Phase search = service.findById(id);
		System.out.println(search);
		if(null != search)
		{
			service.delete(search);
			return new ResponseEntity<Phase>(HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<Phase>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/{id}/tasks", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	public PagedResources<TaskResource> tasks(@PathVariable Long id, Pageable pageable){
		Page<Task> tasks = taskService.findByPhaseId(id, pageable);
	return taskPageAssembler.toResource(tasks, taskAssembler);	
	}
	
	
}
