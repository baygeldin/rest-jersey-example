package fi.jyu.imdb.security;

import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

import static fi.jyu.imdb.util.Util.*;
import fi.jyu.imdb.user.User;
import fi.jyu.imdb.user.UsersService;
import fi.jyu.imdb.util.Util;

public class JWTServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        String body = Util.getRequestAsString(request);
        JsonElement credentials = gson.fromJson(body, JsonElement.class);
        String login = credentials.getAsJsonObject().get("login").getAsString();
        String password = credentials.getAsJsonObject().get("password").getAsString();

        User user = UsersService.getInstance().getUserByLogin(login);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!Arrays.equals(user.getPassword(), Util.encodeMD5(password))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        HashMap<String, String> headerObject = new HashMap<String, String>();
        headerObject.put("alg", "HS256");
        headerObject.put("typ", "JWT");

        HashMap<String, Object> payloadObject = new HashMap<String, Object>();
        payloadObject.put("login", login);
        payloadObject.put("roles", user.getRole());

        String header = base64Encode(gson.toJson(headerObject));
        String payload =  base64Encode(gson.toJson(payloadObject));

        try {
            String signature = base64Encode(encodeHS256(getSecret(), header + "." + payload));
            String token = header + "." + payload + "." + signature;
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.print(token);
            out.flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
