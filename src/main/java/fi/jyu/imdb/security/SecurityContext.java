package fi.jyu.imdb.security;

import fi.jyu.imdb.user.User;

public class SecurityContext implements javax.ws.rs.core.SecurityContext {
    private String authScheme;
    private User user;
    private String scheme;
 
    public SecurityContext(User user, String scheme, String authScheme) {
		this.user = user;
		this.scheme = scheme;
        this.authScheme = authScheme;
    }
    
    @Override
    public User getUserPrincipal() {
        return this.user;
    }
 
    @Override
    public boolean isUserInRole(String s) {
        if (user.getRole() != null) {
            return user.getRole().contains(s);
        }
        return false;
    }
 
    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }
 
    @Override
    public String getAuthenticationScheme() {
        return authScheme;
    }
}