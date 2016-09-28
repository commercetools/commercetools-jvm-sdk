package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Base;

import java.math.BigInteger;

/**
 * Configuration for the Sequential number generator.
 *
 * <h3 id="create-type">Set initial value and container example:</h3>
 * {@include.example io.sphere.sdk.sequencegenerators.BigIntegerNumberGeneratorIntegrationTest#firstNumberCanBeGiven()}
 *
 * @see CustomObjectBigIntegerNumberGeneratorConfigBuilder
 */
public final class CustomObjectBigIntegerNumberGeneratorConfig extends Base {

    private final SphereClient sphereClient;
    private final int maxRetryAttempts;
    private final String container;
    private final String key;
    private final BigInteger initialValue;

    CustomObjectBigIntegerNumberGeneratorConfig(final SphereClient sphereClient, final int maxRetryAttempts, final String container, final String key, final BigInteger initialValue) {
        this.sphereClient = sphereClient;
        this.maxRetryAttempts = maxRetryAttempts;
        this.container = container;
        this.key = key;
        this.initialValue = initialValue;
    }

    public int getMaxRetryAttempts() {
        return maxRetryAttempts;
    }

    public String getContainer() {
        return container;
    }

    public String getKey() {
        return key;
    }

    public SphereClient getSphereClient() {
        return sphereClient;
    }

    public BigInteger getInitialValue() {
        return initialValue;
    }
}
