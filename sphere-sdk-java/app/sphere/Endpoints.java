package sphere;

import sphere.util.Url;

/** Centralizes construction of backend API urls. */
public class Endpoints {

    /** Sphere OAuth 2.0 token endpoint. */
    public static String tokenEndpoint() {
        return Url.combine(Config.authEndpoint(), "/oauth/token");
    }

    /** Sphere backend API endpoints for given project. */
    public static ProjectEndpoints forProject(String project) {
        if (project == null)
            throw new IllegalArgumentException("project cannot be null");
        return new ProjectEndpoints(Url.combine(Config.coreEndpoint(), project));
    }
}
