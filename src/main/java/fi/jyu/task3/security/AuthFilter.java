package fi.jyu.task3.security;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import fi.jyu.task3.user.User;
import fi.jyu.task3.user.UsersService;

import static fi.jyu.task3.util.Util.*;

import javax.ws.rs.container.PreMatching;

/**
 * Jersey HTTP Basic Auth filter
 * @author Deisss (LGPLv3)
 */
@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
	@Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        String auth = containerRequest.getHeaderString("Authorization");
        User user = null;

        if (auth == null || containerRequest.getUriInfo().getPath().contains("reg")) {
            return;
        }

        if (auth.matches("^[B|b]asic.*$")) {
            String[] credentials = base64Decode(auth.replaceFirst("[B|b]asic ", "")).split(":", 2);

            user = UsersService.getInstance().getUserByName(credentials[0]);

            if (user == null) {
                throw new BadRequestException();
            }

            if (!user.getPassword().equals(credentials[1])) {
                throw new ForbiddenException();
            }
        } else if (auth.matches("^[B|b]earer.*$")) {
            String[] jwt = auth.replaceFirst("[B|b]earer ", "").split("\\.");

            Gson gson = new Gson();
            JsonElement payload = gson.fromJson(base64Decode(jwt[1]), JsonElement.class);
            String login = payload.getAsJsonObject().get("login").getAsString();

            user = UsersService.getInstance().getUserByName(login);

            if (user == null) {
                throw new BadRequestException();
            }

            String signature = null;

            try {
                signature = base64Encode(encodeHS256(getSecret(), jwt[0] + "." + jwt[1]));
            } catch (Exception e) {
                throw new InternalServerErrorException();
            }

            if (signature == null || !jwt[2].equals(signature)) {
                throw new ForbiddenException();
            }
        }

        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        containerRequest.setSecurityContext(new Task3SecurityContext(user, scheme));
    }
}
