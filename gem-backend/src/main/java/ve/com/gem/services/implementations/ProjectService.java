package ve.com.gem.services.implementations;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;

import ve.com.gem.controllers.DocumentStateController;
import ve.com.gem.entities.DocumentState;
import ve.com.gem.entities.Project;
import ve.com.gem.repositories.IDocumentStateRepository;
import ve.com.gem.repositories.IProjectRepository;
import ve.com.gem.resources.DocumentStateResource;
import ve.com.gem.resources.TaskResource;
import ve.com.gem.services.IPhaseService;
import ve.com.gem.services.IProjectService;


@Transactional(readOnly = true)
@Service
public class ProjectService implements IProjectService {

	@Autowired
	private IProjectRepository repository;
	
	//@Autowired
	//private ITaskService taskService;
	@Autowired
	private IPhaseService phaseService;
	@Autowired
	private IDocumentStateRepository documentStateRepository;
	
	private List<Project> objects = new ArrayList<Project>();
	
	
	@Override
	@PreAuthorize("@projectEndPointAuthenticator.hasPermissionCustomizedForProjects(1)")
	public Page<Project> findAll(Pageable pageable) {
		objects = Lists.newArrayList(repository.findAll(pageable));
		PageImpl<Project> pages= new PageImpl<Project>(objects, pageable, repository.count());
		return pages;
	}
	
	public DocumentStateResource findDocumentStateFromProjectId(Long id) {
		
		DocumentState documentState = documentStateRepository.findOne(id);
		DocumentStateResource documentStateResource = new DocumentStateResource();
		documentStateResource.setName(documentState.getName());
		documentStateResource.setDescription(documentState.getDescription());
		documentStateResource.setCreatedAt(documentState.getCreatedAt());
		documentStateResource.setUpdatedAt(documentState.getUpdatedAt());
		documentStateResource.setDeletedAt(documentState.getDeletedAt());
		documentStateResource.setIds(documentState.getId());
		documentStateResource.add(linkTo(DocumentStateController.class).slash("").slash(documentState.getId()).withSelfRel());
	    documentStateResource.add(linkTo(DocumentStateController.class).slash("").slash(documentState.getId()).withRel("documentState"));
		return documentStateResource;
	}
	
	@Override
	@JsonIgnoreProperties(ignoreUnknown = true)
	public List<TaskResource> findTaskFromProject(Long id) {
		
		Project project = repository.findOne(id);
		List<TaskResource> taskResourceList = new ArrayList<TaskResource>();
		
//		for (Task task : tasks) {
//			TaskResource taskResource = new TaskResource();
//			taskResource.setName(task.getName());
//			taskResource.setDescription(task.getDescription());
//			taskResource.setCreatedAt(task.getCreatedAt());
//			taskResource.setUpdatedAt(task.getUpdatedAt());
//			taskResource.setDeletedAt(task.getDeletedAt());
//			taskResource.setIsActive(task.getIsActive());
//			taskResource.setRisk(task.getRisk());
//			taskResource.setDocumentState(taskService.findDocumentStateFromTaskId(task.getDocumentState().getId()));
//			taskResource.setEstimatedStartDate(task.getEstimatedStartDate());
//			taskResource.setStartDate(task.getStartDate());
//			taskResource.setEstimatedDateEnd(task.getEstimatedDateEnd());
//			taskResource.setDateEnd(task.getDateEnd());
//			taskResource.setIds(task.getId());
//			taskResource.setJob(taskService.findJobsFromTask(task.getId()));
//			taskResource.add(linkTo(TaskController.class).slash("").slash(task.getId()).withSelfRel());
//			taskResource.add(linkTo(TaskController.class).slash("").slash(task.getId()).withRel("task"));
//			
//			taskResourceList.add(taskResource);
//		}
		
		return taskResourceList;
	}

	@Transactional(readOnly = false)
	@Override
	public Project save(Project project) {
		if (null != project) {
			project.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
			
			//TEST
			project.setIsActive(true);
			
			repository.save(project);
			return project;
		}
		
		return null;
	}

	@Override
	public Project findById(Long id) {
		
		Project project = repository.findOne(id);
		project.setPhases(phaseService.findByProjectId(id));
		if (null != project) {
			return project;
		}
		return null;
	}

	@Transactional(readOnly=false)
	@Override
	public boolean delete(Project object) {
		Long id=0L;
		if(null != object){
			System.out.println("No es nula.");
			id=object.getId();
		}
		repository.delete(id);
		System.out.println(repository.exists(id));
		return !repository.exists(id);
	}

	@Override
	public List<Project> findAll() {
		
		return repository.findAll();
	}

}
