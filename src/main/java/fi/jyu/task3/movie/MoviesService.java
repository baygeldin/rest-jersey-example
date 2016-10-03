package fi.jyu.task3.movie;

import java.util.*;

public class MoviesService {
	private ArrayList<Movie> moviesList;
    private static MoviesService instance;

    private MoviesService() {
        setMoviesList(new ArrayList<Movie>());
    }

    public synchronized static MoviesService getInstance(){
		if(instance == null){
			instance = new MoviesService();
	    }
		
	    return instance;
    }

	public ArrayList<Movie> getMoviesList() {
		ArrayList<Movie> list = new ArrayList<Movie>();
		for (Movie movie : moviesList) {
			if (movie != null) {
				list.add(movie);
			}
		}
		
		return list;
	}

	private void setMoviesList(ArrayList<Movie> moviesList) {
		this.moviesList = moviesList;
	}
	
	public Movie getMovie(int id) {
		return moviesList.get(id);
	}
	
	public synchronized Movie addMovie(Movie movie) {
		movie.setId(moviesList.size());
		moviesList.add(movie);
		
		return movie;
	}
	
	public synchronized void updateMovie(int id, Movie movie) {
		movie.setId(id);
		moviesList.set(id, movie);
	}
	
	public synchronized void removeMovie(int id) {
		moviesList.set(id, null);
	}
}
