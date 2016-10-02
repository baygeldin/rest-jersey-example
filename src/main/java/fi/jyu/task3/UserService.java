package fi.jyu.task3;


import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import fi.jyu.task3.review.ReviewResource;
import fi.jyu.task3.user.User;
import fi.jyu.task3.user.Users;

@Path("/users")
public class UserService {


    //return users list
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getUsersList(){

        return Response.ok(Users.getInstance()).build();

    }

    //you can insert an user
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    
    public Response addUser(User u, @Context UriInfo uriInfo){
        User newUser = Users.getInstance().addUser(u);
        String newId = String.valueOf(newUser.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();        
        return Response.created(uri)
        			   .entity(newUser).build();
    }

    //return an user
    @Path("/{id}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getByName(@PathParam("id") int id){
        User u = Users.getInstance().getById(id);
        if(u!=null)
            return Response.ok(u).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces({"application/json", "application/xml"})
    public Response removeByID(@PathParam("id") int id)
    {
    	Users.getInstance().removeByID(id);
    	return Response.ok().build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Response updateUserByID(User u)
    {
    	Users.getInstance().updateUser(u);
    	return Response.ok().build();
    }
    
    //nested comments
    @Path("/{name}/reviews")
    public ReviewResource getReviewResource(@PathParam("name") String name) {
        return new ReviewResource(name);
    }
}