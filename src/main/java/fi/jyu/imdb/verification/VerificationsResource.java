package fi.jyu.imdb.verification;

import javax.annotation.security.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/verifications")
@Produces(MediaType.APPLICATION_JSON)
public class VerificationsResource {
    private VerificationsService verificationsService = VerificationsService.getInstance();

    @GET
    @RolesAllowed("admin")
    public Response getUsersList(@QueryParam("user") String login){
        if (login != null) {
            Verification verification = verificationsService.getVerificationByUserLogin(login);
            return Response.ok(verification).build();
        } else {
            List<Verification> verifications = verificationsService.getVerificationsList();
            GenericEntity<List<Verification>> entity = new GenericEntity<List<Verification>>(verifications) {};
            return Response.ok(entity).build();
        }
    }
}
