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
<<<<<<< HEAD
    public Response getReviewslist(){
=======
    public Response getCommentslist(){
>>>>>>> c670216fe99f51b46273426de6dc8948a7da371f
        return Response.ok(reviewsList).build();
    }

    @Path("/add")
    @POST
    @Consumes({"application/json", "application/xml"})
<<<<<<< HEAD
    public Response addReview(Review c){
    	
=======
    public Response addComment(Review c){
>>>>>>> c670216fe99f51b46273426de6dc8948a7da371f
        reviewsList.add(c);
        return Response.ok().build();
    }

}
