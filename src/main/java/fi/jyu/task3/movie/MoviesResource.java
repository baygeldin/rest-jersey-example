package fi.jyu.task3.movie;

import java.net.URI;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import fi.jyu.task3.review.ReviewsResource;

@Path("/movies")
public class MoviesResource {
	private MoviesService moviesService = MoviesService.getInstance();
	
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getMoviesList(){
    	List<Movie> movies = moviesService.getMoviesList();
    	GenericEntity<List<Movie>> entity = new GenericEntity<List<Movie>>(movies) {};
        return Response.ok(entity).build();
    }

    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Response addMovie(Movie movie, @Context UriInfo uriInfo){
        Movie newMovie = moviesService.addMovie(movie);
        String newId = String.valueOf(newMovie.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();        
        return Response.created(uri)
        			   .entity(newMovie).build();
    }

    @Path("/{id}")
    @GET
    @Produces({"application/json", "application/xml"})
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
    @Produces({"application/json", "application/xml"})
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
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Response updateMovie(@PathParam("id") int id, Movie movie) {
        try {
        	moviesService.updateMovie(id, movie);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
    	return Response.ok().build();
    }
    
    @Path("/{id}/reviews")
    public ReviewsResource getReviewResource(@PathParam("id") int id) {
        return new ReviewsResource(moviesService.getMovie(id));
    }
}