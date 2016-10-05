package ve.com.gem.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.com.gem.entities.Event;
import ve.com.gem.entities.Job;
import ve.com.gem.entities.Phase;
import ve.com.gem.entities.Project;
import ve.com.gem.entities.Task;
import ve.com.gem.entities.Tree;
import ve.com.gem.services.IJobService;
import ve.com.gem.services.IPhaseService;
import ve.com.gem.services.IProjectService;
import ve.com.gem.services.ITaskService;

@RestController
@RequestMapping(value = "/api/v1/calendar")
public class CalendarController {
	
	@Autowired
	private IProjectService service;
	
	@Autowired
	private IPhaseService phaseService;
	
	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IJobService jobService;

	public CalendarController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/events",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public Collection<Event> events() {
		
		List<Event> events= new ArrayList<Event>();

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
		
		for (Project project : projects) {
			Event node = new Event(project.getName(),"project",project.getId());
			node.setStart(project.getEstimatedStartDate());
			node.setEnd(project.getEstimatedDateEnd());
			for (Phase phase : project.getPhases()) {
				Event nodePhase = new Event(phase.getName(),"phase",phase.getId());
				nodePhase.setStart(phase.getEstimatedStartDate());
				nodePhase.setEnd(phase.getEstimatedDateEnd());
				for (Task task : phase.getTasks()) {
					Event nodeTask = new Event(task.getName(),"task",task.getId());
					nodeTask.setStart(task.getEstimatedStartDate());
					nodeTask.setEnd(task.getEstimatedDateEnd());
					for (Job job : task.getJobs()) {
						Event nodeJob = new Event(job.getName(),"job",job.getId());
						nodeJob.setStart(new Date(job.getCreatedAt().getTime()));
						nodeJob.setEnd(new Date(job.getCreatedAt().getTime()));
						events.add(nodeJob);
					}
					events.add(nodeTask);
				}
				events.add(nodePhase);
			}
			events.add(node);
		}
		
		return events;
	}

}
