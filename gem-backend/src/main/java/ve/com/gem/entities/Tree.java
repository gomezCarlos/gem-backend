package ve.com.gem.entities;

import java.util.ArrayList;
import java.util.List;

public class Tree {

	//extended attributes
	private Long id;
	private String type="tree";
	//Default attributes
	private String value;
	private List<Tree> children = new ArrayList<Tree>();
	
	public Tree() {
		super();
		this.setId(0L);
		this.setValue("Default Tree");
	}
	public Tree(String value){
		super();
		this.setId(0L);
		this.setValue(value);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
