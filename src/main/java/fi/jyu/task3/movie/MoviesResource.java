package fi.jyu.task3.movie;

import java.net.URI;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import fi.jyu.task3.review.ReviewsResource;
import fi.jyu.task3.trailers.TrailersResource;

@Path("/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {
	private MoviesService moviesService = MoviesService.getInstance();
	
    @GET
    public Response getMoviesList(@QueryParam("year") int year){
    	List<Movie> movies;
    	if(year > 0)
    		movies = moviesService.getAllMoviesForYear(year); 
    	else 
    		movies = moviesService.getMoviesList();
    	GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(movies) {};
        return Response.ok(entity).build();
    }

    @POST
    public Response addMovie(Movie movie, @Context UriInfo uriInfo){
    	Movie newMovie = moviesService.addMovie(movie);
        String newId = String.valueOf(newMovie.getId());
        
        URI uri = uriInfo.getBaseUriBuilder()
        		.path(MoviesResource.class)
            	.path(newId)
            	.build();
        newMovie.addLink(uri, "self");
            	
    	uri = uriInfo.getBaseUriBuilder()
    		      .path(MoviesResource.class)
    		      .path(MoviesResource.class, "getReviewsResource")
    		      .resolveTemplate("id", newId)
    		      .build();
    	newMovie.addLink(uri, "reviews");
    	
    	uri = uriInfo.getBaseUriBuilder()
  		      .path(MoviesResource.class)
  		      .path(MoviesResource.class, "getTrailersResource")
  		      .resolveTemplate("id", newId)
  		      .build();
    	newMovie.addLink(uri, "trailers");
    	
    	uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        
    	return Response.created(uri)
        			   .entity(newMovie).build();
    }

    @Path("/{id}")
    @GET
    public Response getMovie(@PathParam("id") int id){
        Movie movie = null;
        
        try {
        	movie = moviesService.getMovie(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok(movie).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response removeMovie(@PathParam("id") int id)
    {
        try {
        	moviesService.removeMovie(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") int id, Movie movie) {
        try {
        	moviesService.updateMovie(id, movie);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
    	return Response.ok().build();
    }
    
    @Path("/{id}/reviews")
    public ReviewsResource getReviewsResource(@PathParam("id") int id) {
    	Movie movie;
    	
    	try {
    		movie = moviesService.getMovie(id);
    	} catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
    	
        return new ReviewsResource(movie);
    }
    
    @Path("/{id}/trailers")
    public TrailersResource getTrailersResource(@PathParam("id") int id) {
    	Movie movie;
    	
    	try {
    		movie = moviesService.getMovie(id);
    	} catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        return new TrailersResource(movie);
    }
}