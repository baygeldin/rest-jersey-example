package fi.jyu.task3.movie;


import java.net.URI;
import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;

import fi.jyu.task3.link.Link;


@XmlRootElement
public class Movie {
    private int id;
    private String title;
    private String description;
    private Date releaseDate;
    private List<Link> links = new ArrayList<>();
    
    public Movie(){}

    public Movie(int id, String title, Date releaseDate) {
        this.setId(id);
        this.setTitle(title);
        this.setReleaseDate(releaseDate);
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
	
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}