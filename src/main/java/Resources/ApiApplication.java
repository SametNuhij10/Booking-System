package Resources;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class ApiApplication extends ResourceConfig {
    public ApiApplication() {
        packages("Resources");

    }
}
