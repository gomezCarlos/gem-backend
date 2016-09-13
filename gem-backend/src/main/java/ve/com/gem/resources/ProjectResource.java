package ve.com.gem.resources;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import ve.com.gem.entities.Department;
import ve.com.gem.entities.Indicator;

@Relation(collectionRelation = "projects")
public class ProjectResource extends ResourceSupport {

	private String name;

	private String description;

	private Date estimatedStartDate;

	private Date startDate;

	private Date estimatedDateEnd;

	private Date dateEnd;

	private Timestamp createdAt;

	private Timestamp updatedAt;

	private Timestamp deletedAt;

	private Boolean isActive;
	private String indicatorName;
	// @JsonManagedReference
	private Indicator indicator;
	
    
	private int value;
	
	private String departmentName;
	// @JsonManagedReference
	private Department department;
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

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

	
	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	

	

}