package sphere;

import sphere.util.Url;

/** Configuration of the Sphere SDK, used internally by the SDK. */
public class Config {
    
    private static play.Configuration c = play.Configuration.root();
    
    public static String coreEndpoint  = c.getString("sphere.core");
    public static String authEndpoint  = c.getString("sphere.auth");
    public static String projectName   = c.getString("sphere.project");
    public static String projectID     = c.getString("sphere.projectID");
    public static String projectSecret = c.getString("sphere.projectSecret");

    /** HTTP API endpoint of current project
      * (configure this in application.conf using sphere.backend and sphere.project). */
    public static String projectEndpoint = Url.combine(coreEndpoint, projectName);
}
