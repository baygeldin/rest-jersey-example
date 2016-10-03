package fi.jyu.task3.review;

import java.net.URI;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import fi.jyu.task3.exception.IAmATeapotException;
import fi.jyu.task3.movie.Movie;
import fi.jyu.task3.movie.MoviesResource;
import fi.jyu.task3.movie.MoviesService;
import fi.jyu.task3.user.User;
import fi.jyu.task3.user.UsersResource;
import fi.jyu.task3.user.UsersService;

@Path("/reviews")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReviewsResource {
	private ReviewsService reviewsService = ReviewsService.getInstance();
	private UsersService usersService = UsersService.getInstance();
	private MoviesService moviesService = MoviesService.getInstance();
	
	@QueryParam("movie") Integer movieId;
	@QueryParam("user") Integer userId;
	
	private Movie movie = null;
	private User user = null;
	
	public ReviewsResource(){}

    public ReviewsResource(Movie movie){
        this.movie = movie;
        if (userId != null) {
        	usersService.getUser(userId);
        }
    }
    
    public ReviewsResource(User user){
        this.user = user;
        if (movieId != null) {
        	moviesService.getMovie(movieId);
        }
    }

    @GET
    public Response getReviewsList(){
    	List<Review> reviewsList;
    	
    	if (movie == null && user == null) {
    		reviewsList = reviewsService.getReviewsList();
    	} else if (movie == null && user != null) {
    		reviewsList = reviewsService.getReviewsList(user);
    	} else {
    		reviewsList = reviewsService.getReviewsList(movie);
    	}
    	GenericEntity<List<Review>> entity = new GenericEntity<List<Review>>(reviewsList) {};
        
    	return Response.ok(entity).build();
    }
    
    @Path("/{id}")
    @GET
    public Response getReview(@PathParam("id") int id){
        Review review = null;
        
        try {
        	review = reviewsService.getReview(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok(review).build();
    }

    @POST
    public Response addReview(Review review, @Context UriInfo uriInfo){
    	if (review.getContent().equals("Brew me some coffee!")) {
    		throw new IAmATeapotException();
    	}
    	
    	if (movie != null) {
    		review.setMovie(movie);
    	} else {
    		int resMovieId = review.getMovie().getId();
    		review.setMovie(moviesService.getMovie(resMovieId));
    	}
    	
    	if (user != null) {
    		review.setAuthor(user);
    	} else {
    		int resUserId = review.getAuthor().getId();
    		review.setAuthor(usersService.getUser(resUserId));
    	}
    	
        Review newReview = reviewsService.addReview(review);
        String newId = String.valueOf(newReview.getId());
        
        URI uri = uriInfo.getBaseUriBuilder()
        		.path(ReviewsResource.class)
            	.path(newId)
            	.build();
        newReview.addLink(uri, "self");
        
        uri = uriInfo.getBaseUriBuilder()
    		      .path(UsersResource.class)
    		      .path(UsersResource.class, "getUser")
    		      .resolveTemplate("id", String.valueOf(newReview.getAuthor().getId()))
    		      .build();
        newReview.addLink(uri, "author");
        
        uri = uriInfo.getBaseUriBuilder()
  		      .path(MoviesResource.class)
  		      .path(MoviesResource.class, "getMovie")
  		      .resolveTemplate("id", String.valueOf(newReview.getMovie().getId()))
  		      .build();
        newReview.addLink(uri, "movie");
        
        uri = uriInfo.getAbsolutePathBuilder().path(newId).build();  
        
        return Response.created(uri)
        			   .entity(newReview).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response removeReview(@PathParam("id") int id)
    {
        try {
        	reviewsService.removeReview(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateReview(@PathParam("id") int id, Review review) {
    	if (review.getContent().equals("Brew me some coffee!")) {
    		throw new IAmATeapotException();
    	}
    	
    	if (movie != null) {
    		review.setMovie(movie);
    	} else {
    		int resMovieId = review.getMovie().getId();
    		review.setMovie(moviesService.getMovie(resMovieId));
    	}
    	
    	if (user != null) {
    		review.setAuthor(user);
    	} else {
    		int resUserId = review.getAuthor().getId();
    		review.setAuthor(usersService.getUser(resUserId));
    	}
    	
        try {
        	reviewsService.updateReview(id, review);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
    	return Response.ok().build();
    }    
}
