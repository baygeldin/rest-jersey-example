package fi.jyu.task3.trailers;

import javax.xml.bind.annotation.XmlRootElement;

import fi.jyu.task3.movie.Movie;

@XmlRootElement
public class Trailer {
    private int id;
    private Movie movie;
    private String url;
    
    public Trailer(){}

    public Trailer(int id, Movie movie, String url) {
        this.setId(id);
        this.setMovie(movie);
        this.setUrl(url);
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