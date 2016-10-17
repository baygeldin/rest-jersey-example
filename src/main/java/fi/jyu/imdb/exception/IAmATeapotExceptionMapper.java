package fi.jyu.imdb.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IAmATeapotExceptionMapper implements ExceptionMapper<IAmATeapotException> {
	@Override
	public Response toResponse(IAmATeapotException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 418, "https://tools.ietf.org/html/rfc2324"); 
		return Response.status(418).entity(errorMessage).build();
	}
}
