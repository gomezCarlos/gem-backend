package ve.com.gem.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ve.com.gem.entities.Job;
import ve.com.gem.entities.Task;

public interface IJobService {

	public Job save (Job job);
	
	public Page<Job> findAll(Pageable pageable);
	
	public Job findById (Long id);
	
	boolean delete(Job object);
	
	public Page<Job> findByTaskId(Long id, Pageable pageable);
}
