package ve.com.gem.resources;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "valorations")
public class ValorationResource extends ResourceSupport {

	private Long ids;
	private String name;
	private String description;

	public ValorationResource() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the ids
	 */
	public Long getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(Long ids) {
		this.ids = ids;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

}
