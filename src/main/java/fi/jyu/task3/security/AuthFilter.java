package fi.jyu.task3.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import fi.jyu.task3.exception.ErrorMessage;
import fi.jyu.task3.user.User;

import javax.ws.rs.container.PreMatching;
  
/**
 * Jersey HTTP Basic Auth filter
 * @author Deisss (LGPLv3)
 */
@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
    private static final CharSequence SECURED_URL_PREFIX = "secure";

	@Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
    	

        String auth = containerRequest.getHeaderString("authorization");

        Boolean req = containerRequest.getUriInfo().getPath().contains("reg");

        if (req){
            return;
        }

        if(auth == null) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
  
        String[] lap = BasicAuth.decode(auth);
  
        if(lap == null || lap.length != 2) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
  
        User authentificationResult =  PasswordAuthentication.Authentication(lap[0], lap[1]);


        // We configure your Security Context here
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        containerRequest.setSecurityContext(new Task3SecurityContext(authentificationResult, scheme));
        
        if ((containerRequest.getUriInfo().getPath().contains(SECURED_URL_PREFIX))
        		|| (containerRequest.getMethod().equals("DELETE")) || (containerRequest.getMethod().equals("POST"))) {
		
			if (authentificationResult!=null) return;
    		
			ErrorMessage errorMessage = new ErrorMessage("User cannot access the resource.", 401,
                    "http://myDocs.org");
            Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
            containerRequest.abortWith(unauthorizedStatus);
        }
        
    }
}
