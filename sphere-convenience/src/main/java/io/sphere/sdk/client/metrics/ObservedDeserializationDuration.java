package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;

import javax.annotation.Nullable;

/**
 * Message for the duration of transforming the http response body into a Java object.
 */
public final class ObservedDeserializationDuration extends ObservedDurationBase {
    @Nullable
    private final String correlationId;

    private ObservedDeserializationDuration(final String sphereRequestId, final SphereRequest<?> request, final long durationInMilliseconds, @Nullable final String correlationId) {
        super(sphereRequestId, request, durationInMilliseconds);
        this.correlationId = correlationId;
    }

    public static ObservedDeserializationDuration of(final long durationInMilliseconds, final String sphereRequestId, final SphereRequest<?> request, @Nullable final String correlationId) {
        return new ObservedDeserializationDuration(sphereRequestId, request, durationInMilliseconds, correlationId);
    }

    @Nullable
    public String getCorrelationId() {
        return correlationId;
    }
}
