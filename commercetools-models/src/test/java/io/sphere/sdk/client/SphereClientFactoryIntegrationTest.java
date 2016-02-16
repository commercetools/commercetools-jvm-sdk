package io.sphere.sdk.client;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;

public class SphereClientFactoryIntegrationTest extends IntegrationTest {
    @Test
    public void autoLoadHttpClientWithoutExplicitSpecification() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final SphereClientFactory sphereClientFactory = SphereClientFactory.of();
            final SphereClient client = sphereClientFactory.createClient(getSphereClientConfig());
            final PagedQueryResult<Category> queryResult = client.execute(CategoryQuery.of()).toCompletableFuture().join();
            assertThat(queryResult).isNotNull();
            client.close();
        }
    }

    @Test
    public void useToken() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final SphereClientFactory sphereClientFactory = SphereClientFactory.of();
            final SphereApiConfig sphereClientConfig = getSphereClientConfig();
            try(final SphereClient client = sphereClientFactory.createClientOfApiConfigAndAccessToken(sphereClientConfig, accessToken())){
                final Project project = client.execute(ProjectGet.of()).toCompletableFuture().join();
                assertThat(project.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
            }
        }
    }

    @Test
    public void useTokenAndReuseClient() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final SphereClientFactory sphereClientFactory = SphereClientFactory.of();
            final SphereApiConfig sphereClientConfig = getSphereClientConfig();
            final StateHttpClient httpClient = new StateHttpClient(newHttpClient());
            try(final SphereClient client = sphereClientFactory.createClientOfApiConfigAndAccessToken(sphereClientConfig, accessToken(), httpClient)){
                final Project project = client.execute(ProjectGet.of()).toCompletableFuture().join();
                assertThat(project.getKey()).isEqualTo(getSphereClientConfig().getProjectKey());
                assertThat(httpClient.isClosed()).isFalse();
            }
            assertThat(httpClient.isClosed()).isFalse();
            httpClient.close();
            assertThat(httpClient.isClosed()).isTrue();
        }
    }

    private static class StateHttpClient implements HttpClient {
        private final HttpClient client;
        private boolean closed = false;

        private StateHttpClient(final HttpClient client) {
            this.client = client;
        }

        @Override
        public void close() {
            client.close();
            closed = true;
        }

        @Override
        public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
            return client.execute(httpRequest);
        }

        public boolean isClosed() {
            return closed;
        }
    }

}
