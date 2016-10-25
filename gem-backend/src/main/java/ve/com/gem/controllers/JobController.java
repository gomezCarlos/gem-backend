package ve.com.gem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import ve.com.gem.entities.Job;
import ve.com.gem.resources.JobResource;
import ve.com.gem.resources.assembler.JobResourceAssembler;
import ve.com.gem.services.IJobService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/jobs")
public class JobController {

	@Autowired
	private IJobService service;

	@Autowired
	private JobResourceAssembler assembler;

	@Autowired
	private PagedResourcesAssembler<Job> pageAssembler;

	public JobController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * List all gems.
	 * @return
	 */
	@RequestMapping(value="",method=RequestMethod.GET,produces="application/hal+json")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"job.list\")")
	@ResponseBody
	public PagedResources<JobResource> loadAll(Pageable pageable){
		
		Page<Job> objects = service.findAll(pageable);
	
		return pageAssembler.toResource(objects, assembler);
	}
	
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"job.view\")")
	public ResponseEntity<JobResource> load(@PathVariable Long id)
	{
		Job object = service.findById(id);
		if(null == object)
		{
			return new ResponseEntity<JobResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<JobResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="",method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"job.save\")")
	public ResponseEntity<JobResource> save(@RequestBody Job object)
	{
		if(service.save(object)!=null)
		{
			return new ResponseEntity<JobResource>(assembler.toResource(object),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<JobResource>(assembler.toResource(object),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT, produces = "application/json; charset=UTF-8")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"job.update\")")
	public ResponseEntity<JobResource> update(@PathVariable Long id,@RequestBody Job object)
	{
		Job search = service.findById(id);
		if(null == search)
		{
			return new ResponseEntity<JobResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
		}else
		
		if(null != object )
		{
			object.setId(id);
			service.save(object);
			return new ResponseEntity<JobResource>(assembler.toResource(search),HttpStatus.OK);
		}
		else
			return new ResponseEntity<JobResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE,produces = "application/json; charset=UTF-8")
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(\"job.delete\")")
	public ResponseEntity<Job> delete(@PathVariable Long id){
		Job search = service.findById(id);
		System.out.println(search);
		if(null != search)
		{
			service.delete(search);
			return new ResponseEntity<Job>(HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<Job>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
