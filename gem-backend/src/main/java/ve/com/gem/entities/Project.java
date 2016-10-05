package ve.com.gem.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Project implements Measurable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private Timestamp createdAt;
	@Column
	private Timestamp updatedAt;
	@Column
	private Timestamp deletedAt;
	@Column
	private Boolean isActive;
	
	private Boolean isDefault;

	private Date estimatedStartDate;

	private Date startDate;

	private Date estimatedDateEnd;

	private Date dateEnd;
	
	@Column(nullable= true)
	private int value;
	
	@ManyToOne
	@JoinColumn(name = "indicator_id")
	private Indicator indicator;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	@ManyToMany
	private List<Department> related = new ArrayList<Department>();
		
	//@ManyToOne
	//@JoinColumn(name = "risk_id", nullable = true, insertable = true, updatable = true)
	//private Risk risk;
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	private String status;

	public Project(Long id) {
		this.id = id;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Phase> phases = new ArrayList<Phase>();
	
	@ManyToMany
	private List<Nature> natures = new ArrayList<Nature>();
	
	@ManyToMany
	private List<Account> responsible = new ArrayList<Account>();

	@ManyToOne
	private Account owner;

	
	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public Project() {
		super();
	}
		
	public Project(Long id, String name, String description, Timestamp createdAt, Timestamp updatedAt,
			Timestamp deletedAt, Boolean isActive, Date estimatedStartDate, Date startDate, Date estimatedDateEnd,
			Date dateEnd, int value, Indicator indicator, String status, List<Phase> phases, List<Nature> natures,
			List<Department> departments, List<Account> responsible) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.isActive = isActive;
		this.estimatedStartDate = estimatedStartDate;
		this.startDate = startDate;
		this.estimatedDateEnd = estimatedDateEnd;
		this.dateEnd = dateEnd;
		this.setValue(value);
		this.indicator = indicator;
		this.status = status;
		this.phases = phases;
		this.natures = natures;		
		this.responsible = responsible;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public List<Nature> getNatures() {
		return natures;
	}

	public void setNatures(List<Nature> natures) {
		this.natures = natures;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

/*
	public Risk getRisk() {
		return risk;
	}

	public void setRisk(Risk risk) {
		this.risk = risk;
	}
*/
		
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the responsible
	 */
	public List<Account> getResponsible() {
		return responsible;
	}

	/**
	 * @param responsible the responsible to set
	 */
	public void setResponsible(List<Account> responsible) {
		this.responsible = responsible;
	}

	public List<Department> getRelated() {
		return related;
	}

	public void setRelated(List<Department> related) {
		this.related = related;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", isActive=" + isActive
				+ ", estimatedStartDate=" + estimatedStartDate + ", startDate=" + startDate + ", estimatedDateEnd="
				+ estimatedDateEnd + ", dateEnd=" + dateEnd + ", value=" + value + ", indicator=" + indicator
				+ ", status=" + status + ", phases=" + phases + ", natures=" + natures + ", department=" + department
				+ ", responsible=" + responsible + "]";
	}

	/*
	 * Returns value of advance based on the average advance of all related phases.
	 * @see ve.com.gem.entities.Measurable#getAdvance()
	 */
	@Override
	public Float getAdvance() {
		Float advance = 0F;
		this.getPhases().size();
		for (Phase phase : getPhases()) {
			advance+= phase.getAdvance()*(phase.getPercentage()/100F);
			
			System.out.println("Phase "+phase.getName()+" is at: "+phase.getAdvance());
			System.out.println("Phase "+phase.getName()+" costs: "+phase.getPercentage());
		}
		System.out.println("Project "+this.getName()+" is at: "+advance);
		if(phases.size() != 0)
			return (advance/phases.size());
		else
			return 0F;
	}
}
