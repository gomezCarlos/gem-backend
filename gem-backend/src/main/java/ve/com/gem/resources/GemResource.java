package ve.com.gem.resources;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "gems")
public class GemResource extends ResourceSupport {

	private String name;
	private String description;
	private Long ids;

	public GemResource() {
		// TODO Auto-generated constructor stub
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

	public Long getIds() {
		// return gemId;
		return ids;
	}

	public void setIds(Long ids) {
		// this.gemId = gemId;
		this.ids = ids;
	}

}
