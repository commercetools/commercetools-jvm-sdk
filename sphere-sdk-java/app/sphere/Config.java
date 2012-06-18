package sphere;

import sphere.util.Url;

import java.util.regex.Pattern;

/** Configuration of the Sphere SDK, used internally by the SDK. */
public class Config {
    
    private static play.Configuration c =  play.Configuration.root();

    /** Main Sphere API endpoint. */
    public static String coreEndpoint()  { return getStringOrThrow("sphere.core"); }
    /** Sphere authorization service endpoint. */
    public static String authEndpoint()  { return getStringOrThrow("sphere.auth"); }

    /** Name of your project. Configured as 'sphere.project'. */
    public static String projectID()   { return validateProjectName(getStringOrThrow("sphere.project")); }
    /** Id of your project, generated in the developer center. Configured as 'sphere.clientID'. */
    public static String clientID()      { return validateProjectID(getStringOrThrow("sphere.clientID")); }
    /** Authorization key for your project, generated in the developer center. Configured as 'sphere.clientSecret'. */
    public static String clientSecret()  { return getStringOrThrow("sphere.clientSecret"); }

    /** Converts a null value returned by Play Configuration into an exception.
     *  It's better to fail fast rather than passing around null and crashing later. */
    private static String getStringOrThrow(String key) {
        String value = c.getString(key);
        if (value == null) {
            throw c.reportError(key, "Path " + key + " not found in configuration.", null);
        } else {
            return value;
        }
    }

    private static Pattern projectRegex = Pattern.compile("[a-zA-Z0-9_-]+");

    private static String validateProjectName(String projectName) {
        if (projectRegex.matcher(projectName).matches()) return projectName;
        else throw new IllegalArgumentException(String.format(
            "Invalid project name '%s'. Project names can only contain letters, numbers, dashes and underscores.",
            projectName));
    }

    private static String validateProjectID(String projectID) {
        if (projectRegex.matcher(projectID).matches()) return projectID;
        else throw new IllegalArgumentException("Invalid project ID: " + projectID);
    }
}
