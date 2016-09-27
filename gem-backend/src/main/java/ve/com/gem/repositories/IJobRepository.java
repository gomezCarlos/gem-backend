package ve.com.gem.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ve.com.gem.entities.Job;

@Repository
public interface IJobRepository extends PagingAndSortingRepository<Job, Long> {

	public List<Job> findJobByTaskId(Long id);

	Job findByName(String name);

	Job findOne(Long id);

	List<Job> findByNameLike(String name, Pageable pageable);
	List<Job> findByNameLike(String name);

	@SuppressWarnings("unchecked")
	Job save(Job object);

	public Page<Job> findByTaskId(Long id, Pageable pageable);

	public List<Job> findByTaskId(Long id);

}
