package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.http.HttpResponse;

import javax.annotation.Nullable;

/**
 * Message for the duration of transforming the http response body into a Java object.
 */
public final class ObservedDeserializationDuration extends ObservedDurationBase {
    @Nullable
    private final String correlationId;
    @Nullable
    private final HttpResponse httpResponse;
    @Nullable
    private final Object result;

    private ObservedDeserializationDuration(final String sphereRequestId, final SphereRequest<?> request, final long durationInMilliseconds, @Nullable final String correlationId, @Nullable final HttpResponse httpResponse, @Nullable final Object result) {
        super(sphereRequestId, request, durationInMilliseconds);
        this.correlationId = correlationId;
        this.httpResponse = httpResponse;
        this.result = result;
    }

    public static ObservedDeserializationDuration of(final long durationInMilliseconds, final String sphereRequestId, final SphereRequest<?> request, @Nullable final String correlationId) {
        return of(durationInMilliseconds, sphereRequestId, request, correlationId, null, null);
    }

    public static ObservedDeserializationDuration of(final long durationInMilliseconds, final String sphereRequestId, final SphereRequest<?> request, @Nullable final String correlationId, @Nullable final HttpResponse httpResponse, @Nullable final Object result) {
        return new ObservedDeserializationDuration(sphereRequestId, request, durationInMilliseconds, correlationId, httpResponse, result);
    }

    @Nullable
    public String getCorrelationId() {
        return correlationId;
    }

    @Nullable
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    @Nullable
    public Object getResult() {
        return result;
    }
}
