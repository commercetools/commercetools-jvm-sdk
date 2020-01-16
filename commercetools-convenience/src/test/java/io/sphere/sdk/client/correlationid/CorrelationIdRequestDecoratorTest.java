package io.sphere.sdk.client.correlationid;

import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CorrelationIdRequestDecoratorTest {

    @Test
    public void of_always_ShouldSetCorrelationId() {
        // prep
        final String correlationId = UUID.randomUUID().toString();
        final CorrelationIdRequestDecorator<Project> requestWithCorrelationId = CorrelationIdRequestDecorator
            .of(ProjectGet.of(), correlationId);

        // test
        final HttpHeaders headers = requestWithCorrelationId.httpRequestIntent().getHeaders();

        //assert
        assertThat(headers.getHeader(HttpHeaders.X_CORRELATION_ID)).containsOnly(correlationId);
    }
}