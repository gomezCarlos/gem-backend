package ve.com.gem.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ve.com.gem.entities.Phase;
import ve.com.gem.resources.DocumentStateResource;
import ve.com.gem.resources.ProjectResource;
import ve.com.gem.resources.TaskResource;

public interface IPhaseService {

	public Page<Phase> findAll(Pageable pageable);
	
	public Phase save(Phase phase);

	public List<TaskResource> findTaskFromPhase(Long id);
	
	public List<ProjectResource> findProjectFromPhase(Long id);
	
	public DocumentStateResource findDocumentStateFromPhaseId(Long id);
	
	public Phase findById(Long id);

	boolean delete(Phase phase);

	Page<Phase> findAll(Sort sort);

	void addByName(String name);

	List<Phase> search(String key);
	
	Page<Phase> findAll(List<Phase> objects,Pageable pageable);
	
	Page<Phase> findByProjectId(Long id, Pageable pageable);
	
}
