package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.TestDoubleSphereClientFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CustomObjectBigIntegerNumberGeneratorConfigBuilderTest {
    @Test
    public void maxRetryAttemptsParameterNonNegative() {
        final SphereClient sphereClient = TestDoubleSphereClientFactory.createObjectTestDouble(r -> null);
        assertThatThrownBy(() -> CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(sphereClient, "key").maxRetryAttempts(-2))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("maxRetryAttempts needs to be 0 or positive but was -2");
    }
}