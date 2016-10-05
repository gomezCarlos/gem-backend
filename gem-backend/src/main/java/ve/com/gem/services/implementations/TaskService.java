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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ve.com.gem.controllers.DocumentStateController;
import ve.com.gem.entities.DocumentState;
import ve.com.gem.entities.Job;
import ve.com.gem.entities.Phase;
import ve.com.gem.entities.Task;
import ve.com.gem.repositories.IDocumentStateRepository;
import ve.com.gem.repositories.IJobRepository;
import ve.com.gem.repositories.IPhaseRepository;
import ve.com.gem.repositories.ITaskRepository;
import ve.com.gem.resources.DocumentStateResource;
import ve.com.gem.resources.JobResource;
import ve.com.gem.resources.assembler.JobResourceAssembler;
import ve.com.gem.services.ITaskService;

@Transactional(readOnly = true)
@Service
public class TaskService implements ITaskService {

	@Autowired
	private IDocumentStateRepository documentStateRepository;
	
	@Autowired
	private ITaskRepository repository;
	
	@Autowired
	private IPhaseRepository phaseRepository;
	
	@Autowired
	private IJobRepository jobRepository;
	
	private List<Task> objects = new ArrayList<Task>();
	
	@Transactional(readOnly = false)
	@Override
	public Task save(Task task) {
		if (null != task) {
			task.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
			if(task.getPhase()!= null){
				Phase phase = phaseRepository.findOne(task.getPhase().getId());
				task.setPhase(phase);
				if(null != phase){
					try {
						if(task.getEstimatedStartDate().before(phase.getEstimatedStartDate()) || task.getEstimatedStartDate().after(phase.getEstimatedDateEnd())){
							task.setEstimatedStartDate(phase.getEstimatedStartDate());
						}
						if(task.getEstimatedDateEnd().before(phase.getEstimatedStartDate()) || task.getEstimatedDateEnd().after(phase.getEstimatedDateEnd())){
							task.setEstimatedDateEnd(phase.getEstimatedDateEnd());
						}
					} catch (Exception e) {
						System.out.println("Error: "+e.getMessage());
					}
				}
			//phase.getTask().add(task);
			phaseRepository.save(phase);
			}
			task.setIsActive(true);

			//TEST
			DocumentState documentState = documentStateRepository.findOne(1L);
			task.setDocumentState(documentState);
			
			return repository.save(task);
		}
		
		else {
			return null;
		}
	}

	@Override
	public Page<Task> findAll(Pageable pageable) {
		
		objects = Lists.newArrayList(repository.findAll(pageable));
		PageImpl<Task> taskPages = new PageImpl<Task>(objects, pageable, repository.count());
		return taskPages;
	}
	
	@Override
	public List<JobResource> findJobsFromTask(Long id) {
		
		Task task = repository.findOne(id);
		List<Job> jobs = task.getJobs();
		List<JobResource> jobResourceList = new ArrayList<JobResource>();
		
		for (Job job : jobs) {
			jobResourceList.add(new JobResourceAssembler().toResource(job));
		}
		return jobResourceList;
	}

	@Override
	public Page<Task> findByNameLike(Pageable pageable, String name) {
		
		objects = Lists.newArrayList(repository.findByNameLike(pageable, "%" + name + "%"));
		PageImpl<Task> taskPages = new PageImpl<>(objects, pageable, repository.count());
		return taskPages;
	}
	
	@Override
	public DocumentStateResource findDocumentStateFromTaskId(Long id) {
		
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
	public Task findById(Long id) {
		Task task = null;
		task= repository.findOne(id);
		task.setJobs(jobRepository.findByTaskId(id));
		return task;
		
	}
	@Transactional(readOnly=false)
	@Override
	public boolean delete(Task object) {
		Long id = object.getId();
		repository.delete(object);
		return repository.exists(id);
	}

	@Override
	public Page<Task> findByPhaseId(Long id,Pageable pageable) {
		
		return repository.findByPhaseId(id, pageable);
	}

	@Override
	public List<Task> findByPhaseId(Long id) {
		return repository.findByPhaseId(id);
	}

}
