package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;

/**
 * Message for the duration of transforming the http response body into a Java object.
 */
public final class ObservedDeserializationDuration extends ObservedDurationBase {
    private ObservedDeserializationDuration(final String sphereRequestId, final SphereRequest<?> request, final long durationInMilliseconds) {
        super(sphereRequestId, request, durationInMilliseconds);
    }

    public static ObservedDeserializationDuration of(final long durationInMilliseconds, final String sphereRequestId, final SphereRequest<?> request) {
        return new ObservedDeserializationDuration(sphereRequestId, request, durationInMilliseconds);
    }
}
