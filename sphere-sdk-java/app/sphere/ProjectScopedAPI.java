package sphere;

import play.libs.WS;
import sphere.Endpoints;
import sphere.ProjectEndpoints;
import sphere.util.Base64;

/** Package private helper for working with Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI {

    protected ProjectEndpoints endpoints;
    protected ClientCredentials credential;

    protected ProjectScopedAPI(ClientCredentials credential, ProjectEndpoints endpoints) {
        this.endpoints = endpoints;
        this.credential = credential;
    }

    // allows for overriding in tests
    protected WS.WSRequestHolder createRequestHolder(String url) {
        return WS.url(url);
    }

    /** Creates Play's WSRequestHolder with pre-filled OAuth access token. */
    WS.WSRequestHolder url(String endpoint) {
        if (Log.isTraceEnabled()) {
            Log.trace(endpoint);
        }
        return createRequestHolder(endpoint).setHeader("Authorization", "Bearer " + credential.accessToken());
    }
}
