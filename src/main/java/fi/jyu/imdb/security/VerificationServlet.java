package fi.jyu.imdb.security;

import fi.jyu.imdb.user.UsersService;
import fi.jyu.imdb.verification.Verification;
import fi.jyu.imdb.verification.VerificationsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import java.io.IOException;


public class VerificationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hash = request.getParameter("hash");
        VerificationsService verificationsService  = VerificationsService.getInstance();
        Verification verification = verificationsService.getVerificationByHash(hash);

        if (verification == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            UsersService.getInstance().verify(verification.getUser());
            verificationsService.removeVerification(hash);
        }
    }
}
