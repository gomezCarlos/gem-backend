package ve.com.gem.services.implementations;

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

import ve.com.gem.entities.Job;
import ve.com.gem.entities.Task;
import ve.com.gem.repositories.IJobRepository;
import ve.com.gem.repositories.ITaskRepository;
import ve.com.gem.services.IJobService;

@Transactional(readOnly = true)
@Service
public class JobService implements IJobService {
	
	
	@Autowired
	private IJobRepository repository;
	
	@Autowired
	private ITaskRepository taskRepository;
	
	private List<Job> objects = new ArrayList<Job>();

	
	@Transactional(readOnly = false)
	@Override
	public Job save(Job job) {
		
		if (null != job) {
			job.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
			if(job.getTask() !=null){
			Task task = taskRepository.findOne(job.getTask().getId());
			job.setTask(task);
			taskRepository.save(task);
			}
			job.setIsActive(true);
			return repository.save(job);
		}
		
		else
			return null;
	}

	@Override
	public Page<Job> findAll(Pageable pageable) {
		
		objects = Lists.newArrayList(repository.findAll(pageable));
		PageImpl<Job> jobPages = new PageImpl<>(objects, pageable, repository.count());
		return jobPages;
	}

	@Override
	public Job findById(Long id) {
		
		if (null != id)
			return repository.findOne(id);
		
		else
			return null;
	}

	@Transactional(readOnly = false)
	@Override
	public boolean delete(Job object) {
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
	public Page<Job> findByTaskId(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByTaskId(id, pageable);
	}

	@Override
	public List<Job> findByTaskId(Long id) {
		
		return repository.findByTaskId(id);
	}


}
