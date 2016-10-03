package fi.jyu.task3.review;

import javax.xml.bind.annotation.XmlRootElement;

import fi.jyu.task3.movie.Movie;
import fi.jyu.task3.user.User;

@XmlRootElement
public class Review {
    private int id;
    private User author;
    private Movie movie;
    private String content;

    public Review(){}

    public Review(int id, User author, Movie movie, String content) {
        this.setId(id);
        this.setAuthor(author);
        this.setMovie(movie);
        this.setContent(content);
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
