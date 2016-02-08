package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;

/**
 * Contains the amount of time spent to transform the Java request object into a {@link io.sphere.sdk.client.HttpRequestIntent} object.
 */
public final class ObservedSerializationDuration extends ObservedDurationBase {

    private ObservedSerializationDuration(final long durationInMilliseconds, final String sphereRequestId, final SphereRequest<?> request) {
        super(sphereRequestId, request, durationInMilliseconds);
    }

    public static ObservedSerializationDuration of(final long durationInMilliseconds, final String requestId, final SphereRequest<?> request) {
        return new ObservedSerializationDuration(durationInMilliseconds, requestId, request);
    }

}
