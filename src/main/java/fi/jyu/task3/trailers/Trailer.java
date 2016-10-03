package fi.jyu.task3.trailers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import fi.jyu.task3.link.Link;
import fi.jyu.task3.movie.Movie;

@XmlRootElement
public class Trailer {
    private int id;
    private Movie movie;
    private String url;
    private List<Link> links = new ArrayList<>();
    
    public Trailer(){}

    public Trailer(int id, Movie movie, String url) {
        this.setId(id);
        this.setMovie(movie);
        this.setUrl(url);
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

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}