package ve.com.gem.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ve.com.gem.entities.Task;
import ve.com.gem.resources.DocumentStateResource;
import ve.com.gem.resources.JobResource;

public interface ITaskService {

	public Task save (Task task);
	
	public Page<Task> findAll (Pageable pageable);
	
	public Page<Task> findByNameLike(Pageable pageable, String name);
	
	public Task findById (Long id);

	public List<JobResource> findJobsFromTask(Long id);
	
	public DocumentStateResource findDocumentStateFromTaskId(Long id);
	
	public boolean delete(Task object);
	
	public Page<Task> findByPhaseId(Long id, Pageable pageable);
	
}
