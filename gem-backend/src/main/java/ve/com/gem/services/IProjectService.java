package ve.com.gem.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ve.com.gem.entities.Project;
import ve.com.gem.resources.DocumentStateResource;
import ve.com.gem.resources.TaskResource;

public interface IProjectService {

	public Page<Project> findAll(Pageable pageable);
	
	public List<Project> findAll();
	
	public Project save(Project project);

	public List<TaskResource> findTaskFromProject(Long id);
	
	public DocumentStateResource findDocumentStateFromProjectId(Long id);
	
	public Project findById(Long id);
	
	public boolean delete(Project object);
	
}
