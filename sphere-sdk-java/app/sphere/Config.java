package sphere;

import sphere.util.Url;

import java.util.regex.Pattern;

/** Configuration of the Sphere SDK, used internally by the SDK. */
public class Config {
    
    private static play.Configuration c =  play.Configuration.root();
    
    private final static String core         = "sphere.core";
    private final static String auth         = "sphere.auth";
    private final static String project      = "sphere.project";
    private final static String clientID     = "sphere.clientID";
    private final static String clientSecret = "sphere.clientSecret";

    /** Main Sphere API endpoint. */
    public static String coreEndpoint()  { return getStringOrThrow(core); }
    /** Sphere authorization service endpoint. */
    public static String authEndpoint()  { return getStringOrThrow(auth); }
    /** Name of your project. Configured as 'sphere.project'. */
    public static String projectID()   { return validateProjectName(project); }
    /** Id of your project, generated in the developer center. Configured as 'sphere.clientID'. */
    public static String clientID()      { return getStringOrThrow(clientID); }
    /** Authorization key for your project, generated in the developer center. Configured as 'sphere.clientSecret'. */
    public static String clientSecret()  { return getStringOrThrow(clientSecret); }

    /** Converts a null value returned by Play Configuration into an exception.
     *  It's better to fail fast rather than passing around null and crashing later. */
    private static String getStringOrThrow(String key) {
        String value = c.getString(key);
        if (value == null) {
            throw c.reportError(key, "Path "+key+" not found in configuration.", null);
        } else {
            return value;
        }
    }

    private static Pattern projectRegex = Pattern.compile("[a-zA-Z0-9_-]+");

    private static String validateProjectName(String key) {
        String projectName = getStringOrThrow(key);
        if (projectRegex.matcher(projectName).matches()) return projectName;
        else throw c.reportError(key,
            "Invalid project name '"+projectName+"'. Project name can only contain letters, numbers, dashes and underscores.", null);
    }
}
