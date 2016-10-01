package fi.jyu.task3.services;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import fi.jyu.task3.comment.CommentResource;
import fi.jyu.task3.user.User;
import fi.jyu.task3.user.Users;

@Path("/users")
public class UserService {

    private Users userslist;

    //return users list
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getUsersList(){

        return Response.ok(Users.getInstance()).build();

    }

    //you can insert an user
    @Path("add")
    @POST
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/json", "application/xml"})
    public Response addUser(User u){
        Users.getInstance().add(u);
        return Response.ok().build();
    }

    //return an user
    @Path("get/{name}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getByName(@PathParam("name") String name){
        User u = Users.getInstance().getByName(name);
        if(u!=null)
            return Response.ok(u).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    //nested comments
    @Path("/{name}/comments")
    public CommentResource getCommentResource(@PathParam("name") String name) {
        return new CommentResource(name);
    }
}