package io.sphere.sdk.projects.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.json.SphereJsonUtils;

import static io.sphere.sdk.http.HttpMethod.GET;

public final class ProjectGet extends Base implements SphereRequest<Project> {
    private ProjectGet() {
    }

    @Override
    public Project deserialize(final HttpResponse httpResponse) {
        return SphereJsonUtils.readObject(httpResponse.getResponseBody(), Project.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(GET, "");
    }

    public static SphereRequest<Project> of() {
        return new ProjectGet();
    }
}
