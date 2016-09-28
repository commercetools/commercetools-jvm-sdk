package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.math.BigInteger;

/**
 *
 * Builds {@link CustomObjectBigIntegerNumberGeneratorConfig} instances using the Builder pattern.
 */
public final class CustomObjectBigIntegerNumberGeneratorConfigBuilder extends Base implements Builder<CustomObjectBigIntegerNumberGeneratorConfig> {

    private SphereClient sphereClient;
    private int maxRetryAttempts = 100;
    private String container = "io.sphere.sdk.sequencegenerators";
    private String key;
    private BigInteger initialValue = BigInteger.ONE;

    CustomObjectBigIntegerNumberGeneratorConfigBuilder(final SphereClient sphereClient, final String key) {
        this.sphereClient = sphereClient;
        this.key = key;
    }

    /**
     *
     * @param container
     * Container and key are namespaces like in a key-value store.
     *
     * @see CustomObject
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder container(final String container) {
        this.container = container;
        return this;
    }

    /**
     *
     * @param maxRetryAttempts
     * If there is a concurrency exception, the generator retries to generate the number. This value sets the maximum number of retries.
     * (By default it is set to 100)
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder maxRetryAttempts(final int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
        return this;
    }

    /**
     *
     * @param initialValue
     * The initial value allows to start the number sequence in a given number. (By default it is set to 1)
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder initialValue(final BigInteger initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    /**
     *
     * @param sphereClient A client to perform requests to the platform.
     * @param key Container and key are namespaces like in a key-value store.
     *
     * @see CustomObject
     */
    public static CustomObjectBigIntegerNumberGeneratorConfigBuilder of(final SphereClient sphereClient, final String key) {
        return new CustomObjectBigIntegerNumberGeneratorConfigBuilder(sphereClient, key);
    }

    @Override
    public CustomObjectBigIntegerNumberGeneratorConfig build() {
        return new CustomObjectBigIntegerNumberGeneratorConfig(sphereClient, maxRetryAttempts, container, key, initialValue);
    }
}
