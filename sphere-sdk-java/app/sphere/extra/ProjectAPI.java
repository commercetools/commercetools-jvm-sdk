package sphere.extra;

import play.libs.WS;
import sphere.Endpoints;
import sphere.ProjectEndpoints;
import sphere.util.Base64;
import sphere.util.OAuthCredentials;

/** Package private helper for implementing wrappers around Sphere HTTP API endpoints. */
abstract class ProjectAPI {

    protected String project;
    protected ProjectEndpoints endpoints;
    protected OAuthCredentials credential;

    protected ProjectAPI(String project, OAuthCredentials credential) {
        this.project = project;
        this.endpoints = Endpoints.forProject(project);
        this.credential = credential;
    }

    /** Creates Play's WSRequestHolder with pre-filled OAuth access token. */
    WS.WSRequestHolder url(String endpoint) {
        return WS.url(endpoint).setHeader("Authorization", "Bearer " + Base64.encode(credential.accessToken()));
    }
}
