package fi.jyu.imdb.trailer;

import java.util.*;

import fi.jyu.imdb.movie.Movie;

public class TrailersService {
    private ArrayList<Trailer> trailersList;
    private static TrailersService instance;

    public synchronized static TrailersService getInstance(){
		if(instance == null){
			instance = new TrailersService();
	    }
		
	    return instance;
    }

    private TrailersService() {
        setTrailersList(new ArrayList<Trailer>());
    }

	public ArrayList<Trailer> getTrailersList() {
		ArrayList<Trailer> list = new ArrayList<Trailer>();
		for (Trailer trailer : trailersList) {
			if (trailer != null) {
				list.add(trailer);
			}
		}
		
		return list;
	}
	
	public ArrayList<Trailer> getTrailersList(Movie movie) {
		ArrayList<Trailer> list = new ArrayList<Trailer>();
		for (Trailer trailer : trailersList) {
			if (trailer != null && trailer.getMovie() == movie) {
				list.add(trailer);
			}
		}
		
		return list;
	}

	public void setTrailersList(ArrayList<Trailer> trailersList) {
		this.trailersList = trailersList;
	}
	
	public Trailer getTrailer(int id) {
		return trailersList.get(id);
	}
	
	public synchronized Trailer addTrailer(Trailer trailer) {
		trailer.setId(trailersList.size());
		trailersList.add(trailer);
		
		return trailer;
	}
	
	public synchronized void updateTrailer(int id, Trailer trailer) {
		trailer.setId(id);
		trailersList.set(id, trailer);
	}
	
	public synchronized void removeTrailer(int id) {
		trailersList.set(id, null);
	}
}
