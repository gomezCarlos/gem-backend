package ve.com.gem.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import ve.com.gem.entities.Chart;
import ve.com.gem.entities.Job;
import ve.com.gem.entities.Phase;
import ve.com.gem.entities.Project;
import ve.com.gem.entities.Task;
import ve.com.gem.entities.Tree;
import ve.com.gem.resources.PhaseResource;
import ve.com.gem.resources.ProjectResource;
import ve.com.gem.resources.assembler.PhaseResourceAssembler;
import ve.com.gem.resources.assembler.ProjectResourceAssembler;
import ve.com.gem.services.IJobService;
import ve.com.gem.services.IPhaseService;
import ve.com.gem.services.IProjectService;
import ve.com.gem.services.ITaskService;

@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {

	@Autowired
	private IProjectService service;
	
	@Autowired
	private IPhaseService phaseService;
	
	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IJobService jobService;

	@Autowired
	private ProjectResourceAssembler assembler;
	
	@Autowired
	private PhaseResourceAssembler phaseAssembler;

	@Autowired
	private PagedResourcesAssembler<Project> pageAssembler;
	
	@Autowired
	private PagedResourcesAssembler<Phase> pagePhaseAssembler;
	
	public ProjectController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/chart",method=RequestMethod.GET,produces="application/hal+json")
	@ResponseBody
	public Collection<Chart> chart() {

		//List<Project> projects = service.findAll();
		List<Project> projects = service.findTop4ByOrderByStartDateDesc();
		/*
		 * 
		 */
		for (Project project : projects) {
			project.setPhases(phaseService.findByProjectId(project.getId()));
			for (Phase phase : project.getPhases()) {
				phase.setTasks(taskService.findByPhaseId(phase.getId()));
				for (Task task : phase.getTasks()) {
					task.setJobs(jobService.findByTaskId(task.getId()));
				}
			}
			project.setValue(project.getAdvance().intValue());
			service.save(project);
		}
		/*
		 * 
		 */
		List<Chart> charts = new ArrayList<Chart>();
		for (Project project : projects) {
			Chart chart = new Chart();
			chart.setColor("rgba(255,255,255,0.8)");
			chart.setDescription(project.getName());
			chart.setStats(project.getAdvance().toString());
			chart.setIcon("refresh");
			chart.setId(project.getId());
			charts.add(chart);
		}
		
		return charts;
	}
	
	@RequestMapping(value="/quick")
	public Project quickProject(){
		Project project = null;
		project = service.findFirsByIsDefaultOrderByCreatedAt(true);
		if(null!=project)
			project.setPhases(phaseService.findByProjectId(project.getId()));
		
		return project;
	}
	
	@RequestMapping(value="/tree",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public Tree tree() {

		List<Project> projects = service.findAll();
		/*
		 * 
		 */
		for (Project project : projects) {
			project.setPhases(phaseService.findByProjectId(project.getId()));
			for (Phase phase : project.getPhases()) {
				phase.setTasks(taskService.findByPhaseId(phase.getId()));
				for (Task task : phase.getTasks()) {
					task.setJobs(jobService.findByTaskId(task.getId()));
				}
			}
			
		}
		/*
		 * 
		 */
		Tree tree = new Tree("Proyectos");
		for (Project project : projects) {
			Tree node = new Tree(project.getName());
			node.setId(project.getId());
			node.setType("project");
			for (Phase phase : project.getPhases()) {
				Tree nodePhase = new Tree(phase.getName());
				nodePhase.setId(phase.getId());
				nodePhase.setType("phase");
				for (Task task : phase.getTasks()) {
					Tree nodeTask = new Tree(task.getName());
					nodeTask.setId(task.getId());
					nodeTask.setType("task");
					for (Job job : task.getJobs()) {
						Tree nodeJob = new Tree(job.getName());
						nodeJob.setId(job.getId());
						nodeJob.setType("job");
						nodeTask.getChildren().add(nodeJob);
					}
					nodePhase.getChildren().add(nodeTask);
				}
				node.getChildren().add(nodePhase);
			}
			
			tree.getChildren().add(node);
		}
		
		return tree;
	}

	@RequestMapping(value="",method=RequestMethod.GET,produces="application/hal+json")
	@ResponseBody
	public PagedResources<ProjectResource> loadAll(Pageable pageable) {

		Page<Project> object = service.findAll(pageable);
		
		return pageAssembler.toResource(object, assembler);
	}
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<ProjectResource> load(@PathVariable Long id)
	{
		Project object = service.findById(id);
		if(null == object)
		{
			return new ResponseEntity<ProjectResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<ProjectResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	/**
	 * Find one gem.
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/advance",method=RequestMethod.GET)
	public ResponseEntity<ProjectResource> advance(@PathVariable Long id)
	{
		Project object = service.findById(id);
		if(null == object)
		{
			return new ResponseEntity<ProjectResource>(assembler.toResource(object),HttpStatus.NOT_FOUND);
		}
		else
		{
			object.setPhases(phaseService.findByProjectId(id));
			for (Phase phase : object.getPhases()) {
				phase.setTasks(taskService.findByPhaseId(phase.getId()));
				for (Task task : phase.getTasks()) {
					task.setJobs(jobService.findByTaskId(task.getId()));
				}
			}
			object.setValue(object.getAdvance().intValue());
			service.save(object);
			return new ResponseEntity<ProjectResource>(assembler.toResource(object),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="",method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<ProjectResource> save(@RequestBody Project object)
	{
		if(service.save(object)!=null)
		{
			return new ResponseEntity<ProjectResource>(assembler.toResource(object),HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<ProjectResource>(assembler.toResource(object),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT, produces = "application/json; charset=UTF-8")
	public ResponseEntity<ProjectResource> update(@PathVariable Long id,@RequestBody Project object)
	{
		Project search = service.findById(id);
		if(null == search)
		{
			return new ResponseEntity<ProjectResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
		}else
		
		if(null != object )
		{
			object.setId(id);
			service.save(object);
			return new ResponseEntity<ProjectResource>(assembler.toResource(search),HttpStatus.OK);
		}
		else
			return new ResponseEntity<ProjectResource>(assembler.toResource(search),HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE,produces = "application/json; charset=UTF-8")
	public ResponseEntity<Project> delete(@PathVariable Long id){
		Project search = service.findById(id);
		System.out.println(search);
		if(null != search)
		{
			service.delete(search);
			return new ResponseEntity<Project>(HttpStatus.OK);
		}
		else
		{
				return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/{id}/phases",method=RequestMethod.GET,produces="application/hal+json")
	@ResponseBody
	public PagedResources<PhaseResource> phases(@PathVariable Long id,Pageable pageable) {
		
		Project project = service.findById(id);

		Page<Phase> object = phaseService.findByProjectId(id,pageable);
		
		return pagePhaseAssembler.toResource(object, phaseAssembler);
	}
	
}
