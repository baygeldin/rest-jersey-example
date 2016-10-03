package fi.jyu.task3.user;


import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;

import fi.jyu.task3.user.link.Link;

@XmlRootElement
public class User {
    private int id;
    private String name;
    private Date birth;
    private List<Link> links = new ArrayList<>();
    
    public User(){}

    public User(int id, String name, Date birth) {
        this.setId(id);
        this.setName(name);
        this.setBirth(birth);
    }
    
    public void addLink(String url, String rel) {
    	Link link = new Link();
    	link.setLink(url);
    	link.setRel(rel);
    	links.add(link);
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
}