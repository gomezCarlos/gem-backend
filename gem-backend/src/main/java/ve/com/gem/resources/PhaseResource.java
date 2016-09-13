package ve.com.gem.resources;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import ve.com.gem.entities.Department;
import ve.com.gem.entities.Indicator;
import ve.com.gem.entities.Project;


@Relation(collectionRelation = "phases")
public class PhaseResource extends ResourceSupport {

	private String name;
	private String description;
	private Long ids;
	private String projectName;
	private Float value;
	private String departmentName;
	@Column
	private Date estimatedStartDate;
	@Column
	private Date startDate;
	@Column
	private Date estimatedDateEnd;
	@Column
	private Date dateEnd;
	private String indicatorName;
	// @JsonManagedReference
	private Indicator indicator;
	
	
	private Float advance;
	
	// @JsonManagedReference
	private Department department;

	public Float getAdvance() {
		return advance;
	}

	public void setAdvance(Float advance) {
		this.advance = advance;
	}
	
	public Date getEstimatedStartDate() {
		return estimatedStartDate;
	}

	public void setEstimatedStartDate(Date estimatedStartDate) {
		this.estimatedStartDate = estimatedStartDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEstimatedDateEnd() {
		return estimatedDateEnd;
	}

	public void setEstimatedDateEnd(Date estimatedDateEnd) {
		this.estimatedDateEnd = estimatedDateEnd;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	private List<TaskResource> task = new ArrayList<TaskResource>();

	private Project project;
	

	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TaskResource> getTask() {
		return task;
	}
	
	public Project getProject() {
		return project;
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setTask(List<TaskResource> task) {
		this.task = task;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}

	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

}