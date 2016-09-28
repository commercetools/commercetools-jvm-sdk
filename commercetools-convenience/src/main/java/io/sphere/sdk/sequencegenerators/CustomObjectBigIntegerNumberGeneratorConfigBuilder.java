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
    public static final int DEFAULT_RETRY_ATTEMPTS = 100;
    public static final String DEFAULT_CONTAINER = "io.sphere.sdk.sequencegenerators";

    private SphereClient sphereClient;
    private int maxRetryAttempts = DEFAULT_RETRY_ATTEMPTS;
    private String container = DEFAULT_CONTAINER;
    private String key;
    private BigInteger initialValue = BigInteger.ONE;

    CustomObjectBigIntegerNumberGeneratorConfigBuilder(final SphereClient sphereClient, final String key) {
        this.sphereClient = sphereClient;
        this.key = key;
    }

    /**
     * Sets the container for the {@link CustomObject} storing the last used sequence number.
     *
     * @param container container name of the underlying {@link CustomObject}. If this method is not used the default value is {@value DEFAULT_CONTAINER}.
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder container(final String container) {
        this.container = container;
        return this;
    }

    /**
     * Sets the maximum amount of retries.
     *
     * @param maxRetryAttempts the maximum amount of attempts to retry the number generation if a {@link io.sphere.sdk.client.ConcurrentModificationException} occurs
     * By default it is set to {@value DEFAULT_RETRY_ATTEMPTS}.
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder maxRetryAttempts(final int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
        return this;
    }

    /**
     * Sets the first number generated if the underlying {@link CustomObject} does not exist.
     *
     * @param initialValue first number generated. By default it is set to 1.
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder initialValue(final BigInteger initialValue) {
        this.initialValue = initialValue;
        return this;
    }

    /**
     * Creates a new builder instance.
     *
     * @param sphereClient A client to perform requests to the platform.
     * @param key the key is part of the namespace to store the {@link CustomObject} with the last used sequence number, it normally is sth. like "orderNumber" or "customerNumber"
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
