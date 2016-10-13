package fi.jyu.task3.security;

import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.*;
import java.io.*;
import java.util.HashMap;

import static fi.jyu.task3.util.Util.*;
import fi.jyu.task3.user.User;
import fi.jyu.task3.user.UsersService;

public class JWTServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();

        try {
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        JsonElement credentials = gson.fromJson(jb.toString(), JsonElement.class);
        String login = credentials.getAsJsonObject().get("login").getAsString();
        String password = credentials.getAsJsonObject().get("password").getAsString();

        User user = UsersService.getInstance().getUserByName(login);

        if (user == null) {
            throw new BadRequestException();
        }

        if (!user.getPassword().equals(password)) {
            throw new ForbiddenException();
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
            throw new InternalServerErrorException();
        }
    }
}
