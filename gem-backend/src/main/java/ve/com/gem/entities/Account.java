package ve.com.gem.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String authorities;
	
	private Timestamp lastPasswordReset;
	
	private String firstname;
	
	private String lastname;
	
	@ManyToOne
	@JoinColumn(name = "department_id", nullable = true, insertable = true, updatable = true)
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "charge_id", nullable = true, insertable = true, updatable = true)
	private Charge charge;

	@Column
	private Timestamp createdAt;
	@Column
	private Timestamp updatedAt;
	@Column
	private Timestamp deletedAt;
	@Column
	private Boolean isActive;


	public Account(Long id, String username, String password, String authorities, Timestamp lastPasswordReset,
			String firstname, String lastname, Department department, Charge charge, Timestamp createdAt,
			Timestamp updatedAt, Timestamp deletedAt, Boolean isActive) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.lastPasswordReset = lastPasswordReset;
		this.firstname = firstname;
		this.lastname = lastname;
		this.department = department;
		this.charge = charge;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.isActive = isActive;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Charge getCharge() {
		return charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}

	public Account() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// @JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the createdAt
	 */
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the deletedAt
	 */
	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	/**
	 * @param deletedAt
	 *            the deletedAt to set
	 */
	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Timestamp getLastPasswordReset() {
		return lastPasswordReset;
	}

	public void setLastPasswordReset(Timestamp lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", authorities="
				+ authorities + ", lastPasswordReset=" + lastPasswordReset + ", firstname=" + firstname + ", lastname="
				+ lastname + ", department=" + department + ", charge=" + charge + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", isActive=" + isActive + "]";
	}

}
