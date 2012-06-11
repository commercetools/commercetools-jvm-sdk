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

    /** Name of your project. */
    public static String projectName()   { return validateProjectName(getStringOrThrow("sphere.project")); }
    /** Id of your project, generated in the developer center. */
    public static String projectID()     { return validateProjectID(getStringOrThrow("sphere.projectID")); }
    /** Authorization key for your project, generated in the developer center. */
    public static String projectSecret() { return getStringOrThrow("sphere.projectSecret"); }

    /** Converts the null value returned by Play into an exception.
     *  It's better to fail fast rather than pass around a null value and crashing somewhere else. */
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
        else throw new IllegalArgumentException("Invalid project name: " + projectName);
    }

    private static String validateProjectID(String projectID) {
        if (projectRegex.matcher(projectID).matches()) return projectID;
        else throw new IllegalArgumentException("Invalid project ID: " + projectID);
    }
}
