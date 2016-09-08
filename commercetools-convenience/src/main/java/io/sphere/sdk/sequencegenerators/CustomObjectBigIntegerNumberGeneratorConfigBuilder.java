package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.math.BigInteger;

/**
 * Builds {@link CustomObjectBigIntegerNumberGeneratorConfig} instances using the Builder pattern.
 *
 */

public class CustomObjectBigIntegerNumberGeneratorConfigBuilder extends Base implements Builder<CustomObjectBigIntegerNumberGeneratorConfig> {

    private SphereClient sphereClient;
    private int maxRetryAttempts = 100;
    private String container = "io.sphere.sdk.sequencegenerators";
    private String key;
    private BigInteger initialValue = BigInteger.ONE;

    CustomObjectBigIntegerNumberGeneratorConfigBuilder(final SphereClient sphereClient, final String key){
        this.sphereClient = sphereClient;
        this.key = key;
    }

    public CustomObjectBigIntegerNumberGeneratorConfigBuilder container(final String container){
        this.container = container;
        return this;
    }

    public CustomObjectBigIntegerNumberGeneratorConfigBuilder maxRetryAttempts(final int maxRetryAttempts){
        this.maxRetryAttempts = maxRetryAttempts;
        return this;
    }

    public CustomObjectBigIntegerNumberGeneratorConfigBuilder initialValue(final BigInteger initialValue){
        this.initialValue = initialValue;
        return this;
    }

    public static CustomObjectBigIntegerNumberGeneratorConfigBuilder of(final SphereClient sphereClient, final String key) {
        return new CustomObjectBigIntegerNumberGeneratorConfigBuilder(sphereClient, key);
    }

    @Override
    public CustomObjectBigIntegerNumberGeneratorConfig build() {
        return new CustomObjectBigIntegerNumberGeneratorConfig(sphereClient, maxRetryAttempts, container, key, initialValue);
    }
}
