package fi.jyu.imdb.user;


import java.net.URI;
import java.util.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.ValidationException;

import fi.jyu.imdb.review.ReviewsResource;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

	private UsersService usersService = UsersService.getInstance();
	
    @GET
    @PermitAll
    public Response getUsersList(){
    	List<User> users = usersService.getUsersList();
    	GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @POST
    @RolesAllowed("admin")
    public Response addUser(User user, @Context UriInfo uriInfo){
        if (user.getPassword() == null || user.getEmail() == null || user.getLogin() == null) {
            throw new BadRequestException();
        }

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

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id){
        User user;
        
        try {
        	user = usersService.getUser(id);
        } catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
        
        return Response.ok(user).build();
    }
    
    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
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
    @RolesAllowed("admin")
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
    	User user;
    	
    	try {
    		user = usersService.getUser(id);
    	} catch(IndexOutOfBoundsException e) {
        	throw new NotFoundException();
        }
    	
        return new ReviewsResource(user);
    }
}