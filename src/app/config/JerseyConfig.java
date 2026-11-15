package app.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        
    	// REQUIRED:
		// need to explicitly tell Jersey where the REST classes are found
		packages("app.rest");

		// let ServiceException turn validation errors into {"error": ...};
		// Jersey's own validation mapper would answer with an empty 400 (seen as a 404)
		property(ServerProperties.BV_FEATURE_DISABLE, true);


    }
}