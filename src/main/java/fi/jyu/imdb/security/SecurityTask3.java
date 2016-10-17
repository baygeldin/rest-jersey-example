package fi.jyu.imdb.security;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

class SecurityTask3 extends ResourceConfig {
   public SecurityTask3() {
       register(RolesAllowedDynamicFeature.class);
   }
}