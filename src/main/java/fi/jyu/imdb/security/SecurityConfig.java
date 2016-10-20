package fi.jyu.imdb.security;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

class SecurityConfig extends ResourceConfig {
   public SecurityConfig() {
       register(RolesAllowedDynamicFeature.class);
   }
}