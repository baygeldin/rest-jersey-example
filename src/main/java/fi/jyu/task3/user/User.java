package fi.jyu.task3.user;


import java.net.URI;
import java.security.Principal;
import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;

import fi.jyu.task3.link.Link;

@XmlRootElement
public class User implements Principal {
	private String firstName, lastName, login, email, password;
	private List<String> role;
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
    
    public List<Link> getLinks() {
		return links;
	}
    
    public void setLinks(List<Link> links) {
		this.links = links;
	}
    
    public void addLink(URI url, String rel) {
    	Link link = new Link();
    	link.setLink(url.toString());
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

	public String getFirstName() {return this.firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}

	public String getLastName() {return this.lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}

	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}

	public List<String> getRole() {return role;}

	public void setRole(List<String> role) {this.role = role;}

}