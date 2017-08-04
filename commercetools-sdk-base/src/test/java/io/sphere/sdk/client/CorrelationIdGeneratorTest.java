package io.sphere.sdk.client;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link CorrelationIdGenerator}.
 */
public class CorrelationIdGeneratorTest {

    @Test
    public void createCorrelationId() {
        final String projectKey = "my-project";

        final CorrelationIdGenerator correlationIdGenerator =
                CorrelationIdGenerator.of(projectKey);

        final String correlationId = correlationIdGenerator.get();
        final List<String> correlationIdParts = Arrays.asList(correlationId.split("/"));

        assertThat(correlationIdParts).hasSize(2);
        assertThat(correlationIdParts.get(0)).isEqualTo(projectKey);
        final UUID uuid = UUID.fromString(correlationIdParts.get(1));
        assertThat(uuid).isNotNull();
    }
}
