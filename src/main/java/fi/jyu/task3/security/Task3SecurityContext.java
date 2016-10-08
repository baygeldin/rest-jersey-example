package fi.jyu.task3.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
 
/**
 * Custom Security Context.
 * 
 * @author Deisss (MIT License)
*/
public class Task3SecurityContext implements SecurityContext {
    private User user;
    private String scheme;
 
    public Task3SecurityContext(User user, String scheme) {
		this.user = user;
		this.scheme = scheme;
    }
    
    @Override
    public Principal getUserPrincipal() {return this.user;}
 
    @Override
    public boolean isUserInRole(String s) {
        if (user.getRole() != null) {
            return user.getRole().contains(s);
        }
        return false;
    }
 
    @Override
    public boolean isSecure() {return "https".equals(this.scheme);}
 
    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}