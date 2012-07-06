package sphere;

import java.util.regex.Pattern;

import de.commercetools.sphere.client.shop.ShopClientConfig;

/** Internal configuration of the Sphere SDK. */
class Config {
    private static final String core         = "sphere.core";
    private static final String auth         = "sphere.auth";
    private static final String project      = "sphere.project";
    private static final String clientID     = "sphere.clientID";
    private static final String clientSecret = "sphere.clientSecret";

    private static final Pattern projectRegex = Pattern.compile("[a-zA-Z0-9_-]+");

    private static final Config instance = new Config(play.Configuration.root());

    public static Config root() {
        return instance;
    }

    private final play.Configuration playConfig;
    private final ShopClientConfig shopClientConfig;

    /** Creates a new instance of config. */
    public Config(play.Configuration playConfig) {
        this.playConfig = playConfig;
        this.shopClientConfig = new ShopClientConfig.Builder(projectID(), clientID(), clientSecret())
            .setCoreHttpServiceUrl(coreEndpoint())
            .setAuthHttpServiceUrl(authEndpoint())
            .build();
    }

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

    public ShopClientConfig shopClientConfig() { return this.shopClientConfig; }

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
