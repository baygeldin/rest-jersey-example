package fi.jyu.imdb.security;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.jyu.imdb.user.User;
import fi.jyu.imdb.user.UsersService;
import fi.jyu.imdb.util.Util;
import fi.jyu.imdb.verification.Verification;
import fi.jyu.imdb.verification.VerificationsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.util.Date;

public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        String body = Util.getRequestAsString(request);
        JsonObject object = gson.fromJson(body, JsonElement.class).getAsJsonObject();
        String login = object.get("login").getAsString();
        String password = object.get("password").getAsString();
        String email = object.get("email").getAsString();
        User user = new User(login, password, email);

        if (user.getPassword() == null || user.getEmail() == null || user.getLogin() == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Verification verification = new Verification(user);
        UsersService.getInstance().addUser(user);
        VerificationsService.getInstance().addVerification(verification);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
