package fi.jyu.task3.user;


import java.net.URI;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import fi.jyu.task3.review.ReviewsResource;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
	private UsersService usersService = UsersService.getInstance();
	
    @GET
    public Response getUsersList(){
    	List<User> users = usersService.getUsersList();
    	GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @POST
    public Response addUser(User user, @Context UriInfo uriInfo){
        User newUser = usersService.addUser(user);
        String newId = String.valueOf(newUser.getId());
        
        URI uri = uriInfo.getBaseUriBuilder()
        		.path(UsersResource.class)
            	.path(newId)
            	.build();
        newUser.addLink(uri, "self");
            	
    	uri = uriInfo.getBaseUriBuilder()
    		      .path(UsersResource.class)
    		      .path(UsersResource.class, "getReviewsResource")
    		      .resolveTemplate("id", newId)
    		      .build();
    	newUser.addLink(uri, "reviews");
    	
    	uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        
    	return Response.created(uri)
        			   .entity(newUser).build();
    }
    
    @Path("/{id}")
    @GET
    public Response getUser(@PathParam("id") int id){
        User user = null;
        
        try {
        	user = usersService.getUser(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok(user).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response removeUser(@PathParam("id") int id)
    {
        try {
        	usersService.removeUser(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        try {
        	usersService.updateUser(id, user);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
    	return Response.ok().build();
    }
    
    @Path("/{id}/reviews")
    public ReviewsResource getReviewsResource(@PathParam("id") int id) {
        return new ReviewsResource(usersService.getUser(id));
    }
}