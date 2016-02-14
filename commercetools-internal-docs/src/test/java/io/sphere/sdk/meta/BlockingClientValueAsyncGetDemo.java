package io.sphere.sdk.meta;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;

import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockingClientValueAsyncGetDemo {
    public static void demo(final BlockingSphereClient client, final String expectedProjectKey) {
        final SphereRequest<Project> sphereRequest = ProjectGet.of();
        final CompletionStage<Project> projectCompletionStage = client.execute(sphereRequest);
        assertThat(client)
                .as("blocking client can also used as regular sphere client")
                .isInstanceOf(SphereClient.class)
                .isInstanceOf(BlockingSphereClient.class);
    }
}
