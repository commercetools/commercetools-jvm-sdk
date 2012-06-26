package sphere;

import play.libs.WS;
import sphere.Endpoints;
import sphere.ProjectEndpoints;
import sphere.util.Base64;
import sphere.util.ClientCredentials;

/** Package private helper for working with Sphere HTTP APIs scoped to a project. */
abstract class ProjectScopedAPI {

    protected String project;
    protected ProjectEndpoints endpoints;
    protected ClientCredentials credential;

    protected ProjectScopedAPI(String project, ClientCredentials credential) {
        this.project = project;
        this.endpoints = Endpoints.forProject(project);
        this.credential = credential;
    }

    /** Creates Play's WSRequestHolder with pre-filled OAuth access token. */
    WS.WSRequestHolder url(String endpoint) {
        return WS.url(endpoint).setHeader("Authorization", "Bearer " + credential.accessToken());
    }
}
