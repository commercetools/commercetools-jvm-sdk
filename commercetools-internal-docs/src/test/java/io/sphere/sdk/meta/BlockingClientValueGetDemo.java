package io.sphere.sdk.meta;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BlockingClientValueGetDemo {
    public static void demo(final BlockingSphereClient client, final String expectedProjectKey) {
        final SphereRequest<Project> sphereRequest = ProjectGet.of();
        final Project project = client.executeBlocking(sphereRequest);//returns result directly, no CompletionStage
        assertThat(project.getKey()).isEqualTo(expectedProjectKey);
    }
}
