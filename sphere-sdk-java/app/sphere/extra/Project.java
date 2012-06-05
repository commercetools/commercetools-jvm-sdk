package sphere.extra;

import sphere.Config;
import sphere.util.Url;

public class Project {
    /** Returns backend endpoint for given project. */
    public static String endpoint(String project) {
        if (project == null)
            throw new IllegalArgumentException("project cannot be null");
        return Url.combine(Config.coreEndpoint(), project);
    }
}
