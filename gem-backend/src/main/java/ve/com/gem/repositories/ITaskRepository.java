package ve.com.gem.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ve.com.gem.entities.Task;

@Repository
public interface ITaskRepository extends PagingAndSortingRepository<Task, Long> {

	public Page<Task> findByNameLike (Pageable pagable,String name);

	Task findByName(String name);
    
	Task findOne(Long id);
    
    List<Task> findByNameLike(String name, Pageable pageable);
    List<Task> findByNameLike(String name);

    @SuppressWarnings("unchecked")
    Task save (Task object);

	public Page<Task> findByPhaseId(Long id,Pageable pageable);

	public List<Task> findByPhaseId(Long id);

}
