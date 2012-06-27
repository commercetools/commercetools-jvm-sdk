package sphere;

import java.util.regex.Pattern;

/** Internal configuration of the Sphere SDK. */
class Config {
    
    private static Config instance = new Config(play.Configuration.root());
    public static Config root() {
        return instance;
    }

    private play.Configuration playConfig;
    /** Creates a new instance of config. */
    public Config(play.Configuration playConfig) {
        this.playConfig = playConfig;
    }

    private final static String core         = "sphere.core";
    private final static String auth         = "sphere.auth";
    private final static String project      = "sphere.project";
    private final static String clientID     = "sphere.clientID";
    private final static String clientSecret = "sphere.clientSecret";

    private final static Pattern projectRegex = Pattern.compile("[a-zA-Z0-9_-]+");

    /** Main Sphere API endpoint. */
    public String coreEndpoint()  { return getStringOrThrow(core); }
    /** Sphere authorization service endpoint. */
    public String authEndpoint()  { return getStringOrThrow(auth); }
    /** Name of your project. Configured as 'sphere.project'. */
    public String projectID()   { return validateProjectName(project); }
    /** Id of your project, generated in the developer center. Configured as 'sphere.clientID'. */
    public String clientID()      { return getStringOrThrow(clientID); }
    /** Authorization key for your project, generated in the developer center. Configured as 'sphere.clientSecret'. */
    public String clientSecret()  { return getStringOrThrow(clientSecret); }

    /** Converts a null value returned by Play Configuration into an exception.
     *  It's better to fail fast rather than passing around null and crashing later. */
    private String getStringOrThrow(String key) {
        String value = playConfig.getString(key);
        if (value == null) {
            throw playConfig.reportError(key, "Path "+key+" not found in configuration.", null);
        } else {
            return value;
        }
    }

    private String validateProjectName(String key) {
        String projectName = getStringOrThrow(key);
        if (projectRegex.matcher(projectName).matches()) return projectName;
        else throw playConfig.reportError(key,
            "Invalid project name '"+projectName+"'. Project name can only contain letters, numbers, dashes and underscores.", null);
    }
}
