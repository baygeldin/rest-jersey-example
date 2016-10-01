package fi.jyu.task3.comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
    private Comments commentsList;

    public CommentResource(String name){
        this.commentsList = Comments.getInstance(name);
        System.out.println(name);
    }

    @GET
    public Response getCommentslist(){
        return Response.ok(commentsList).build();
    }

    @Path("/add")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addComment(Comment c){
        commentsList.add(c);
        return Response.ok().build();
    }

}
