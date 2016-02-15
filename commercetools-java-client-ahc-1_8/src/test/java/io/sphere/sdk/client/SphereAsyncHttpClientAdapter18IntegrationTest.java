package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class SphereAsyncHttpClientAdapter18IntegrationTest {
    @Test
    public void integrationTest() {
        final HttpClient httpClient = AsyncHttpClientAdapter.of(new AsyncHttpClient());
        final SphereClientConfig sphereClientConfig = IntegrationTest.getSphereClientConfig();
        final SphereClientFactory factory = SphereClientFactory.of(() -> httpClient);
        try (final BlockingSphereClient sphereClient = BlockingSphereClient.of(factory.createClient(sphereClientConfig), 10, TimeUnit.SECONDS)) {
            final Project project = sphereClient.executeBlocking(ProjectGet.of());
            assertThat(project.getKey()).isEqualTo(sphereClientConfig.getProjectKey());
        }
    }
}
