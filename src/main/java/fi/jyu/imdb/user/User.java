package fi.jyu.imdb.user;


import java.net.URI;
import java.security.Principal;
import java.util.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import fi.jyu.imdb.link.Link;
import fi.jyu.imdb.util.PasswordXmlAdapter;
import fi.jyu.imdb.util.Util;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Principal {
    private int id;
	private String login;
    private String email;

    @XmlJavaTypeAdapter(PasswordXmlAdapter.class)
    private byte[] password;

	private List<String> role = new ArrayList<>();
    private Date birth;
    private List<Link> links = new ArrayList<>();
    
    public User(){}
    
    public User(String login, String password, String email) {
        this.setLogin(login);
        this.setPassword(Util.encodeMD5(password));
        this.setEmail(email);
    }
    
    public List<Link> getLinks() {
		return links;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getLogin() {
        return login;
	}

	public void setLogin(String login) {
        this.login = login;
	}

	public String getEmail() {
        return email;
	}

	public void setEmail(String email) {
        this.email = email;
	}

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
	}

	public List<String> getRole() {
        return role;
	}

	public void setRole(List<String> role) {
        this.role = role;
	}

    @Override
    public String getName() {
        return this.login;
    }
}