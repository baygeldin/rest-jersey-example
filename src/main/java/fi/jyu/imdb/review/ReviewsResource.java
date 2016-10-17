package fi.jyu.imdb.review;

import java.net.URI;
import java.util.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import fi.jyu.imdb.exception.IAmATeapotException;
import fi.jyu.imdb.movie.Movie;
import fi.jyu.imdb.movie.MoviesResource;
import fi.jyu.imdb.movie.MoviesService;
import fi.jyu.imdb.user.User;
import fi.jyu.imdb.user.UsersResource;
import fi.jyu.imdb.user.UsersService;

@Path("/reviews")
@PermitAll
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReviewsResource {
	private ReviewsService reviewsService = ReviewsService.getInstance();
	private UsersService usersService = UsersService.getInstance();
	private MoviesService moviesService = MoviesService.getInstance();
	
	private Movie movie = null;
	private User user = null;
	
	public ReviewsResource(){}

    public ReviewsResource(Movie movie){
        this.movie = movie;
    }
    
    public ReviewsResource(User user){
        this.user = user;
    }

    @GET
    public Response getReviewsList(
    		@QueryParam("movie") Integer movieId,
    		@QueryParam("author") Integer userId){
    	List<Review> reviewsList;
    	
		if (user == null && userId != null) {
        	user = usersService.getUser(userId);
        }
		
		if (movie == null && movieId != null) {
        	movie = moviesService.getMovie(movieId);
        }
		
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
    @RolesAllowed("verified")
    public Response addReview(Review review, @Context UriInfo uriInfo, @Context SecurityContext sc){
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
            if (!sc.isUserInRole("admin") && user != (User) sc.getUserPrincipal()) {
                throw new ForbiddenException();
            }
    		review.setAuthor(user);
    	} else {
            if (sc.isUserInRole("admin")) {
                int resUserId = review.getAuthor().getId();
                review.setAuthor(usersService.getUser(resUserId));
            } else {
                review.setAuthor((User) sc.getUserPrincipal());
            }
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
    @RolesAllowed("verified")
    public Response removeReview(@PathParam("id") int id, @Context SecurityContext sc)
    {
        User user = (User) sc.getUserPrincipal();

        try {
            if (!reviewsService.getReview(id).getAuthor().equals(user)) {
                throw new ForbiddenException();
            } else {
                reviewsService.removeReview(id);
            }
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed("verified")
    public Response updateReview(@PathParam("id") int id, Review review, @Context SecurityContext sc) {
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

    	User user = (User) sc.getUserPrincipal();

        try {
            if (!reviewsService.getReview(id).getAuthor().equals(user)) {
                throw new ForbiddenException();
            } else {
                reviewsService.updateReview(id, review);
            }
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
    	return Response.ok().build();
    }    
}
