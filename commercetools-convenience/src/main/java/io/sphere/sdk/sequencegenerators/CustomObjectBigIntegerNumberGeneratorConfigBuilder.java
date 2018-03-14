package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.math.BigInteger;
import java.util.Objects;

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
        this.sphereClient = Objects.requireNonNull(sphereClient, "sphereClient");
        this.key = Objects.requireNonNull(key, "key");
    }

    /**
     * Sets the container for the {@link CustomObject} storing the last used sequence number.
     *
     * @param container container name of the underlying {@link CustomObject}. If this method is not used the default value is {@value DEFAULT_CONTAINER}.
     * @return the builder instance
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder container(final String container) {
        this.container = Objects.requireNonNull(container, "container");
        return this;
    }

    /**
     * Sets the maximum amount of retries.
     *
     * @param maxRetryAttempts the maximum amount of attempts to retry the number generation if a {@link io.sphere.sdk.client.ConcurrentModificationException} occurs
     * By default it is set to {@value DEFAULT_RETRY_ATTEMPTS}.
     * @return the builder instance
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder maxRetryAttempts(final int maxRetryAttempts) {
        if (maxRetryAttempts < 0) {
            throw new IllegalArgumentException(String.format("maxRetryAttempts needs to be 0 or positive but was %d", maxRetryAttempts));
        }
        this.maxRetryAttempts = maxRetryAttempts;
        return this;
    }

    /**
     * Sets the first number generated if the underlying {@link CustomObject} does not exist.
     *
     * @param initialValue first number generated. By default it is set to 1.
     * @return the builder instance
     */
    public CustomObjectBigIntegerNumberGeneratorConfigBuilder initialValue(final BigInteger initialValue) {
        this.initialValue = Objects.requireNonNull(initialValue, "initialValue");
        return this;
    }

    /**
     * Creates a new builder instance.
     *
     * @param sphereClient A client to perform requests to the platform.
     * @param key the key is part of the namespace to store the {@link CustomObject} with the last used sequence number, it normally is sth. like "orderNumber" or "customerNumber"
     * @return the builder instance
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
