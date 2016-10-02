package fi.jyu.task3.review;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReviewResource {
    private Reviews reviewsList;

    public ReviewResource(String name){
        this.reviewsList = Reviews.getInstance(name);
        System.out.println(name);
    }

    @GET
    public Response getCommentslist(){
        return Response.ok(reviewsList).build();
    }

    @Path("/add")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addComment(Review c){
        reviewsList.add(c);
        return Response.ok().build();
    }

}
