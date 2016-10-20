package fi.jyu.imdb.security;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import fi.jyu.imdb.user.User;
import fi.jyu.imdb.user.UsersService;
import fi.jyu.imdb.util.Util;

import static fi.jyu.imdb.util.Util.*;

import javax.ws.rs.container.PreMatching;
import java.util.Arrays;

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

        String authScheme = null;

        if (auth.matches("^[B|b]asic.*$")) {
            authScheme = javax.ws.rs.core.SecurityContext.BASIC_AUTH;

            String[] credentials = base64Decode(auth.replaceFirst("[B|b]asic ", "")).split(":", 2);

            user = UsersService.getInstance().getUserByLogin(credentials[0]);

            if (user == null) {
                throw new BadRequestException();
            }

            if (!Arrays.equals(user.getPassword(), Util.encodeMD5(credentials[1]))) {
                throw new ForbiddenException();
            }
        } else if (auth.matches("^[B|b]earer.*$")) {
            authScheme = "JWT";

            String[] jwt = auth.replaceFirst("[B|b]earer ", "").split("\\.");

            Gson gson = new Gson();
            JsonElement payload = gson.fromJson(base64Decode(jwt[1]), JsonElement.class);
            String login = payload.getAsJsonObject().get("login").getAsString();

            user = UsersService.getInstance().getUserByLogin(login);

            if (user == null) {
                throw new BadRequestException();
            }

            String signature;

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
        containerRequest.setSecurityContext(new SecurityContext(user, scheme, authScheme));
    }
}
