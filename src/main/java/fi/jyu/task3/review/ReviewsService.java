package fi.jyu.task3.review;

import java.util.*;

import fi.jyu.task3.movie.Movie;
import fi.jyu.task3.user.User;

public class ReviewsService {
    private ArrayList<Review> reviewsList;
    private static ReviewsService instance;

    public synchronized static ReviewsService getInstance(){
		if(instance == null){
			instance = new ReviewsService();
	    }
		
	    return instance;
    }

    private ReviewsService() {
        setReviewsList(new ArrayList<Review>());
    }

	public ArrayList<Review> getReviewsList() {
		ArrayList<Review> list = new ArrayList<Review>();
		for (Review review : reviewsList) {
			if (review != null) {
				list.add(review);
			}
		}
		
		return list;
	}
	
	public ArrayList<Review> getReviewsList(User user) {
		ArrayList<Review> list = new ArrayList<Review>();
		for (Review review : reviewsList) {
			if (review != null && review.getAuthor() == user) {
				list.add(review);
			}
		}
		
		return list;
	}
	
	public ArrayList<Review> getReviewsList(Movie movie) {
		ArrayList<Review> list = new ArrayList<Review>();
		for (Review review : reviewsList) {
			if (review != null && review.getMovie() == movie) {
				list.add(review);
			}
		}
		
		return list;
	}

	public void setReviewsList(ArrayList<Review> reviewsList) {
		this.reviewsList = reviewsList;
	}
	
	public Review getReview(int id) {
		return reviewsList.get(id);
	}
	
	public synchronized Review addReview(Review review) {
		review.setId(reviewsList.size());
		reviewsList.add(review);
		
		return review;
	}
	
	public synchronized void updateReview(int id, Review review) {
		review.setId(id);
		reviewsList.set(id, review);
	}
	
	public synchronized void removeReview(int id) {
		reviewsList.set(id, null);
	}
}
