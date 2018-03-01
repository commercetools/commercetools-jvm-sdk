package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SphereClient}.
 */
public class SphereClientIntegrationTest extends IntegrationTest {
    private Optional<String> correlationId;

    @Test
    public void shouldRetrieveCorrelationId() throws ExecutionException, InterruptedException {
        final CorrelationIdRequestDecorator correlationIdRequestDecorator = new CorrelationIdRequestDecorator(ProjectGet.of());
        client().executeBlocking(correlationIdRequestDecorator);

        assertThat(correlationId.isPresent()).isTrue();

        final List<String> correlationIdParts = Arrays.asList(correlationId.get().split("/"));
        assertThat(correlationIdParts).hasSize(2);

        assertThat(correlationIdParts.get(0)).isEqualTo(client().getConfig().getProjectKey());

        final UUID uuid = UUID.fromString(correlationIdParts.get(1));
        assertThat(uuid).isNotNull();
    }

    private class CorrelationIdRequestDecorator extends SphereRequestDecorator<Project> {

        public CorrelationIdRequestDecorator(final SphereRequest<Project> request) {
            super(request);
        }

        @Override
        public Project deserialize(HttpResponse httpResponse) {
            correlationId = httpResponse.getHeaders().findFlatHeader(HttpHeaders.X_CORRELATION_ID);
            return super.deserialize(httpResponse);
        }
    }
}
