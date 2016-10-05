package ve.com.gem.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author informatica
 *
 */
public class Event {
	
	private String title;
	private String type="event";
	private Long id;
	private Date start;
	private Date end;
	private List<Event> children = new ArrayList<Event>(); 

	public Event() {
		// TODO Auto-generated constructor stub
	}
	
	public Event(Long id){
		
	}

	public Event(String name, String type, Long id) {
		super();
		this.title = name;
		this.type = type;
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Event> getChildren() {
		return children;
	}

	public void setChildren(List<Event> children) {
		this.children = children;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date begin) {
		this.start = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Event [name=" + title + ", type=" + type + ", id=" + id + ", children=" + children + "]";
	}
	

}
