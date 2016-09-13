package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

import java.math.BigInteger;

/**
 * Configuration for the Sequential number generator.
 *
 * @see CustomObjectBigIntegerNumberGeneratorConfigBuilder
 */
public final class CustomObjectBigIntegerNumberGeneratorConfig extends Base {

    private final SphereClient sphereClient;

    /**
     *
     * If there is a concurrency exception, the generator retries to generate the number. This parameter sets the maximum number of retries.
     * (by default is is set to 100 in the config builder)
     *
     * @see CustomObjectBigIntegerNumberGeneratorConfigBuilder
     */
    private final int maxRetryAttempts;

    /**
     *
     * Container and key are namespaces like in a key-value store.
     *
     * @see CustomObject
     */
    private final String container;
    private final String key;

    /**
     *
     * initialValue allows to start the number sequence in a given number.
     * (by default is is set to 1 in the config builder)
     *
     * @see CustomObjectBigIntegerNumberGeneratorConfigBuilder
     */
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
