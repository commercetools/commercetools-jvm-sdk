package sphere;

import sphere.util.Url;

/** Configuration of the Sphere SDK, used internally by the SDK. */
public class Config {
    
    private static String backendURL = play.Configuration.root().getString("sphere.backend");
    private static String projectID = play.Configuration.root().getString("sphere.project");
    /** HTTP API endpoint of current project
      * (configure this in application.conf using sphere.backend and sphere.project). */
    public static String projectURL = Url.combine(backendURL, projectID);
}
