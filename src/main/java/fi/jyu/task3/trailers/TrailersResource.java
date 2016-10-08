package fi.jyu.task3.trailers;

import java.net.URI;
import java.util.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import fi.jyu.task3.movie.Movie;
import fi.jyu.task3.movie.MoviesResource;
import fi.jyu.task3.movie.MoviesService;
import fi.jyu.task3.review.ReviewsResource;

@Path("/trailers")
@PermitAll
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TrailersResource {
	private TrailersService trailersService = TrailersService.getInstance();
	private MoviesService moviesService = MoviesService.getInstance();
	
	private Movie movie = null;
	
	public TrailersResource(){}

    public TrailersResource(Movie movie){
        this.movie = movie;
    }

    @GET
    public Response getTrailersList(
    		@QueryParam("movie") Integer movieId){
    	List<Trailer> trailersList;
    	
    	if (movie == null && movieId != null) {
        	movie = moviesService.getMovie(movieId);
        }
    	
    	if (movie == null) {
    		trailersList = trailersService.getTrailersList();
    	} else {
    		trailersList = trailersService.getTrailersList(movie);
    	}
    	GenericEntity<List<Trailer>> entity = new GenericEntity<List<Trailer>>(trailersList) {};
        
    	return Response.ok(entity).build();
    }
    
    @Path("/{id}")
    @GET
    public Response getTrailer(@PathParam("id") int id){
    	Trailer trailer = null;
        
        try {
        	trailer = trailersService.getTrailer(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok(trailer).build();
    }

    @POST
    @RolesAllowed("admin")

    public Response addTrailer(Trailer trailer, @Context UriInfo uriInfo){
    	if (movie != null) {
    		trailer.setMovie(movie);
    	} else {
    		int resMovieId = trailer.getMovie().getId();
    		trailer.setMovie(moviesService.getMovie(resMovieId));
    	}
    	
    	Trailer newTrailer = trailersService.addTrailer(trailer);
        String newId = String.valueOf(newTrailer.getId());
        
        URI uri = uriInfo.getBaseUriBuilder()
        		.path(ReviewsResource.class)
            	.path(newId)
            	.build();
        newTrailer.addLink(uri, "self");
        
        uri = uriInfo.getBaseUriBuilder()
    		      .path(MoviesResource.class)
    		      .path(MoviesResource.class, "getMovie")
    		      .resolveTemplate("id", String.valueOf(newTrailer.getMovie().getId()))
    		      .build();
        newTrailer.addLink(uri, "movie");
        
        uri = uriInfo.getAbsolutePathBuilder().path(newId).build(); 
        
        return Response.created(uri)
        			   .entity(newTrailer).build();
    }
    
    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")

    public Response removeTrailer(@PathParam("id") int id)
    {
        try {
        	trailersService.removeTrailer(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")

    public Response updateTrailer(@PathParam("id") int id, Trailer trailer) {
    	if (movie != null) {
    		trailer.setMovie(movie);
    	} else {
    		int resMovieId = trailer.getMovie().getId();
    		trailer.setMovie(moviesService.getMovie(resMovieId));
    	}
    	
        try {
        	trailersService.updateTrailer(id, trailer);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
    	return Response.ok().build();
    }    
}
