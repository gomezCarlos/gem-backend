package ve.com.gem.resources;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Relation(collectionRelation = "jobs")
public class JobResource extends ResourceSupport {

	private String name;

	private String description;

	private Timestamp createdAt;

	private Timestamp updatedAt;

	private Timestamp deletedAt;

	private Boolean isActive;
    
	private String taskName;
	// @JsonManagedReference
	private TaskResource task;

	private Long ids;
	
	private Float advance;

	public Float getAdvance() {
		return advance;
	}

	public void setAdvance(Float advance) {
		this.advance = advance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@JsonIgnore
	public TaskResource getTask() {
		return task;
	}

	public void setTask(TaskResource task) {
		this.task = task;
	}

	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
