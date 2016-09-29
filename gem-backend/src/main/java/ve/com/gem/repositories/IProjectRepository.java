package ve.com.gem.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ve.com.gem.entities.Project;

@Repository
public interface IProjectRepository extends PagingAndSortingRepository<Project, Long> {

	Project findByName(String name);

	Project findOne(Long id);

	List<Project> findByNameLike(String name, Pageable pageable);
	List<Project> findByNameLike(String name);

	List<Project> findAll();
	@SuppressWarnings("unchecked")
	Project save(Project object);

}
