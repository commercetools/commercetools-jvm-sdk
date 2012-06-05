package sphere;

import sphere.util.Url;

/** Configuration of the Sphere SDK, used internally by the SDK. */
public class Config {
    
    private static play.Configuration c =  play.Configuration.root();

    /** Main Sphere API endpoint. */
    public static String coreEndpoint()  { return getStringOrThrow("sphere.core"); }
    /** Sphere authorization service endpoint. */
    public static String authEndpoint()  { return getStringOrThrow("sphere.auth"); }
    /** Sphere OAuth 2.0 token endpoint. */
    public static String tokenEndpoint() { return Url.combine(authEndpoint(), "/oauth/token"); }

    /** Name of your project. */
    public static String projectName()   { return getStringOrThrow("sphere.project"); }
    /** Id of your project, generated in the developer center. */
    public static String projectID()     { return getStringOrThrow("sphere.projectID"); }
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
}
